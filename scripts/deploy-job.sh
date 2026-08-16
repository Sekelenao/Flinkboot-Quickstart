#!/bin/bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "${SCRIPT_DIR}/.." && pwd )"

CONTAINER_NAME="flink-jobmanager"
JAR_PATH="${PROJECT_ROOT}/job/target/job-1.0.0.jar"
CONTAINER_JAR_PATH="/tmp/job.jar"
CONTAINER_CONFIG_PATH="/tmp/job-configuration.yaml"
MAIN_CLASS="io.github.sekelenao.quickstart.HeartbeatJob"

echo "1. Checking if Flink JobManager container is running..."
if [ ! "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]; then
    echo "❌ Error: The '${CONTAINER_NAME}' container is not running."
    echo "Please start the cluster first with: docker compose up -d"
    exit 1
fi

echo "2. Packaging project with Maven..."
mvn -f "${PROJECT_ROOT}/pom.xml" clean package -DskipTests

echo "3. Copying JAR and configuration to Flink JobManager container..."
docker cp "${JAR_PATH}" "${CONTAINER_NAME}:${CONTAINER_JAR_PATH}"

# We replace localhost:9092 with kafka:29092 for in-cluster communication
sed 's/localhost:9092/kafka:29092/g' "${PROJECT_ROOT}/job/src/main/resources/job-configuration.yaml" > /tmp/job-cluster-config.yaml
docker cp /tmp/job-cluster-config.yaml "${CONTAINER_NAME}:${CONTAINER_CONFIG_PATH}"
rm -f /tmp/job-cluster-config.yaml

echo "4. Cancelling any previously running jobs..."
RUNNING_JOBS=$(docker exec ${CONTAINER_NAME} flink list -r 2>/dev/null | grep -oE '[0-9a-f]{32}' || true)
for JOB_ID in ${RUNNING_JOBS}; do
    echo "Cancelling previous job: ${JOB_ID}"
    docker exec ${CONTAINER_NAME} flink cancel "${JOB_ID}" || true
done

echo "5. Submitting Flink Job to cluster..."
docker exec -w /tmp ${CONTAINER_NAME} flink run -d -c ${MAIN_CLASS} ${CONTAINER_JAR_PATH} -flinkboot-configurations file:${CONTAINER_CONFIG_PATH}

echo "✅ Flink job submitted successfully! View status at http://localhost:8081"

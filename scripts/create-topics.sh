#!/bin/bash
set -e

CONTAINER_NAME="kafka"

echo "Checking if Kafka container is running..."
if [ "$(docker ps -q -f name=^/${CONTAINER_NAME}$)" ]; then
    echo "Creating Kafka topics 'heartbeats.raw' and 'heartbeats.alerts'..."
    docker exec ${CONTAINER_NAME} /opt/kafka/bin/kafka-topics.sh \
      --bootstrap-server localhost:9092 \
      --create \
      --topic heartbeats.raw \
      --partitions 2 \
      --replication-factor 1 \
      --if-not-exists

    docker exec ${CONTAINER_NAME} /opt/kafka/bin/kafka-topics.sh \
      --bootstrap-server localhost:9092 \
      --create \
      --topic heartbeats.alerts \
      --partitions 2 \
      --replication-factor 1 \
      --if-not-exists

    echo "✅ Topics created successfully!"
else
    echo "❌ Error: The '${CONTAINER_NAME}' container is not running."
    echo "Please start the cluster first with: docker compose up -d"
    exit 1
fi

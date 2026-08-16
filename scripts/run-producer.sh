#!/bin/bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "${SCRIPT_DIR}/.." && pwd )"

MESSAGE_COUNT="${1:-15}"
INTERVAL_MS="${2:-500}"

echo "Starting Heartbeat Producer (sending ${MESSAGE_COUNT} messages, 1 every ${INTERVAL_MS}ms)..."
mvn -f "${PROJECT_ROOT}/producer/pom.xml" exec:java \
  -Dexec.mainClass="io.github.sekelenao.quickstart.producer.HeartbeatProducer" \
  -Dexec.args="localhost:9092 heartbeats.raw ${MESSAGE_COUNT} ${INTERVAL_MS}"

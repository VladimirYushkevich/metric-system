#!/bin/bash

# PATHS
TOPIC_FILE="topic_list.txt"

# FUNCTIONS
function info() {
  # logging an info message
  echo "[start_local.sh] (INFO) : $*"
}
function success() {
  # logging an info message
  echo -e "[start_local.sh] (INFO) : $*"
}
function error() {
  # logging an error message and exiting
  echo -e "[start_local.sh] (ERROR): $1"
  exit 1
}
function infoblock() {
  # logging an info block message
  info
  info "========================================"
  info "$*"
  info "========================================"
}
function create_topic() {
  # creates a topic on Kafka
  docker exec kafka kafka-topics --create --zookeeper zookeeper:2181 --topic $1 --replication-factor 1 --partitions $2 --if-not-exists
  echo consume topic: docker exec -i kafka kafka-console-consumer --topic $1 --bootstrap-server localhost:29092 --from-beginning
}
function compose_up() {
  # starts all containers by docker compose
  docker-compose --compatibility up -d
}
function check_flyway_result() {
  info "Checking flyway logs"
  flyway_logs=$(docker-compose --compatibility logs -f flyway)
  if [[ ! $flyway_logs =~ "Successfully applied" ]]; then
    echo "$flyway_logs"
    error "Flyway seems to have issues creating tables"
  fi
  success "Tables created successfully"
}
function wait_for_kafka() {
  info "Waiting for Kafka to start"
  grep -q "INFO starting (kafka.server.KafkaServer)" <(docker-compose --compatibility logs -f kafka)
  success "Kafka is listening"
}

## MAIN
# We start all services in Docker Compose
infoblock "Start all services in Docker"
if ! compose_up; then
  error "Could not start docker services"
fi
check_flyway_result
wait_for_kafka
success "All containers created successfully"

# Create topics
infoblock "Create all topics"
while IFS=' ' read -r topic partition; do
  if ! create_topic "$topic" "$partition"; then
    error "Could not create topic: $topic"
  fi
done <$TOPIC_FILE
success "All topics created successfully"

success Finished successfully.
info When you are done, run \`docker-compose --compatibility down -v\`
info You can also safely Ctrl-C and abort nest steps.

# Grepping Logs for Errors
info will now grep docker compose logs for error.
echo "> docker-compose --compatibility logs -f | grep ERROR ..."
docker-compose --compatibility logs -f | grep ERROR
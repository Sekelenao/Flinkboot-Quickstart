# Flinkboot Quickstart ⚡

> **Production-ready Apache Flink 1.20 streaming pipeline in under 40 lines of code.**  
> Showcasing fail-fast multi-source configuration, 100% native POJO serialization (zero Kryo fallback), and zero-boilerplate Kafka connectors with [Flinkboot](https://github.com/Sekelenao/Flinkboot).

[![Java](https://img.shields.io/badge/Java_17-%23ED8B00.svg?logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/17/)
[![Flink](https://img.shields.io/badge/Flink_1.20-%23E6526F.svg?logo=apacheflink&logoColor=white)](https://flink.apache.org/)
[![Flinkboot](https://img.shields.io/badge/Flinkboot_0.2.0--1.20-%2300599C.svg)](https://github.com/Sekelenao/Flinkboot)

---

## 🎯 Use Case: Real-Time Heartbeat & Latency Monitor

A real-time edge monitoring pipeline:
1. Consumes device heartbeats from Kafka topic `heartbeats.raw`.
2. Inspects latency and health status against dynamic YAML thresholds (`Duration`).
3. Emits alert events to Kafka topic `heartbeats.alerts` whenever a device experiences degraded health or high latency.

```mermaid
flowchart LR
    Producer["📡 Devices / Producer"] -->|"heartbeats.raw"| KafkaIn[("Kafka Input")]
    KafkaIn -->|"KafkaSource"| FlinkJob["⚡ Flinkboot Job (HeartbeatJob)"]
    FlinkJob -->|"HeartbeatAlertFilter & HeartbeatAlertMapper"| KafkaOut[("Kafka Output")]
    KafkaOut -->|"heartbeats.alerts"| Consumer["🚨 Alerting & Dashboard"]
```

---

## 💡 What This Quickstart Showcases

### 1. ⚡ Fail-Fast Multi-Source Configuration (Java 17 Record + Jakarta Bean Validation)
Configurations are loaded from YAML, merged with CLI args / environment variables, and validated using Jakarta Bean Validation **before** any Flink cluster resources are provisioned.

```java
public record HeartbeatJobConfiguration(
    @Valid @NotNull @JsonProperty("job") JobProperties job,
    @Valid @NotNull @JsonProperty("kafka-source") KafkaSourceTopicListProperties kafkaSource,
    @Valid @NotNull @JsonProperty("kafka-sink") KafkaSinkProperties kafkaSink,
    @NotNull @JsonProperty("max-latency") Duration maxLatency
) implements Serializable {}
```

### 2. 🚀 Native POJO Serialization (Zero Kryo Fallback)
Using `java.time.LocalDateTime` and `java.time.Duration` without slow Kryo fallback via Flinkboot's built-in `@TypeInfo` factories:

```java
public class HeartbeatEvent {
    @JsonProperty("deviceId")
    public String deviceId;

    @JsonProperty("status")
    public DeviceStatus status;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @TypeInfo(LocalDateTimeTypeInfoFactory.class)
    public LocalDateTime timestamp;

    @JsonProperty("latency")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TypeInfo(DurationTypeInfoFactory.class)
    public Duration latency;

    public HeartbeatEvent() {}
}
```

Verified at build-time in unit tests:
```java
@Test
void shouldComplyWithFlinkPojoRequirements() {
    FlinkbootTest.assertPojo(HeartbeatEvent.class);
    FlinkbootTest.assertPojo(HeartbeatAlert.class);
}
```

### 3. 📦 Zero-Boilerplate Bootstrapping
Initializes execution environment, checkpointing, restart strategies, and production Kafka connectors in a clean, readable pipeline:

```java
public static void main(String[] args) throws Exception {
    var boot = Flinkboot.initialize(args);
    var config = boot.configuration(HeartbeatJobConfiguration.class);

    var env = boot.executionEnvironment(config.job());

    var deserializer = KafkaRecordDeserializationSchema.valueOnly(new HeartbeatDeserializationSchema());
    var kafkaSource = KafkaSourceFactory.supplyFor(config.kafkaSource(), deserializer);

    var serializer = KafkaRecordSerializationSchema.<HeartbeatAlert>builder()
        .setTopic(config.kafkaSink().topic())
        .setValueSerializationSchema(new HeartbeatAlertSerializationSchema())
        .build();
    var kafkaSink = KafkaSinkFactory.supplyFor(config.kafkaSink(), serializer);

    env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Heartbeats Source")
       .filter(new HeartbeatAlertFilter(config.maxLatency()))
       .name("Filter Unhealthy or High Latency")
       .map(new HeartbeatAlertMapper(config.maxLatency()))
       .name("Map to Alert")
       .sinkTo(kafkaSink)
       .name("Kafka Alerts Sink");

    env.execute(config.job().name());
}
```

---

## 🚀 Quickstart: Up & Running in 3 Minutes

### Prerequisites
* Docker & Docker Compose
* Java 17+ & Maven 3.8+

### Step 1: Start Kafka & Flink Cluster
```bash
docker compose up -d
```
* **Kafka UI:** [http://localhost:8085](http://localhost:8085)
* **Flink Dashboard:** [http://localhost:8081](http://localhost:8081)

### Step 2: Create Kafka Topics
```bash
./scripts/create-topics.sh
```

### Step 3: Deploy the Flink Job
```bash
./scripts/deploy-job.sh
```

### Step 4: Stream Mock Heartbeats
```bash
./scripts/run-producer.sh 15 500
```

Open **[Kafka UI (http://localhost:8085)](http://localhost:8085)** and navigate to `heartbeats.alerts` to see live alerts triggered by the Flink pipeline!

---

## 🧪 Running Tests
Verify POJO compliance, configuration parsing, and operator logic:
```bash
mvn clean test
```

---

## 📂 Project Structure

```text
flinkboot-quickstart/
├── docker-compose.yml              # Kafka 3.8 (KRaft), Kafka UI, Flink 1.20 cluster
├── pom.xml                         # Parent BOM & modules definition
├── README.md                       # Documentation
├── scripts/
│   ├── create-topics.sh            # Topic creation script
│   ├── deploy-job.sh               # Fat JAR build & Flink submit script
│   └── run-producer.sh             # Live event generator runner
├── job/                            # Flink Streaming Pipeline Module
│   ├── pom.xml
│   ├── src/main/java/io/github/sekelenao/quickstart/
│   │   ├── HeartbeatJob.java                       # Flink main streaming pipeline
│   │   ├── configuration/
│   │   │   └── HeartbeatJobConfiguration.java      # Validated Java 17 record configuration
│   │   ├── model/
│   │   │   ├── DeviceStatus.java                   # Device state enum
│   │   │   ├── HeartbeatEvent.java                 # Native Flink POJO (with Duration)
│   │   │   └── HeartbeatAlert.java                 # Alert POJO
│   │   ├── operator/
│   │   │   ├── HeartbeatAlertFilter.java           # Filter operator (FilterFunction)
│   │   │   └── HeartbeatAlertMapper.java           # Map operator (MapFunction)
│   │   └── serde/
│   │       ├── HeartbeatDeserializationSchema.java # Jackson source deserializer
│   │       └── HeartbeatAlertSerializationSchema.java
│   ├── src/main/resources/
│   │   └── job-configuration.yaml                  # Unified YAML config
│   └── src/test/java/io/github/sekelenao/quickstart/
│       ├── configuration/
│       │   └── HeartbeatJobConfigurationTest.java  # Config validation test
│       ├── model/
│       │   └── HeartbeatPojoComplianceTest.java    # POJO compliance test (assertPojo)
│       ├── operator/
│       │   ├── HeartbeatAlertFilterTest.java       # Filter unit tests
│       │   └── HeartbeatAlertMapperTest.java       # Mapper unit tests
│       └── serde/
│           └── HeartbeatAlertSerializationSchemaTest.java
└── producer/                       # Standalone Kafka Event Producer Module
    ├── pom.xml
    └── src/main/java/io/github/sekelenao/quickstart/producer/
        └── HeartbeatProducer.java                  # Standalone mock event generator
```

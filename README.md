# Flinkboot Quickstart

Minimal Apache Flink stream bootstrapping application powered by **[Flinkboot](https://flinkboot.com)**.

> [!CAUTION]
> **Required IntelliJ IDEA configuration before first launch:**
>
> Apache Flink dependencies are declared with `provided` scope by the Flinkboot BOM to ensure seamless deployment onto remote Flink clusters.
>
> To run [`Main`](src/main/java/com/example/flinkboot/quickstart/Main.java) directly in IntelliJ IDEA without triggering a `ClassNotFoundException`:
> 1. Open your Run Configuration (**Run > Edit Configurations...**)
> 2. Select **Main**
> 3. Click **Modify options**
> 4. Check **"Add dependencies with 'Provided' scope to classpath"**
> 5. Apply and run the application.
> 6. Go to http://localhost:8080 to view the Flink Web UI

---

## Prerequisites

- **Java 11+**
- **Maven 3.8+**
- **IntelliJ IDEA** (or your favorite Java IDE)
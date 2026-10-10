# Flinkboot Quickstart

Minimal Apache Flink stream bootstrapping application powered by **[Flinkboot](https://flinkboot.com)**.

> [!TIP]
> **IntelliJ IDEA setup for local launch:**
>
> Apache Flink dependencies are declared with `provided` scope by the Flinkboot BOM to ensure seamless deployment onto remote Flink clusters.
>
> To run [`Main`](src/main/java/com/example/flinkboot/quickstart/Main.java) directly in IntelliJ IDEA without triggering a `ClassNotFoundException`:
> 1. Open your Run Configuration (**Run > Edit Configurations...**)
> 2. Select **Main**
> 3. Click **Modify options**
> 4. Check **"Add dependencies with 'Provided' scope to classpath"**
> 5. Run the application and open [http://localhost:8080](http://localhost:8080) to view the live Flink Web UI dashboard!

---

## What Does This App Do?

Imagine a busy spaceport station at the edge of the galaxy! 🌌

Spaceships arrive constantly with cargo boxes full of galactic goods: golden apples, quantum processors, jars of dirt, or vintage zero-gravity wine.

Most items are completely harmless, but there is one dangerous contraband item that should **never** enter the station: **Alien Eggs** 🥚👾!

This application acts like an automatic **Space Customs Security Scanner**:

1. 📦 **Space Cargo Source**: Spaceships arrive and unload boxes containing random galactic items.
2. 🔍 **Forbidden Items Filter**: An automated scanner checks inside every box. If it detects an **Alien Egg**, it flags the cargo for quarantine!
3. 🚨 **Customs Alert Map**: For every intercepted box, an immediate quarantine alert message is formatted (`🚨 [QUARANTINE ALERT] Intercepted illegal cargo: ...`).
4. 🖨️ **Printing Alert Sink**: Prints intercepted quarantine alerts to standard output for the customs officers on duty.
5. 🖥️ **Live Radar (Web UI)**: You can open [http://localhost:8080](http://localhost:8080) to watch the entire inspection pipeline and checkpoints run live!

---

## Prerequisites

- **Java 11+**
- **Maven 3.8+**
- **IntelliJ IDEA** (or your favorite Java IDE)
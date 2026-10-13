# Flinkboot Quickstart

Minimal Apache Flink stream bootstrapping application powered by **[Flinkboot](https://flinkboot.com)**.

> 📖 **Prefer a visual step-by-step guide?**  
> Check out the **[Illustrated Quickstart Tutorial on flinkboot.com](https://flinkboot.com/quickstart)** to get up and running with screenshots in under 2 minutes.

---

## What Does This App Do?

Imagine a busy spaceport station at the edge of the galaxy! 🌌

Spaceships arrive constantly with cargo boxes full of galactic goods: golden apples, quantum processors, jars of dirt, or vintage zero-gravity wine.

Most items are completely harmless, but there is one dangerous contraband item that should **never** enter the station: **Alien Eggs** 🕷️☣️!

This application acts like an automatic **Space Customs Security Scanner**:

1. 📦 **Space Cargo Source**: Spaceships arrive and unload boxes containing random galactic items.
2. 🔍 **Forbidden Items Filter**: An automated scanner checks inside every box. If it detects an **Alien Egg**, it flags the cargo for quarantine!
3. 🚨 **Customs Alert Map**: For every intercepted box, an immediate quarantine alert message is formatted (`🚨 [QUARANTINE ALERT] Intercepted illegal cargo: ...`).
4. 🖨️ **Printing Alert Sink**: Prints intercepted quarantine alerts to standard output for the customs officers on duty.
5. 🖥️ **Live Radar (Web UI)**: Open [http://localhost:8080](http://localhost:8080) to watch the entire inspection pipeline and checkpoints run live!

---

## Prerequisites

- **Java 11+**
- **Maven**
- **IntelliJ IDEA**

---

## Quickstart

### 1. Clone the repository

```bash
git clone https://github.com/Sekelenao/Flinkboot-Quickstart.git
cd Flinkboot-Quickstart
```

Open the project in **IntelliJ IDEA**.

### 2. IntelliJ IDEA setup

Flink runtime dependencies are declared with `provided` scope by the Flinkboot BOM so deployment JARs stay lightweight on remote clusters.

To run locally in IntelliJ IDEA without a `ClassNotFoundException`:

1. In the project tree, right-click [`Main.java`](src/main/java/com/example/flinkboot/quickstart/Main.java) > **More Run/Debug** > **Modify Run Configuration...**
2. In the top-right of the modal, click **Modify options** (or press `Alt + M`).
3. Under **Java**, check **"Add dependencies with 'provided' scope to classpath"**.
4. Click **Apply**, then hit the green **Run 'Main'** button!

*(Looking for screenshots? Follow the [illustrated guide on flinkboot.com](https://flinkboot.com/quickstart).)*

### 3. Watch the scanner run live!

- Real-time alerts print directly to your IntelliJ console as contraband is intercepted.
- Open **[http://localhost:8080](http://localhost:8080)** in your browser to inspect the live Flink Web UI and pipeline graph!
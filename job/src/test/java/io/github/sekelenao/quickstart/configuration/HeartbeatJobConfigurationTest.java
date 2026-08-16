package io.github.sekelenao.quickstart.configuration;

import io.github.sekelenao.flinkboot.test.api.FlinkbootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("HeartbeatJobConfiguration Test")
class HeartbeatJobConfigurationTest {

    @Test
    @DisplayName("Should successfully load and validate default YAML configuration with Duration")
    void shouldLoadDefaultConfiguration() {
        var config = FlinkbootTest.configuration(
                HeartbeatJobConfiguration.class,
                "classpath:job-configuration.yaml"
        );

        assertNotNull(config);
        assertAll(
                () -> assertEquals("flinkboot-heartbeat-monitor", config.job().name()),
                () -> assertEquals(Duration.ofMillis(500), config.maxLatency()),
                () -> assertTrue(config.kafkaSource().topics().contains("heartbeats.raw")),
                () -> assertEquals("heartbeats.alerts", config.kafkaSink().topic()),
                () -> {
                    var env = config.job().environment().orElseThrow();
                    assertEquals("false", env.properties().get("pipeline.operator-chaining.enabled"));
                }
        );
    }
}

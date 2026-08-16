package io.github.sekelenao.quickstart.serde;

import io.github.sekelenao.quickstart.pojo.HeartbeatAlert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("HeartbeatAlertSerializationSchema Test")
class HeartbeatAlertSerializationSchemaTest {

    @Test
    @DisplayName("Should serialize HeartbeatAlert with ISO-8601 date format")
    void shouldSerializeWithIsoDateFormat() {
        var schema = new HeartbeatAlertSerializationSchema();
        schema.open(null);

        var alert = new HeartbeatAlert();
        alert.deviceId = "srv-01";
        alert.alertType = "HIGH_LATENCY";
        alert.message = "High latency detected";
        alert.detectedAt = LocalDateTime.of(2026, 8, 16, 18, 30, 0, 123000000);
        alert.latency = Duration.ofMillis(750);

        var bytes = schema.serialize(alert);
        var json = new String(bytes, StandardCharsets.UTF_8);

        assertNotNull(json);
        assertTrue(json.contains("\"detectedAt\":\"2026-08-16T18:30:00.123\""), "JSON should contain formatted ISO date: " + json);
        assertTrue(json.contains("\"deviceId\":\"srv-01\""));
    }
}

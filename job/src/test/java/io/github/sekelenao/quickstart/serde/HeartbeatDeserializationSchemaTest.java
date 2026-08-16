package io.github.sekelenao.quickstart.serde;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("HeartbeatDeserializationSchema Test")
class HeartbeatDeserializationSchemaTest {

    @Test
    @DisplayName("Should deserialize JSON message into HeartbeatEvent")
    void shouldDeserializeMessage() throws IOException {
        var schema = new HeartbeatDeserializationSchema();
        schema.open(null);

        var json = "{\"deviceId\":\"srv-10\",\"status\":\"HEALTHY\",\"timestamp\":\"2026-08-16T19:00:00.000\",\"latency\":\"PT0.45S\"}";
        var event = schema.deserialize(json.getBytes(StandardCharsets.UTF_8));

        assertNotNull(event);
        assertAll(
                () -> assertEquals("srv-10", event.deviceId),
                () -> assertEquals(DeviceStatus.HEALTHY, event.status),
                () -> assertEquals(Duration.ofMillis(450), event.latency),
                () -> assertNotNull(event.timestamp)
        );
    }
}

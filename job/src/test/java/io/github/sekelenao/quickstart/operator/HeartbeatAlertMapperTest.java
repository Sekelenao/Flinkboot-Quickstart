package io.github.sekelenao.quickstart.operator;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("HeartbeatAlertMapper Test")
class HeartbeatAlertMapperTest {

    private final HeartbeatAlertMapper mapper = new HeartbeatAlertMapper(Duration.ofMillis(500));

    @Test
    @DisplayName("Should map degraded device event to DEVICE_DEGRADED alert")
    void shouldMapDegradedEventToAlert() {
        var event = new HeartbeatEvent();
        event.deviceId = "srv-degraded";
        event.status = DeviceStatus.DEGRADED;
        event.timestamp = LocalDateTime.now();
        event.latency = Duration.ofMillis(200);

        var alert = mapper.map(event);

        assertNotNull(alert);
        assertAll(
                () -> assertEquals("srv-degraded", alert.deviceId),
                () -> assertEquals("DEVICE_DEGRADED", alert.alertType),
                () -> assertTrue(alert.message.contains("status is DEGRADED")),
                () -> assertNotNull(alert.detectedAt)
        );
    }

    @Test
    @DisplayName("Should map high latency event to HIGH_LATENCY alert")
    void shouldMapHighLatencyEventToAlert() {
        var event = new HeartbeatEvent();
        event.deviceId = "srv-slow";
        event.status = DeviceStatus.HEALTHY;
        event.timestamp = LocalDateTime.now();
        event.latency = Duration.ofMillis(900);

        var alert = mapper.map(event);

        assertNotNull(alert);
        assertAll(
                () -> assertEquals("srv-slow", alert.deviceId),
                () -> assertEquals("HIGH_LATENCY", alert.alertType),
                () -> assertTrue(alert.message.contains("latency is 900ms")),
                () -> assertNotNull(alert.detectedAt)
        );
    }
}

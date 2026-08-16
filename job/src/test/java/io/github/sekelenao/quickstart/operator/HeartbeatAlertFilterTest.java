package io.github.sekelenao.quickstart.operator;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("HeartbeatAlertFilter Test")
class HeartbeatAlertFilterTest {

    private final HeartbeatAlertFilter filter = new HeartbeatAlertFilter(Duration.ofMillis(500));

    @Test
    @DisplayName("Should pass filter when device status is DEGRADED or DOWN")
    void shouldPassWhenStatusNotHealthy() {
        var degradedEvent = new HeartbeatEvent();
        degradedEvent.deviceId = "srv-1";
        degradedEvent.status = DeviceStatus.DEGRADED;
        degradedEvent.timestamp = LocalDateTime.now();
        degradedEvent.latency = Duration.ofMillis(100);

        var downEvent = new HeartbeatEvent();
        downEvent.deviceId = "srv-2";
        downEvent.status = DeviceStatus.DOWN;
        downEvent.timestamp = LocalDateTime.now();
        downEvent.latency = Duration.ofMillis(100);

        assertTrue(filter.filter(degradedEvent));
        assertTrue(filter.filter(downEvent));
    }

    @Test
    @DisplayName("Should pass filter when latency exceeds max threshold")
    void shouldPassWhenLatencyExceeded() {
        var highLatencyEvent = new HeartbeatEvent();
        highLatencyEvent.deviceId = "srv-1";
        highLatencyEvent.status = DeviceStatus.HEALTHY;
        highLatencyEvent.timestamp = LocalDateTime.now();
        highLatencyEvent.latency = Duration.ofMillis(600);

        assertTrue(filter.filter(highLatencyEvent));
    }

    @Test
    @DisplayName("Should drop event when device is HEALTHY and latency is below threshold")
    void shouldDropNormalEvent() {
        var normalEvent = new HeartbeatEvent();
        normalEvent.deviceId = "srv-1";
        normalEvent.status = DeviceStatus.HEALTHY;
        normalEvent.timestamp = LocalDateTime.now();
        normalEvent.latency = Duration.ofMillis(200);

        assertFalse(filter.filter(normalEvent));
    }
}

package io.github.sekelenao.quickstart.alert;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AlertTypeMapper Test")
class AlertTypeMapperTest {

    private final AlertTypeMapper mapper = new AlertTypeMapper();

    @Test
    @DisplayName("Should return HIGH_LATENCY when status is HEALTHY")
    void shouldReturnHighLatencyWhenHealthy() {
        var event = new HeartbeatEvent();
        event.status = DeviceStatus.HEALTHY;

        assertEquals("HIGH_LATENCY", mapper.map(event));
    }

    @Test
    @DisplayName("Should return DEVICE_DEGRADED when status is DEGRADED")
    void shouldReturnDeviceDegradedWhenDegraded() {
        var event = new HeartbeatEvent();
        event.status = DeviceStatus.DEGRADED;

        assertEquals("DEVICE_DEGRADED", mapper.map(event));
    }

    @Test
    @DisplayName("Should return DEVICE_DOWN when status is DOWN")
    void shouldReturnDeviceDownWhenDown() {
        var event = new HeartbeatEvent();
        event.status = DeviceStatus.DOWN;

        assertEquals("DEVICE_DOWN", mapper.map(event));
    }
}

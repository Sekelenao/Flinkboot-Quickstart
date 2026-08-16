package io.github.sekelenao.quickstart.alert;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AlertMessageMapper Test")
class AlertMessageMapperTest {

    private final AlertMessageMapper mapper = new AlertMessageMapper(Duration.ofMillis(500));

    @Test
    @DisplayName("Should build accurate alert message string")
    void shouldBuildAlertMessage() {
        var event = new HeartbeatEvent();
        event.deviceId = "srv-01";
        event.status = DeviceStatus.HEALTHY;
        event.latency = Duration.ofMillis(800);

        var message = mapper.map(event);

        assertEquals("Alert for device 'srv-01': status is HEALTHY, latency is 800ms (threshold: 500ms)", message);
    }
}

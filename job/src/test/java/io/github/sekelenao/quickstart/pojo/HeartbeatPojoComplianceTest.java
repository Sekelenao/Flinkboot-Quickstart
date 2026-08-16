package io.github.sekelenao.quickstart.pojo;

import io.github.sekelenao.flinkboot.test.api.FlinkbootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Heartbeat Models POJO Compliance")
class HeartbeatPojoComplianceTest {

    @Test
    @DisplayName("HeartbeatEvent should comply with Flink POJO requirements and have zero Kryo fallback")
    void heartbeatEventShouldBePojoCompliant() {
        FlinkbootTest.assertPojo(HeartbeatEvent.class);
    }

    @Test
    @DisplayName("HeartbeatAlert should comply with Flink POJO requirements and have zero Kryo fallback")
    void heartbeatAlertShouldBePojoCompliant() {
        FlinkbootTest.assertPojo(HeartbeatAlert.class);
    }
}

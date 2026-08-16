package io.github.sekelenao.quickstart.alert;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;

import java.io.Serial;
import java.io.Serializable;

public class AlertTypeMapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public String map(HeartbeatEvent event) {
        if (event.status != null && event.status != DeviceStatus.HEALTHY) {
            return "DEVICE_" + event.status;
        }
        return "HIGH_LATENCY";
    }
}

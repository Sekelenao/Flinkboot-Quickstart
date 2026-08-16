package io.github.sekelenao.quickstart.operator;

import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.apache.flink.api.common.functions.FilterFunction;

import java.io.Serial;
import java.time.Duration;
import java.util.Objects;

public class HeartbeatAlertFilter implements FilterFunction<HeartbeatEvent> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Duration maxLatency;

    public HeartbeatAlertFilter(Duration maxLatency) {
        this.maxLatency = Objects.requireNonNull(maxLatency, "maxLatency must not be null");
    }

    @Override
    public boolean filter(HeartbeatEvent event) {
        if (event == null) {
            return false;
        }
        var isUnhealthy = event.status != null && event.status != DeviceStatus.HEALTHY;
        var isLatencyExceeded = event.latency != null && event.latency.compareTo(maxLatency) > 0;
        return isUnhealthy || isLatencyExceeded;
    }
}

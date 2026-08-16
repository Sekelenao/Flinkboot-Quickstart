package io.github.sekelenao.quickstart.alert;

import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

public class AlertMessageMapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Duration maxLatency;
    private final LatencyTextMapper latencyTextMapper;

    public AlertMessageMapper(Duration maxLatency) {
        this(maxLatency, new LatencyTextMapper());
    }

    public AlertMessageMapper(Duration maxLatency, LatencyTextMapper latencyTextMapper) {
        this.maxLatency = Objects.requireNonNull(maxLatency, "maxLatency must not be null");
        this.latencyTextMapper = Objects.requireNonNull(latencyTextMapper, "latencyTextMapper must not be null");
    }

    public String map(HeartbeatEvent event) {
        var latencyText = latencyTextMapper.map(event.latency);
        return String.format(
                "Alert for device '%s': status is %s, latency is %s (threshold: %sms)",
                event.deviceId,
                event.status,
                latencyText,
                maxLatency.toMillis()
        );
    }
}

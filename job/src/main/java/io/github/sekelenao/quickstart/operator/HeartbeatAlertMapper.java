package io.github.sekelenao.quickstart.operator;

import io.github.sekelenao.quickstart.alert.AlertMessageMapper;
import io.github.sekelenao.quickstart.alert.AlertTypeMapper;
import io.github.sekelenao.quickstart.pojo.HeartbeatAlert;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.apache.flink.api.common.functions.OpenContext;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.io.Serial;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class HeartbeatAlertMapper extends RichMapFunction<HeartbeatEvent, HeartbeatAlert> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Duration maxLatency;

    private transient AlertTypeMapper alertTypeMapper;
    private transient AlertMessageMapper alertMessageMapper;

    public HeartbeatAlertMapper(Duration maxLatency) {
        this.maxLatency = Objects.requireNonNull(maxLatency, "maxLatency must not be null");
    }

    @Override
    public void open(OpenContext openContext) {
        this.alertTypeMapper = new AlertTypeMapper();
        this.alertMessageMapper = new AlertMessageMapper(maxLatency);
    }

    @Override
    public HeartbeatAlert map(HeartbeatEvent event) {
        if (alertTypeMapper == null) {
            this.alertTypeMapper = new AlertTypeMapper();
        }
        if (alertMessageMapper == null) {
            this.alertMessageMapper = new AlertMessageMapper(maxLatency);
        }

        var alert = new HeartbeatAlert();
        alert.deviceId = event.deviceId;
        alert.alertType = alertTypeMapper.map(event);
        alert.message = alertMessageMapper.map(event);
        alert.detectedAt = LocalDateTime.now();
        alert.latency = event.latency;
        return alert;
    }
}

package io.github.sekelenao.quickstart.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.flinkboot.core.api.typing.time.DurationTypeInfoFactory;
import io.github.sekelenao.flinkboot.core.api.typing.time.LocalDateTimeTypeInfoFactory;
import org.apache.flink.api.common.typeinfo.TypeInfo;

import java.time.Duration;
import java.time.LocalDateTime;

public class HeartbeatAlert {

    @JsonProperty("deviceId")
    public String deviceId;

    @JsonProperty("alertType")
    public String alertType;

    @JsonProperty("message")
    public String message;

    @JsonProperty("detectedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TypeInfo(LocalDateTimeTypeInfoFactory.class)
    public LocalDateTime detectedAt;

    @JsonProperty("latency")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TypeInfo(DurationTypeInfoFactory.class)
    public Duration latency;
}

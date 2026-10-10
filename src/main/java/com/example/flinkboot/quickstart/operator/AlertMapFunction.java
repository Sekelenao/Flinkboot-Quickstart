package com.example.flinkboot.quickstart.operator;

import com.example.flinkboot.quickstart.event.SpaceCargo;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Objects;

public class AlertMapFunction implements MapFunction<SpaceCargo, String> {

    private final String alertPrefix;

    public AlertMapFunction(String alertPrefix) {
        this.alertPrefix = Objects.requireNonNull(alertPrefix);
    }

    @Override
    public String map(SpaceCargo cargo) {
        return alertPrefix + cargo.content;
    }
}

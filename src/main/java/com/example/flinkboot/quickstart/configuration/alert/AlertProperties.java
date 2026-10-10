package com.example.flinkboot.quickstart.configuration.alert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public final class AlertProperties implements Serializable {

    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final Integer parallelism;

    @NotBlank
    private final String alertPrefix;

    @JsonCreator
    public AlertProperties(
        @JsonProperty("name") String name,
        @JsonProperty("parallelism") Integer parallelism,
        @JsonProperty("alert-prefix") String alertPrefix
    ) {
        this.name = name;
        this.parallelism = parallelism;
        this.alertPrefix = alertPrefix;
    }

    public String name() {
        return name;
    }

    public int parallelism() {
        return parallelism;
    }

    public String alertPrefix() {
        return alertPrefix;
    }
}

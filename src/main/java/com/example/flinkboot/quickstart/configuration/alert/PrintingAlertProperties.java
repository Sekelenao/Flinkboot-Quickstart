package com.example.flinkboot.quickstart.configuration.alert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public final class PrintingAlertProperties implements Serializable {

    @NotBlank
    private final String name;

    @JsonCreator
    public PrintingAlertProperties(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}

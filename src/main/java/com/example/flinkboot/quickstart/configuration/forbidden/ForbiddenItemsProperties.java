package com.example.flinkboot.quickstart.configuration.forbidden;

import com.example.flinkboot.quickstart.event.CargoItem;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public final class ForbiddenItemsProperties implements Serializable {

    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final Integer parallelism;

    @NotNull
    private final List<CargoItem> items;

    @JsonCreator
    public ForbiddenItemsProperties(
        @JsonProperty("name") String name,
        @JsonProperty("parallelism") Integer parallelism,
        @JsonProperty("items") List<CargoItem> items
    ) {
        this.name = name;
        this.parallelism = parallelism;
        this.items = items;
    }

    public String name() {
        return name;
    }

    public int parallelism() {
        return parallelism;
    }

    public List<CargoItem> items() {
        return Collections.unmodifiableList(items);
    }
}

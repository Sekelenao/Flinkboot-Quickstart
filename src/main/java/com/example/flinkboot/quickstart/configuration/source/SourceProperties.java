package com.example.flinkboot.quickstart.configuration.source;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.OptionalLong;

public final class SourceProperties implements Serializable {

    @NotBlank
    private final String name;

    @NotNull
    @Positive
    private final Integer parallelism;

    @NotNull
    @Positive
    private final Double ratePerSecond;

    @Positive
    private final Long totalRecords;

    @NotNull
    @Positive
    private final Integer maxItemsPerCargo;

    @JsonCreator
    public SourceProperties(
        @JsonProperty("name") String name,
        @JsonProperty("parallelism") Integer parallelism,
        @JsonProperty("rate-per-second") Double ratePerSecond,
        @JsonProperty("total-records") Long totalRecords,
        @JsonProperty("max-items-per-cargo") Integer maxItemsPerCargo
    ) {
        this.name = name;
        this.parallelism = parallelism;
        this.ratePerSecond = ratePerSecond;
        this.totalRecords = totalRecords;
        this.maxItemsPerCargo = maxItemsPerCargo;
    }

    public String name() {
        return name;
    }

    public int parallelism() {
        return parallelism;
    }

    public double ratePerSecond() {
        return ratePerSecond;
    }

    public OptionalLong totalRecords() {
        if (totalRecords == null) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(totalRecords);
    }

    public int maxItemsPerCargo() {
        return maxItemsPerCargo;
    }
}

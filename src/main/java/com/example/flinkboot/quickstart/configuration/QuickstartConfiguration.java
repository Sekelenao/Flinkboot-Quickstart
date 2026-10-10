package com.example.flinkboot.quickstart.configuration;

import com.example.flinkboot.quickstart.configuration.alert.AlertProperties;
import com.example.flinkboot.quickstart.configuration.forbidden.ForbiddenItemsProperties;
import com.example.flinkboot.quickstart.configuration.source.SourceProperties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.flinkboot.core.api.properties.JobProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public final class QuickstartConfiguration implements Serializable {

    @Valid
    @NotNull
    private final JobProperties job;

    @Valid
    @NotNull
    private final SourceProperties source;

    @Valid
    @NotNull
    private final ForbiddenItemsProperties forbiddenItems;

    @Valid
    @NotNull
    private final AlertProperties alert;

    @JsonCreator
    public QuickstartConfiguration(
        @JsonProperty("job") JobProperties job,
        @JsonProperty("source") SourceProperties source,
        @JsonProperty("forbidden-items") ForbiddenItemsProperties forbiddenItems,
        @JsonProperty("alert") AlertProperties alert
    ) {
        this.job = job;
        this.source = source;
        this.forbiddenItems = forbiddenItems;
        this.alert = alert;
    }

    public JobProperties job() {
        return job;
    }

    public SourceProperties source() {
        return source;
    }

    public ForbiddenItemsProperties forbiddenItems() {
        return forbiddenItems;
    }

    public AlertProperties alert() {
        return alert;
    }
}

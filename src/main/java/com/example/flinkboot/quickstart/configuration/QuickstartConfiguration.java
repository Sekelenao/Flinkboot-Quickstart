package com.example.flinkboot.quickstart.configuration;

import com.example.flinkboot.quickstart.configuration.alert.AlertProperties;
import com.example.flinkboot.quickstart.configuration.alert.PrintingAlertProperties;
import com.example.flinkboot.quickstart.configuration.forbidden.ForbiddenItemsProperties;
import com.example.flinkboot.quickstart.configuration.source.SourceProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
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

    @Valid
    @NotNull
    private final PrintingAlertProperties printingAlert;

    @JsonCreator
    public QuickstartConfiguration(
        @JsonProperty("job") JobProperties job,
        @JsonProperty("source") SourceProperties source,
        @JsonProperty("forbidden-items") ForbiddenItemsProperties forbiddenItems,
        @JsonProperty("alert") AlertProperties alert,
        @JsonProperty("printing-alert-sink") @JsonAlias("printing-alert") PrintingAlertProperties printingAlert
    ) {
        this.job = job;
        this.source = source;
        this.forbiddenItems = forbiddenItems;
        this.alert = alert;
        this.printingAlert = printingAlert;
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

    public PrintingAlertProperties printingAlert() {
        return printingAlert;
    }
}

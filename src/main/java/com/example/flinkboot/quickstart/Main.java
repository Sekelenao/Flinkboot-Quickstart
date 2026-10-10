package com.example.flinkboot.quickstart;

import com.example.flinkboot.quickstart.configuration.QuickstartConfiguration;
import com.example.flinkboot.quickstart.event.SpaceCargo;
import com.example.flinkboot.quickstart.operator.AlertMapFunction;
import com.example.flinkboot.quickstart.operator.ForbiddenItemsFilterFunction;
import com.example.flinkboot.quickstart.source.SpaceCargoGenerator;
import io.github.sekelenao.flinkboot.core.api.Flinkboot;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;

public class Main {

    public static void main(String[] args) throws Exception {
        // 1. Initialize Flinkboot with CLI arguments
        var boot = Flinkboot.initialize(args);

        // 2. Load and validate YAML configuration fail-fast into a type-safe model
        var configuration = boot.configuration(QuickstartConfiguration.class);

        // 3. Pre-configured environment: local WebUI on port 8080 initialized automatically
        @SuppressWarnings("resource") var environment = boot.executionEnvironment(configuration.job());

        // 4. Fake data source
        var sourceProperties = configuration.source();
        var generator = new DataGeneratorSource<>(
            new SpaceCargoGenerator(sourceProperties.maxItemsPerCargo()),
            sourceProperties.totalRecords().orElse(Long.MAX_VALUE),
            RateLimiterStrategy.perSecond(sourceProperties.ratePerSecond()),
            TypeInformation.of(SpaceCargo.class)
        );

        var cargoStream = environment
            .fromSource(generator, WatermarkStrategy.noWatermarks(), sourceProperties.name())
            .setParallelism(sourceProperties.parallelism());

        // 5. Filter forbidden cargo items
        var filterProperties = configuration.forbiddenItems();
        var filteredStream = cargoStream
            .filter(new ForbiddenItemsFilterFunction(filterProperties.items()))
            .name(filterProperties.name())
            .setParallelism(filterProperties.parallelism());

        // 6. Map to customs quarantine alert
        var alertProperties = configuration.alert();
        var alertStream = filteredStream
            .map(new AlertMapFunction(alertProperties.alertPrefix()))
            .name(alertProperties.name())
            .setParallelism(alertProperties.parallelism());

        // 7. Print intercepted alerts
        alertStream.print();

        // 8. Execute pipeline
        environment.execute(configuration.job().name());
    }
}

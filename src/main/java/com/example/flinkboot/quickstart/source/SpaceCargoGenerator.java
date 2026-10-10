package com.example.flinkboot.quickstart.source;

import com.example.flinkboot.quickstart.event.CargoItem;
import com.example.flinkboot.quickstart.event.SpaceCargo;
import org.apache.flink.connector.datagen.source.GeneratorFunction;

import java.security.SecureRandom;
import java.util.ArrayList;

public class SpaceCargoGenerator implements GeneratorFunction<Long, SpaceCargo> {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final int maxItemsPerCargo;

    public SpaceCargoGenerator(int maxItemsPerCargo) {
        this.maxItemsPerCargo = maxItemsPerCargo;
    }

    @Override
    public SpaceCargo map(Long value) {
        var cargo = new SpaceCargo();
        cargo.content = new ArrayList<>();
        int count = RANDOM.nextInt(maxItemsPerCargo) + 1;
        for (int i = 0; i < count; i++) {
            cargo.content.add(CargoItem.values()[RANDOM.nextInt(CargoItem.values().length)]);
        }
        return cargo;
    }
}

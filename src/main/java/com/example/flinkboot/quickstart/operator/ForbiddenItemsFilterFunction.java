package com.example.flinkboot.quickstart.operator;

import com.example.flinkboot.quickstart.event.CargoItem;
import com.example.flinkboot.quickstart.event.SpaceCargo;
import org.apache.flink.api.common.functions.FilterFunction;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ForbiddenItemsFilterFunction implements FilterFunction<SpaceCargo> {

    private final Set<CargoItem> forbiddenItems;

    public ForbiddenItemsFilterFunction(List<CargoItem> forbiddenItems) {
        Objects.requireNonNull(forbiddenItems);
        this.forbiddenItems = new HashSet<>(forbiddenItems);
    }

    @Override
    public boolean filter(SpaceCargo cargo) {
        for (int i = 0; i < cargo.content.size(); i++) {
            if (forbiddenItems.contains(cargo.content.get(i))) {
                return true;
            }
        }
        return false;
    }
}

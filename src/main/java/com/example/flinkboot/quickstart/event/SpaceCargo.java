package com.example.flinkboot.quickstart.event;

import io.github.sekelenao.flinkboot.core.api.typing.collection.ListTypeInfoFactory;
import org.apache.flink.api.common.typeinfo.TypeInfo;

import java.util.List;

public final class SpaceCargo {

    @TypeInfo(ListTypeInfoFactory.class)
    public List<CargoItem> content;

}

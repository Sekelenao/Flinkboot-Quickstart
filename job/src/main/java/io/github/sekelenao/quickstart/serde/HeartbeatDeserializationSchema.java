package io.github.sekelenao.quickstart.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.io.Serial;

public class HeartbeatDeserializationSchema implements DeserializationSchema<HeartbeatEvent> {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient ObjectMapper objectMapper;

    @Override
    public void open(InitializationContext context) {
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    @Override
    public HeartbeatEvent deserialize(byte[] message) throws IOException {
        return objectMapper.readValue(message, HeartbeatEvent.class);
    }

    @Override
    public boolean isEndOfStream(HeartbeatEvent nextElement) {
        return false;
    }

    @Override
    public TypeInformation<HeartbeatEvent> getProducedType() {
        return TypeInformation.of(HeartbeatEvent.class);
    }
}

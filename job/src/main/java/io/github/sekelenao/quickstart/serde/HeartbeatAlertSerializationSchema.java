package io.github.sekelenao.quickstart.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.sekelenao.quickstart.pojo.HeartbeatAlert;
import org.apache.flink.api.common.serialization.SerializationSchema;

import java.io.Serial;
import java.io.UncheckedIOException;

public class HeartbeatAlertSerializationSchema implements SerializationSchema<HeartbeatAlert> {

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
    public byte[] serialize(HeartbeatAlert element) {
        try {
            return objectMapper.writeValueAsBytes(element);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Failed to serialize HeartbeatAlert to JSON", e);
        }
    }
}

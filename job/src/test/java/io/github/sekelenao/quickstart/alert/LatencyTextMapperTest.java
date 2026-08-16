package io.github.sekelenao.quickstart.alert;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("LatencyTextMapper Test")
class LatencyTextMapperTest {

    private final LatencyTextMapper mapper = new LatencyTextMapper();

    @Test
    @DisplayName("Should format valid duration to millis string")
    void shouldFormatDurationToMillis() {
        assertEquals("500ms", mapper.map(Duration.ofMillis(500)));
        assertEquals("1250ms", mapper.map(Duration.ofMillis(1250)));
    }

    @Test
    @DisplayName("Should return N/A when latency is null")
    void shouldReturnNAWhenNull() {
        assertEquals("N/A", mapper.map(null));
    }
}

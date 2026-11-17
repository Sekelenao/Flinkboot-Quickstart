package com.example.flinkboot.quickstart.configuration;

import com.flinkboot.core.api.Flinkboot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.flinkboot.test.api.assertion.FlinkbootAssertions.assertThat;

final class QuickstartConfigurationTest {

    @Test
    @DisplayName("Configuration should be readable and serializable")
    void testConfigurationSerialization() throws IOException {
        var configuration = Flinkboot.initialize().configuration(QuickstartConfiguration.class);
        assertThat(configuration).isSerializable();
    }

}

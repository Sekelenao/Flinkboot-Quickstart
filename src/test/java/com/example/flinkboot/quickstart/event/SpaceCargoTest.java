package com.example.flinkboot.quickstart.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.github.sekelenao.flinkboot.test.api.assertion.FlinkbootAssertions.assertThat;

final class SpaceCargoTest {

    @Test
    @DisplayName("Space cargo is POJO compliant")
    void testSpaceShuttleIsPojoCompliant() {
        assertThat(SpaceCargo.class).isPojo();
    }

}

package io.github.sekelenao.quickstart.alert;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

public class LatencyTextMapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public String map(Duration latency) {
        if (latency != null) {
            return latency.toMillis() + "ms";
        }
        return "N/A";
    }
}

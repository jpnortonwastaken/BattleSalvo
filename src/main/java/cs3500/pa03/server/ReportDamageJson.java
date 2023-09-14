package cs3500.pa03.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * @param theirshots -
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<Coord> theirshots
) {
}

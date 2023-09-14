package cs3500.pa03.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * @param oursuccess -
 */
public record SuccessfulHitsJson(
    @JsonProperty("coordinates") List<Coord> oursuccess
) {
}

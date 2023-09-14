package cs3500.pa03.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @param ships -
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> ships
) {
}

package cs3500.pa03.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;


/**
 * @param width  -
 * @param height -
 * @param specs  -
 */
// have a list of ships and then use the ship adapter to turn into usable JSON
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> specs
) {
}

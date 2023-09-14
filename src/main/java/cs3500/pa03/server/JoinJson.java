package cs3500.pa03.server;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @param name -
 * @param type -
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType type
) {
}

package cs3500.pa03.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.Ship;

/**
 * used to adapt ships to be in JSON form
 */
public class ShipAdapter {

  private Coord start;
  private int length;
  private Direction direction;


  /**
   * @param myShip -
   */
  public ShipAdapter(Ship myShip) {
    this.start = myShip.getCoords().get(0);
    this.length = myShip.getType().getSize();
    this.direction = myShip.getDirection();
  }

  /**
   * @param start -
   * @param length -
   * @param direction -
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") Coord start,
      @JsonProperty("length") int length,
      @JsonProperty("direction") Direction direction) {

    this.start = start;
    this.length = length;
    this.direction = direction;
  }

  public Coord getCoord() {
    return start;
  }

  public Direction getDirection() {
    return direction;
  }

  public int getLength() {
    return length;
  }
}

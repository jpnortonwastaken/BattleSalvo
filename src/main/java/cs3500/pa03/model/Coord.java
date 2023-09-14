package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class that represents a coordinate point
 */
public class Coord {
  private int xcoord;
  private int ycoord;

  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.xcoord = x;
    this.ycoord = y;
  }

  /**
   * Gets the x position of the coordinate
   *
   * @return the x position of the coordinate
   */
  public int getX() {
    return this.xcoord;
  }

  /**
   * Gets the y position of the coordinate
   *
   * @return the y position of the coordinate
   */
  public int getY() {
    return this.ycoord;
  }
}

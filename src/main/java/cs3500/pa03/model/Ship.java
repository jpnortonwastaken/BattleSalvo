package cs3500.pa03.model;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;

/**
 * Class that represents a single ship
 */
public class Ship {
  private ShipType type;

  private ArrayList<Coord> coords;

  private Direction direction;

  /**
   * Constructor for a ship
   *
   * @param type - the type of the ship
   * @param coords - the list of coordinates for the chip
   */
  public Ship(ShipType type, ArrayList<Coord> coords, Direction direction) {
    this.type = type;
    this.coords = coords;
    this.direction = direction;
  }

  /**
   * Gets the type of the ship
   *
   * @return ship type
   */
  public ShipType getType() {
    return this.type;
  }

  /**
   * Gets the coords of the ship
   *
   * @return the list of coords of the ship
   */
  public ArrayList<Coord> getCoords() {
    return this.coords;
  }

  /**
   * Gets the direction of the ship
   *
   * @return the direction of the ship
   */
  public Direction getDirection() {
    return this.direction;
  }
}

package cs3500.pa03.model;

/**
 * Enumeration for ship type
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private int size;

  ShipType(int size) {
    this.size = size;
  }

  public int getSize() {
    return this.size;
  }
}
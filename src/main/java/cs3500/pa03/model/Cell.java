package cs3500.pa03.model;

/**
 * Enumeration for a Cell
 */
public enum Cell {
  CARRIER("C"),
  BATTLESHIP("B"),
  DESTROYER("D"),
  SUBMARINE("S"),
  HIT("+"),
  MISS("0"),
  SUNK("X"),
  WATER(".");

  private String value;

  Cell(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}

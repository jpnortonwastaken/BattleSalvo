package cs3500.pa03.model;

/**
 * Direction
 */
public enum Direction {
  HORIZONTAL,
  VERTICAL;

  /**
   * @param bool -
   * @return -
   */
  public static Direction converter(boolean bool) {
    Direction out;
    if (bool) {
      out = HORIZONTAL;
    } else {
      out = VERTICAL;
    }
    return out;
  }
}

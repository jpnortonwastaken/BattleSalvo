package cs3500.pa03.model;

/**
 * Enumeration for the GameResult
 */
public enum GameResult {
  WIN("won"),
  LOSE("lost"),
  DRAW("tied");

  private String result;

  /**
   * Constructor for GameResult
   *
   * @param result - the result of the GameResult
   */
  GameResult(String result) {
    this.result = result;
  }

  public String getResult() {
    return this.result;
  }
}

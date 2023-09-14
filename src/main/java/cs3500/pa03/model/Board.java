package cs3500.pa03.model;

/**
 * A class that represents a Board
 */
public class Board {
  private Cell[][] board;

  /**
   * Constructor for board
   *
   * @param height - the height of the board
   * @param width  - the width of the board
   */
  public Board(int height, int width) {
    this.board = new Cell[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.board[i][j] = Cell.WATER;
      }
    }
  }

  /**
   * Gets the cell given a row and column
   *
   * @param row - the row of the cell
   * @param col - the column of the cell
   * @return the cell
   */
  public Cell getCell(int row, int col) {
    return this.board[row][col];
  }

  /**
   * Sets the cell to a given value
   *
   * @param row   - the row of the cell to change
   * @param col   - the column of the cell to change
   * @param value - the value to set it to
   */
  public void setCell(int row, int col, Cell value) {
    this.board[row][col] = value;
  }

  /**
   * Gets the board
   *
   * @return the board
   */
  public Cell[][] getBoard() {
    return this.board;
  }

  /**
   * Determines if the given ship placement is valid
   *
   * @param row          - the row position of the ship
   * @param col          - the column position of the ship
   * @param shipType     - the type of ship
   * @param isHorizontal - whether the ship is placed horizontally
   * @return if the ship placement is valid
   */
  public boolean isValidPlacement(int row, int col, ShipType shipType, boolean isHorizontal) {
    int shipSize = shipType.getSize();

    if (isHorizontal) {
      if (col + shipSize > this.board[0].length) {
        return false;
      }
    } else {
      if (row + shipSize > this.board.length) {
        return false;
      }
    }

    for (int i = 0; i < shipSize; i++) {
      if (isHorizontal) {
        if (this.board[row][col + i] != Cell.WATER) {
          return false;
        }
      } else {
        if (this.board[row + i][col] != Cell.WATER) {
          return false;
        }
      }
    }

    return true;
  }
}

package cs3500.pa03.view;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Map;

/**
 * Interface for the view
 */
public interface InterfaceView {
  /**
   * Displays a welcome message to the user
   */
  public void displayWelcomeMessage();

  /**
   * Displays a question to the user asking
   * for the dimensions of the board
   */
  public int[] askDimensions();

  /**
   * Displays a message to the user
   * saying their dimensions are invalid
   */
  public int[] invalidDimensions();

  /**
   * Displays a question to the user asking
   * for their fleet
   */
  public Map<ShipType, Integer> askFleet(int size);

  /**
   * Displays a message to the user
   * saying their fleet is invalid
   */
  public Map<ShipType, Integer> invalidFleet(int size);

  /**
   * Displays a question to the user asking
   * for their shots
   */
  public ArrayList<Coord> askShots(int num);

  /**
   * Displays a board to the user
   *
   * @param board - the board to be shown
   */
  public void displayBoard(Cell[][] board, String player);

  /**
   * Displays a final end message to the user
   */
  public void displayEndMessage(GameResult result);
}

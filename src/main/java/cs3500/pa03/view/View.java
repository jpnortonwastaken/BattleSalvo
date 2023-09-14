package cs3500.pa03.view;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.InterfaceView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A class representing a view
 */
public class View implements InterfaceView {
  private Appendable appendable;
  private Readable readable;
  private Scanner scanner;

  /**
   * Constructor for view
   *
   * @param appendable - the appendable used in the class
   * @param readable - the readable used in the class
   */
  public View(Appendable appendable, Readable readable) {
    this.appendable = appendable;
    this.readable = readable;
    scanner = new Scanner(readable);
  }

  /**
   * Displays a welcome message to the user
   */
  public void displayWelcomeMessage() {
    try {
      appendable.append("Hello! Welcome to the OOD BattleSalvo Game!\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Displays a question to the user asking
   * for the dimensions of the board
   */
  public int[] askDimensions() {
    int[] dimensions = new int[2];
    try {
      appendable.append("Please enter a valid height and width below:\n"
          + "------------------------------------------------------\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    dimensions[0] = scanner.nextInt();
    dimensions[1] = scanner.nextInt();
    return dimensions;
  }

  /**
   * Displays a message to the user
   * saying their dimensions are invalid
   */
  public int[] invalidDimensions() {
    int[] dimensions = new int[2];
    try {
      appendable.append("------------------------------------------------------\n"
          + "Uh Oh! You've entered invalid dimensions. "
          + "Please remember that the height and width\n"
          + "of the game must be in the range [6, 15], inclusive. Try again!\n"
          + "------------------------------------------------------\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    dimensions[0] = scanner.nextInt();
    dimensions[1] = scanner.nextInt();
    return dimensions;
  }

  /**
   * Displays a question to the user asking
   * for their fleet
   */
  public Map<ShipType, Integer> askFleet(int size) {
    Map<ShipType, Integer> fleet = new HashMap<>();
    try {
      appendable.append("------------------------------------------------------\n"
          + "Please enter your fleet in the order "
          + "[Carrier, Battleship, Destroyer, Submarine].\n"
          + "Remember, your fleet may not exceed size " + size + ".\n"
          + "----------------------------------------"
          + "----------------------------------------\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    fleet.put(ShipType.CARRIER, scanner.nextInt());
    fleet.put(ShipType.BATTLESHIP, scanner.nextInt());
    fleet.put(ShipType.DESTROYER, scanner.nextInt());
    fleet.put(ShipType.SUBMARINE, scanner.nextInt());
    return fleet;
  }

  /**
   * Displays a message to the user
   * saying their fleet is invalid
   */
  public Map<ShipType, Integer> invalidFleet(int size) {
    Map<ShipType, Integer> fleet = new HashMap<>();
    try {
      appendable.append("------------------------------------------------------\n"
          + "Uh Oh! You've entered invalid fleet sizes.\n"
          + "Please enter your fleet in the order "
          + "[Carrier, Battleship, Destroyer, Submarine].\n"
          + "Remember, your fleet may not exceed size " + size + ".\n"
          + "----------------------------------------"
          + "----------------------------------------\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    fleet.put(ShipType.CARRIER, scanner.nextInt());
    fleet.put(ShipType.BATTLESHIP, scanner.nextInt());
    fleet.put(ShipType.DESTROYER, scanner.nextInt());
    fleet.put(ShipType.SUBMARINE, scanner.nextInt());
    return fleet;
  }

  /**
   * Displays a question to the user asking
   * for their shots
   */
  public ArrayList<Coord> askShots(int num) {
    ArrayList<Coord> coordList = new ArrayList<>();
    try {
      appendable.append("Please Enter " + num + " Shots:\n"
          + "---------------------------------"
          + "---------------------------------\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    for (int i = 0; i < num; i++) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      coordList.add(new Coord(x, y));
    }

    return coordList;
  }

  /**
   * Displays a board to the user
   *
   * @param board - the board to be shown
   */
  public void displayBoard(Cell[][] board, String player) {
    try {
      if (player.equals("manual")) {
        appendable.append("Your Board:\n");
        for (Cell[] row : board) {
          StringBuilder rowString = new StringBuilder();
          for (Cell cell : row) {
            rowString.append(cell.getValue()).append(" ");
          }
          appendable.append(rowString.toString().trim()).append("\n");
        }
      }
      if (player.equals("ai")) {
        appendable.append("Opponent Board Data:\n");
        for (Cell[] row : board) {
          StringBuilder rowString = new StringBuilder();
          for (Cell cell : row) {
            if ("CBDS".contains(cell.getValue())) {
              rowString.append(Cell.WATER.getValue()).append(" ");
            } else {
              rowString.append(cell.getValue()).append(" ");
            }
          }
          appendable.append(rowString.toString().trim()).append("\n");
        }
      }
      appendable.append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Displays a final end message to the user
   *
   * @param result - the game result
   */
  public void displayEndMessage(GameResult result) {
    try {
      appendable.append("You " + result.getResult() + "." + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

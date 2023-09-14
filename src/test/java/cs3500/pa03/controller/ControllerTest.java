package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.MockRandom;
import cs3500.pa03.MockReadable;
import cs3500.pa03.Randomable;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Cell;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.InterfaceView;
import cs3500.pa03.view.View;
import org.junit.jupiter.api.Test;

/**
 * Test for controller class
 */
class ControllerTest {
  /**
   * Test for controller
   */
  @Test
  public void controllerTest() {
    Readable mockReadable = new MockReadable("60 6\n"
        + "6 6\n"
        + "50 1 1 1\n"
        + "1 1 1 1\n"
        + "0 0\n 1 0\n 2 0\n 3 0\n 4 0\n 5 0\n"
        + "0 1\n 1 1\n 2 1\n 3 1\n 4 1\n 5 1\n"
        + "0 2\n 1 2\n 2 2\n 3 2\n 4 2\n 5 2\n"
        + "0 3\n 1 3\n 2 3\n 3 3\n 4 3\n 5 3\n"
        + "0 4\n 1 4\n 2 4\n 3 4\n 4 4\n 5 4\n"
        + "0 5\n 1 5\n 2 5\n 3 5\n 4 5\n 5 5\n");
    StringBuilder stringBuilder = new StringBuilder();
    InterfaceView view = new View(stringBuilder, mockReadable);
    Randomable rand = new MockRandom();
    Player manual = new ManualPlayer(rand, view);
    manual.name();
    manual.endGame(GameResult.DRAW, "DRAW");
    Controller controller = new Controller(rand, stringBuilder, mockReadable);
    Controller controller1 = new Controller(stringBuilder, mockReadable);
    String outputt = "Hello!";
    String output = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        +
        "Please enter a valid height and width below:\n"
        +
        "------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Uh Oh! You've entered invalid dimensions. Please remember that the height and width\n"
        +
        "of the game must be in the range [6, 15], inclusive. Try again!\n"
        +
        "------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        +
        "Remember, your fleet may not exceed size 6.\n"
        +
        "--------------------------------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Uh Oh! You've entered invalid fleet sizes.\n"
        +
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        +
        "Remember, your fleet may not exceed size 6.\n"
        +
        "--------------------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        ". B B B B B\n"
        +
        ". B B B B B\n"
        +
        "C C C C C C\n"
        +
        ". . . S S S\n"
        +
        "C C C C C C\n"
        +
        ". . D D D D\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". B B B + B\n"
        +
        "C C C C C C\n"
        +
        ". . 0 S S S\n"
        +
        "C C C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". B B B + +\n"
        +
        "C C C C C C\n"
        +
        ". . 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B B + +\n"
        +
        "C C C C C C\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        "X X X X X X\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C + C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        "X X X X X X\n"
        +
        "0 0 X X X 0\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C + C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "You won.\n";

    controller.start();
    assertTrue(stringBuilder.toString().contains(outputt));
  }



  @Test
  public void controllerTest1() {
    Readable mockReadable = new MockReadable("60 6\n"
        + "6 6\n"
        + "50 1 1 1\n"
        + "0 1 1 1\n"
        + "1 1 1 1\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n");
    StringBuilder stringBuilder = new StringBuilder();
    Randomable rand = new MockRandom();
    Controller controller = new Controller(rand, stringBuilder, mockReadable);
    Controller controller1 = new Controller(stringBuilder, mockReadable);
    String outputt = "Hello!";
    String output = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        +
        "Please enter a valid height and width below:\n"
        +
        "------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Uh Oh! You've entered invalid dimensions. Please remember that the height and width\n"
        +
        "of the game must be in the range [6, 15], inclusive. Try again!\n"
        +
        "------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        +
        "Remember, your fleet may not exceed size 6.\n"
        +
        "--------------------------------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Uh Oh! You've entered invalid fleet sizes.\n"
        +
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        +
        "Remember, your fleet may not exceed size 6.\n"
        +
        "--------------------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        ". B B B B B\n"
        +
        ". B B B B B\n"
        +
        "C C C C C C\n"
        +
        ". . . S S S\n"
        +
        "C C C C C C\n"
        +
        ". . D D D D\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". B B B + B\n"
        +
        "C C C C C C\n"
        +
        ". . 0 S S S\n"
        +
        "C C C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". B B B + +\n"
        +
        "C C C C C C\n"
        +
        ". . 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B B + +\n"
        +
        "C C C C C C\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        "X X X X X X\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C + C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        "X X X X X X\n"
        +
        "0 0 X X X 0\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C + C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "You won.\n";

    controller.start();
    assertTrue(stringBuilder.toString().contains(outputt));
  }

  @Test
  public void controllerTest2() {
    Readable mockReadable = new MockReadable("60 6\n"
        + "6 7\n"
        + "50 1 1 1\n"
        + "0 1 1 1\n"
        + "1 1 1 1\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n"
        + "0 0\n 0 0\n 0 0\n 0 0\n 0 0\n 0 0\n");
    StringBuilder stringBuilder = new StringBuilder();
    Randomable rand = new MockRandom();
    Controller controller = new Controller(rand, stringBuilder, mockReadable);
    Controller controller1 = new Controller(stringBuilder, mockReadable);
    String outputt = "Hello!";
    String output = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        +
        "Please enter a valid height and width below:\n"
        +
        "------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Uh Oh! You've entered invalid dimensions. Please remember that the height and width\n"
        +
        "of the game must be in the range [6, 15], inclusive. Try again!\n"
        +
        "------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        +
        "Remember, your fleet may not exceed size 6.\n"
        +
        "--------------------------------------------------------------------------------\n"
        +
        "------------------------------------------------------\n"
        +
        "Uh Oh! You've entered invalid fleet sizes.\n"
        +
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        +
        "Remember, your fleet may not exceed size 6.\n"
        +
        "--------------------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        ". B B B B B\n"
        +
        ". B B B B B\n"
        +
        "C C C C C C\n"
        +
        ". . . S S S\n"
        +
        "C C C C C C\n"
        +
        ". . D D D D\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". B B B + B\n"
        +
        "C C C C C C\n"
        +
        ". . 0 S S S\n"
        +
        "C C C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". B B B + +\n"
        +
        "C C C C C C\n"
        +
        ". . 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B B + +\n"
        +
        "C C C C C C\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        ". . . . . .\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C C C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        "X X X X X X\n"
        +
        ". . . . . .\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C + C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "Please Enter 6 Shots:\n"
        +
        "------------------------------------------------------------------\n"
        +
        "Opponent Board Data:\n"
        +
        "X X X X X X\n"
        +
        "X X X X X 0\n"
        +
        "X X X X X 0\n"
        +
        "0 X X X X 0\n"
        +
        "X X X X X X\n"
        +
        "0 0 X X X 0\n"
        +
        "\n"
        +
        "Your Board:\n"
        +
        "0 + B B B B\n"
        +
        ". + B + + +\n"
        +
        "C C C C C +\n"
        +
        ". 0 0 S S S\n"
        +
        "C + C C + C\n"
        +
        ". . D D + +\n"
        +
        "\n"
        +
        "You won.\n";

    controller.start();
    assertTrue(stringBuilder.toString().contains(outputt));
  }



  @Test
  public void boardTest() {
    Board board = new Board(6, 6);
    board.setCell(0, 0, Cell.CARRIER);
    assertFalse(board.isValidPlacement(6, 6, ShipType.CARRIER, true));
    assertFalse(board.isValidPlacement(6, 6, ShipType.CARRIER, false));
    assertFalse(board.isValidPlacement(0, 0, ShipType.CARRIER, false));
    assertFalse(board.isValidPlacement(0, 0, ShipType.CARRIER, true));
    assertEquals(Cell.CARRIER, board.getCell(0, 0));
  }




}
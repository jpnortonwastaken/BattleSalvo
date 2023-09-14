package cs3500.pa03.controller;

import cs3500.pa03.Randomable;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.View;
import java.util.List;
import java.util.Map;

/**
 * Class representing a controller
 */
public class Controller implements InterfaceController {
  private Player manualPlayer;
  private Player aiPlayer;
  private View view;
  private int width;
  private int height;
  private List<Ship> manualPlayerShips;
  private List<Ship> aiPlayerShips;

  /**
   * Constructor for a Controller
   *
   * @param appendable - the appendable to be used in the view
   * @param readable   - the readable to be used in the view
   */
  public Controller(Appendable appendable, Readable readable) {
    this.view = new View(appendable, readable);
    this.manualPlayer = new ManualPlayer(this.view);
    this.aiPlayer = new AiPlayer(this.view);
  }

  /**
   * Constructor for a Controller
   *
   * @param appendable - the appendable to be used in the view
   * @param readable   - the readable to be used in the view
   */
  public Controller(Randomable rand, Appendable appendable, Readable readable) {
    this.view = new View(appendable, readable);
    this.manualPlayer = new ManualPlayer(this.view);
    this.aiPlayer = new AiPlayer(rand, this.view);
  }

  /**
   * Starts the game
   */
  public void start() {
    view.displayWelcomeMessage();

    int[] dimensions = view.askDimensions();
    width = dimensions[0];
    height = dimensions[1];
    while (width < 6 || width > 15 || height < 6 || height > 15) {
      dimensions = view.invalidDimensions();
      width = dimensions[0];
      height = dimensions[1];
    }

    Map<ShipType, Integer> fleet = view.askFleet(Math.min(width, height));
    while (invalidFleet(fleet)) {
      fleet = view.invalidFleet(Math.min(width, height));
    }

    this.aiPlayerShips = this.aiPlayer.setup(height, width, fleet);
    this.manualPlayerShips = this.manualPlayer.setup(height, width, fleet);

    play();
  }

  /**
   * Gameplay loop
   */
  public void play() {
    while (!isGameOver()) {
      // AI player takes shots and Manual player reports the damage
      List<Coord> aiShots = this.aiPlayer.takeShots();
      List<Coord> hitsOnManual = this.manualPlayer.reportDamage(aiShots);

      // Manual player takes shots and AI player reports the damage
      List<Coord> manualShots = this.manualPlayer.takeShots();
      List<Coord> hitsOnAi = this.aiPlayer.reportDamage(manualShots);

      // Report back to each player the successful hits
      this.aiPlayer.successfulHits(hitsOnAi);
      this.manualPlayer.successfulHits(hitsOnManual);
    }
  }


  /**
   * Determines if the game is over
   *
   * @return true if the game is over
   */
  public boolean isGameOver() {
    if (this.aiPlayerShips.isEmpty() && this.manualPlayerShips.isEmpty()) {
      this.view.displayEndMessage(GameResult.DRAW);
      return true;
    } else if (this.aiPlayerShips.isEmpty()) {
      this.view.displayEndMessage(GameResult.WIN);
      return true;
    } else if (this.manualPlayerShips.isEmpty()) {
      this.view.displayEndMessage(GameResult.LOSE);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if an inputted fleet is invalid
   *
   * @param fleet - the fleet to be checked
   * @return if the given fleet is invalid
   */
  public boolean invalidFleet(Map<ShipType, Integer> fleet) {
    int sum = 0;
    boolean hasZero = false;
    for (Map.Entry<ShipType, Integer> entry : fleet.entrySet()) {
      if (entry.getValue() < 1) {
        hasZero = true;
        break;
      }
      sum += entry.getValue();
    }
    return sum > this.width || hasZero;
  }
}

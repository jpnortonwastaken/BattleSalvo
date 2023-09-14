package cs3500.pa03.controller;

import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * An interface for a controller
 */
public interface InterfaceController {
  /**
   * Starts the game
   */
  public void start();

  /**
   * Gameplay loop
   */
  public void play();

  /**
   * Determines if the game is over
   *
   * @return true if the game is over
   */
  public boolean isGameOver();

  /**
   * Checks if an inputted fleet is invalid
   *
   * @param fleet - the fleet to be checked
   * @return if the given fleet is invalid
   */
  public boolean invalidFleet(Map<ShipType, Integer> fleet);
}

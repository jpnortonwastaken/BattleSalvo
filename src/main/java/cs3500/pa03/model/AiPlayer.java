package cs3500.pa03.model;

import cs3500.pa03.RandomDecorator;
import cs3500.pa03.Randomable;
import cs3500.pa03.view.InterfaceView;
import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that represents an AI player
 */
public class AiPlayer implements Player {
  private InterfaceView view;
  private List<Ship> shipsList;
  private Board board;
  private List<Coord> allShots;
  private int width;
  private int height;
  private Randomable rand;
  private List<Coord> prevShots;
  private List<Coord> hitShots;
  private int shotsLeft;

  /**
   * Constructor for AiPlayer
   *
   * @param view - the view
   */
  public AiPlayer(InterfaceView view) {
    this.view = view;
    this.board = null;
    this.rand = new RandomDecorator();
    prevShots = new ArrayList<>();
    hitShots = new ArrayList<>();
    shotsLeft = -1;
  }

  /**
   * Constructor for AiPlayer
   *
   * @param view - the view
   */
  public AiPlayer(Randomable rand, InterfaceView view) {
    this.view = view;
    this.board = null;
    prevShots = new ArrayList<>();
    hitShots = new ArrayList<>();
    shotsLeft = -1;
    this.rand = rand;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "pa04-jack-jp";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.width = width;
    this.height = height;
    shotsLeft = width * height;
    List<Ship> listShipsTemp = new ArrayList<>();
    this.board = new Board(height, width);

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        ShipType type = entry.getKey();
        boolean validShip = false;
        boolean isHorizontal = false;
        ArrayList<Coord> coordsList = null;

        while (!validShip) {
          int randX;
          int randY;
          isHorizontal = rand.nextBoolean();

          if (isHorizontal) {
            randX = rand.nextInt(width - type.getSize() + 1);
            randY = rand.nextInt(height);
          } else {
            randX = rand.nextInt(width);
            randY = rand.nextInt(height - type.getSize() + 1);
          }

          coordsList = new ArrayList<>();

          for (int j = 0; j < type.getSize(); j++) {
            Coord coord;
            if (isHorizontal) {
              coord = new Coord(randX + j, randY);
            } else {
              coord = new Coord(randX, randY + j);
            }
            coordsList.add(coord);
          }

          validShip = this.board.isValidPlacement(randY, randX, type, isHorizontal)
              && this.canPlaceShip(coordsList, height, width, listShipsTemp);
        }

        listShipsTemp.add(new Ship(entry.getKey(), coordsList, Direction.converter(isHorizontal)));
      }
    }

    initializeBoard(listShipsTemp);

    this.view.displayBoard(this.board.getBoard(), "ai");

    this.shipsList = listShipsTemp;

    return listShipsTemp;
  }


  /**
   * Initializes the board
   *
   * @param ships - the list of ships to be placed
   * @return the board created
   */
  private void initializeBoard(List<Ship> ships) {
    for (Ship ship : ships) {
      for (Coord coord : ship.getCoords()) {
        switch (ship.getType()) {
          case CARRIER -> this.board.setCell(coord.getY(), coord.getX(), Cell.CARRIER);
          case BATTLESHIP -> this.board.setCell(coord.getY(), coord.getX(), Cell.BATTLESHIP);
          case DESTROYER -> this.board.setCell(coord.getY(), coord.getX(), Cell.DESTROYER);
          case SUBMARINE -> this.board.setCell(coord.getY(), coord.getX(), Cell.SUBMARINE);
          default -> {
          }
        }
      }
    }
  }


  /**
   * Checks if a ship can be placed
   *
   * @param coordsList - the list of coords
   * @param height     - the height of the board
   * @param width      - the width of the board
   * @param ships      - the list of ships
   * @return if a ship can be placed
   */
  private boolean canPlaceShip(List<Coord> coordsList, int height, int width, List<Ship> ships) {
    for (Coord coord : coordsList) {
      if (coord.getX() >= width || coord.getY() >= height || coord.getX() < 0 || coord.getY() < 0) {
        return false;
      }

      for (Ship ship : ships) {
        for (Coord c : ship.getCoords()) {
          if (c.getX() == coord.getX() && c.getY() == coord.getY()) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {

    ArrayList<Coord> coordList = new ArrayList<>();
    boolean targetMode = false;

    for (int i = 0; i < this.shipsList.size(); i++) {
      if (shotsLeft == 0) {
        break;
      }
      targetMode = false;
      if (containsShot(new Coord(width - 1, height - 1))
          || containsShot(new Coord(width - 2, height - 1))) {
        targetMode = true;
      }
      if (!targetMode) {
        Coord coord = new Coord(0, 0);
        boolean bool = false;
        for (int j = 0; j < this.height; j++) {
          for (int k = 0; k < this.width; k++) {
            if ((j + k) % 2 == 0) {
              coord = new Coord(k, j);
              if (!containsShot(coord)) {
                bool = true;
                break;
              }
            }
          }
          if (bool) {
            break;
          }
        }
        coordList.add(coord);
        this.prevShots.add(coord);
      } else {
        Coord coord = new Coord(0, 0);
        boolean bool = false;
        for (int j = 0; j < this.height; j++) {
          for (int k = 0; k < this.width; k++) {
            if ((j + k) % 2 == 1) {
              coord = new Coord(k, j);
              if (!containsShot(coord)) {
                bool = true;
                break;
              }
            }
          }
          if (bool) {
            break;
          }
        }
        coordList.add(coord);
        this.prevShots.add(coord);

      }
      shotsLeft -= 1;
    }

    return coordList;
  }

  private boolean containsShot(Coord coord) {
    for (Coord c : this.prevShots) {
      if (c.getX() == coord.getX() && c.getY() == coord.getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> shotsThatHit = new ArrayList<>();

    for (Coord shot : opponentShotsOnBoard) {
      for (Ship ship : this.shipsList) {
        for (Coord shipCoord : ship.getCoords()) {
          if (shipCoord.getX() == shot.getX() && shipCoord.getY() == shot.getY()) {
            shotsThatHit.add(shot);
            break;
          }
        }
      }
    }
    allShots = opponentShotsOnBoard;

    for (Coord shot : opponentShotsOnBoard) {
      // check if the shot was successful
      if (opponentShotsOnBoard.contains(shot)) {
        this.board.setCell(shot.getY(), shot.getX(), Cell.MISS);
      }
    }

    for (Coord shot : shotsThatHit) {
      // check if the shot was successful
      if (opponentShotsOnBoard.contains(shot)) {
        this.board.setCell(shot.getY(), shot.getX(), Cell.HIT);
      }
    }

    // this is where the board updates
    // the opponents shots on this board need to be used in order to see when a ship should be
    // removed
    this.removeSunkShips();
    return shotsThatHit;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    hitShots.addAll(shotsThatHitOpponentShips);
    // display the updated board
    this.view.displayBoard(this.board.getBoard(), "ai");
  }

  /**
   * Removes sunk ships from the list of ships
   * and marks the board with sunk ships
   */
  private void removeSunkShips() {
    List<Ship> shipsToRemove = new ArrayList<>();

    for (Ship ship : shipsList) {
      boolean allCoordinatesHit = true;

      for (Coord coord : ship.getCoords()) {
        int x = coord.getX();
        int y = coord.getY();

        if (board.getBoard()[y][x] != Cell.HIT) { // Indices swapped here
          allCoordinatesHit = false;
          break;
        }
      }
      if (allCoordinatesHit) {
        for (Coord coord : ship.getCoords()) {
          int x = coord.getX();
          int y = coord.getY();
          board.getBoard()[y][x] = Cell.SUNK; // Indices swapped here
        }
        shipsToRemove.add(ship);
      }
    }
    shipsList.removeAll(shipsToRemove);
  }


  /**
   * Notifies the player that the game is over.
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    view.displayEndMessage(result);
  }
}

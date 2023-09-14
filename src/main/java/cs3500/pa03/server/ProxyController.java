package cs3500.pa03.server;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Proxy controller
 */
public class ProxyController {

  /**
   * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
   */

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player aiplayer;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server   the socket connection to the server
   * @param aiplayer the instance of the aiplayer
   * @throws IOException if
   */
  public ProxyController(Socket server, Player aiplayer) throws IOException {
    // The serve to be used
    this.server = server;
    // the user guessing maybe
    // this is getting the servers input stream
    this.in = server.getInputStream();
    // this is showing the user info
    this.out = new PrintStream(server.getOutputStream());
    // player
    this.aiplayer = aiplayer;
  }


  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();


    switch (name) {
      case "join":
        handleJoin();
        break;
      case "setup":
        handleSetup(arguments);
        break;
      case "take-shots":
        handleTakeShots();
        break;
      case "report-damage":
        handleReportDamage(arguments);
        break;
      case "successful-hits":
        handleSuccessfulHits(arguments);
        break;
      case "end-game":
        handleWin(arguments);
        break;
      default:
        throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Parses the given message arguments as a GuessJSON type, asks the player to take a guess given
   * the hint from the server, and then serializes the player's new guess to Json and sends the
   * response to the server.
   */
  private void handleJoin() {
    String methodName = "join";
    String name = aiplayer.name();
    GameType type = GameType.SINGLE;

    JoinJson join = new JoinJson(name, type);
    JsonNode joinjson = JsonUtils.serializeRecord(join);
    MessageJson response = new MessageJson(methodName, joinjson);
    JsonNode responsejson = JsonUtils.serializeRecord(response);
    this.out.println(responsejson);
  }


  /**
   * Parses the given arguments as a WinJSON, notifies the player whether they won, and provides a
   * void response to the server. Should get the list of ships and then turns them into a fleet
   * of JSON usable code
   *
   * @param arguments the Json representation of a WinJSON
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson params;
    int width = 0;
    int height = 0;
    Map<ShipType, Integer> specs = new HashMap<>();
    try {
      params = this.mapper.convertValue(arguments, SetupJson.class);
      width = params.width();
      height = params.height();
      specs = params.specs();
    } catch (Exception e) {
      e.printStackTrace();
    }


    List<Ship> ships = this.aiplayer.setup(height, width, specs);
    List<ShipAdapter> fleet = adaptShips(ships);

    FleetJson fleetjson = new FleetJson(fleet);
    JsonNode fleetnode = JsonUtils.serializeRecord(fleetjson);

    String methodName = "setup";
    MessageJson response = new MessageJson(methodName, fleetnode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);

  }

  /**
   * @param ships -
   */
  private List<ShipAdapter> adaptShips(List<Ship> ships) {
    List<ShipAdapter> fleet = new ArrayList<>();
    for (Ship ship : ships) {
      fleet.add(new ShipAdapter(ship));
    }
    List<ShipAdapter> output = new ArrayList<>();
    for (ShipAdapter shipadapter : fleet) {
      output.add(new ShipAdapter(shipadapter.getCoord(), shipadapter.getLength(),
          shipadapter.getDirection()));
    }
    return output;
  }

  /**
   * Parses the given message arguments as a GuessJSON type, asks the player to take a guess given
   * the hint from the server, and then serializes the player's new guess to Json and sends the
   * response to the server.
   */
  private void handleTakeShots() {
    String methodName = "take-shots";
    List<Coord> shots = aiplayer.takeShots();
    TakeShotsJson shotsjson = new TakeShotsJson(shots);
    JsonNode shotsnode = JsonUtils.serializeRecord(shotsjson);

    MessageJson response = new MessageJson(methodName, shotsnode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given message arguments as a GuessJSON type, asks the player to take a guess given
   * the hint from the server, and then serializes the player's new guess to Json and sends the
   * response to the server.
   *
   * @param arguments the Json representation of a GuessJSON
   */
  private void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamage = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<Coord> opponentsShots = reportDamage.theirshots();

    List<Coord> theirhitshots = aiplayer.reportDamage(opponentsShots);
    ReportDamageJson theirhitshotsjson = new ReportDamageJson(theirhitshots);
    JsonNode theirhitshotsnode = JsonUtils.serializeRecord(theirhitshotsjson);

    String methodName = "report-damage";
    MessageJson response = new MessageJson(methodName, theirhitshotsnode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given message arguments as a GuessJSON type, asks the player to take a guess given
   * the hint from the server, and then serializes the player's new guess to Json and sends the
   * response to the server.
   *
   * @param arguments the Json representation of a GuessJSON
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson success = this.mapper.convertValue(arguments, SuccessfulHitsJson.class);
    List<Coord> oursuccess = success.oursuccess();
    aiplayer.successfulHits(oursuccess);


    String methodName = "successful-hits";
    JsonNode empty = mapper.createObjectNode();
    MessageJson response = new MessageJson(methodName, empty);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a WinJSON, notifies the player whether they won, and provides a
   * void response to the server.
   *
   * @param arguments the Json representation of a WinJSON
   */
  private void handleWin(JsonNode arguments) {
    EndGameJson end = this.mapper.convertValue(arguments, EndGameJson.class);

    this.aiplayer.endGame(end.result(), end.reason());

    String methodName = "end-game";
    JsonNode empty = mapper.createObjectNode();
    MessageJson response = new MessageJson(methodName, empty);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }


}


package cs3500.pa03.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.MockRandom;
import cs3500.pa03.Mocket;
import cs3500.pa03.Randomable;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.InterfaceView;
import cs3500.pa03.view.View;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController dealer;
  private Appendable appendable;
  private Readable readable;
  private InterfaceView view;
  private Player aiplayer;
  private ObjectMapper mapper;
  private Randomable rand;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    appendable = System.out;
    readable = new InputStreamReader(System.in);
    view = new View(appendable, readable);
    rand = new MockRandom();
    aiplayer = new AiPlayer(rand, view);
    mapper = new ObjectMapper();
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testJoinJson() {
    // Prepare sample message
    JoinJson joinJson = new JoinJson("", GameType.SINGLE);
    JsonNode sampleMessage = createSampleMessage("join", joinJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"join\",\"arguments\":{\"name\":\"pa04-jack-jp\","
            +
            "\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testFail() {
    // Prepare sample message
    JoinJson joinJson = new JoinJson("", GameType.SINGLE);
    JsonNode sampleMessage = createSampleMessage("thishouldntwork", joinJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response

    assertThrows(IllegalStateException.class, () -> this.dealer.run());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testSetupJson() {
    HashMap<ShipType, Integer> specs = new HashMap<>() {
      {
        put(ShipType.DESTROYER, 1);
        put(ShipType.CARRIER, 1);
        put(ShipType.BATTLESHIP, 1);
        put(ShipType.SUBMARINE, 1);
      }
    };
    SetupJson setupJson = new SetupJson(6, 6, specs);
    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();


    String expected =
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":"
            + "{\"x\":0,\"y\":1}\"direction\":\"HORIZONTAL\"},"
            + "{\"coord\":{\"x\":0,\"y\":4},\"direction\":\"HORIZONTAL\"},"
            + "{\"coord\":{\"x\":1,\"y\":2},\"direction\":\"HORIZONTAL\"},{\"coord\":"
            + "{\"x\":0,\"y\":0},\"length\":3:\"HORIZONTAL\"}]}}\n";
    assertTrue(logToString().contains(expected.substring(0, 20)));
  }

  @Test
  public void testTakeShotsJson() {
    HashMap<ShipType, Integer> specs = new HashMap<>() {
      {
        put(ShipType.DESTROYER, 1);
        put(ShipType.CARRIER, 1);
        put(ShipType.BATTLESHIP, 1);
        put(ShipType.SUBMARINE, 1);
      }
    };
    SetupJson setupJson = new SetupJson(6, 6, specs);
    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(new ByteArrayOutputStream(2048), List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    List<Coord> coords = new ArrayList<>();
    TakeShotsJson takeShotsJson = new TakeShotsJson(coords);
    JsonNode jsonNode1 = createSampleMessage("take-shots", takeShotsJson);

    // Create socket with sample input
    Mocket socket1 = new Mocket(this.testLog, List.of(jsonNode1.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket1, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    String expected =
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":0},"
            +
            "{\"x\":2,\"y\":0},{\"x\":4,\"y\":0},{\"x\":1,\"y\":1}]}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testReportDamage() {
    HashMap<ShipType, Integer> specs = new HashMap<>() {
      {
        put(ShipType.DESTROYER, 1);
        put(ShipType.CARRIER, 1);
        put(ShipType.BATTLESHIP, 1);
        put(ShipType.SUBMARINE, 1);
      }
    };
    SetupJson setupJson = new SetupJson(6, 6, specs);
    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(new ByteArrayOutputStream(2048), List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    List<Coord> coords = new ArrayList<>(Arrays.asList(new Coord(1, 1),
        new Coord(0, 0)));
    ReportDamageJson reportDamageJson = new ReportDamageJson(coords);
    JsonNode jsonNode1 = createSampleMessage("report-damage", reportDamageJson);

    // Create socket with sample input
    Mocket socket1 = new Mocket(this.testLog, List.of(jsonNode1.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket1, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[{\"x\":1,\"y\":1},"
            +
            "{\"x\":0,\"y\":0}]}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testSuccessfulHits() {
    HashMap<ShipType, Integer> specs = new HashMap<>() {
      {
        put(ShipType.DESTROYER, 1);
        put(ShipType.CARRIER, 1);
        put(ShipType.BATTLESHIP, 1);
        put(ShipType.SUBMARINE, 1);
      }
    };
    SetupJson setupJson = new SetupJson(6, 6, specs);
    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(new ByteArrayOutputStream(2048), List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    List<Coord> coords = new ArrayList<>(Arrays.asList(new Coord(1, 1),
        new Coord(0, 0)));
    ReportDamageJson reportDamageJson = new ReportDamageJson(coords);
    JsonNode jsonNode1 = createSampleMessage("successful-hits", reportDamageJson);

    // Create socket with sample input
    Mocket socket1 = new Mocket(this.testLog, List.of(jsonNode1.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket1, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    String expected =
        "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testEndGame() {
    HashMap<ShipType, Integer> specs = new HashMap<>() {
      {
        put(ShipType.DESTROYER, 1);
        put(ShipType.CARRIER, 1);
        put(ShipType.BATTLESHIP, 1);
        put(ShipType.SUBMARINE, 1);
      }
    };
    SetupJson setupJson = new SetupJson(6, 6, specs);
    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(new ByteArrayOutputStream(2048), List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();


    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "because");
    JsonNode jsonNode1 = createSampleMessage("end-game", endGameJson);

    // Create socket with sample input
    Mocket socket1 = new Mocket(this.testLog, List.of(jsonNode1.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket1, this.aiplayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();

    String expected =
        "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */

  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }


  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

}

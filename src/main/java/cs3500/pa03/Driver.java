package cs3500.pa03;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.InterfaceController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.server.ProxyController;
import cs3500.pa03.view.InterfaceView;
import cs3500.pa03.view.View;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the main Driver of this project.
 */
public class Driver {

  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket socket = new Socket(host, port);
    Appendable appendable = System.out;
    Readable readable = new InputStreamReader(System.in);
    InterfaceView view = new View(appendable, readable);
    Player player = new AiPlayer(view);
    ProxyController proxy = new ProxyController(socket, player);
    //sendToServer.println("HELLO");
    proxy.run();

  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    try {
      ArrayList<String> processedArgs = processCliArgs(args);
      if (!processedArgs.isEmpty()) {
        Driver.runClient(processedArgs.get(0), Integer.parseInt(processedArgs.get(1)));
      } else {
        Appendable appendable = System.out;
        Readable readable = new InputStreamReader(System.in);
        InterfaceController controller = new Controller(appendable, readable);
        controller.start();
      }
    } catch (Exception e) {
      System.out.println("We have encountered an error");
    }

  }

  private static ArrayList<String> processCliArgs(String[] args) {
    ArrayList<String> cliArgs = new ArrayList<>(Arrays.asList(args));
    return  cliArgs;
  }

}

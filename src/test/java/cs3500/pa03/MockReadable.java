package cs3500.pa03;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Mock Readable class for testing the controller
 */
public class MockReadable implements Readable {
  /**
   * @param input is a string that you can input
   */
  private String input;
  private final Queue<String> inputTokens;

  /**
   * Constructor for mock readable
   *
   * @param input - input
   */
  public MockReadable(String input) {
    this.input = input;
    this.inputTokens = new LinkedList<>(Arrays.asList(input.split("\\s+")));
  }

  /**
   * @param cb the buffer to read characters into
   * @return an int based on whether there's a next line or not
   * @throws IOException exception
   */
  @Override
  public int read(CharBuffer cb) throws IOException {
    if (inputTokens.isEmpty()) {
      return -1;
    }
    String token = inputTokens.remove();
    cb.append(token);
    cb.append(' ');
    return 1;
  }

}


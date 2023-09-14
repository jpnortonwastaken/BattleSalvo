package cs3500.pa03;

/**
 *
 */
public class MockRandom implements Randomable {
  private int index = 0;
  private final int[] numbers = new int[] {0, 0, 1, 2, 3, 4, 5, 6};

  public MockRandom() {
  }

  @Override
  public int nextInt() {
    int next = numbers[index];
    index = (index + 1) % numbers.length;
    return next;
  }

  @Override
  public int nextInt(int value) {
    int next = numbers[index];
    index = (index + 1) % numbers.length;
    return next % value;
  }

  @Override
  public boolean nextBoolean() {
    int next = numbers[index];
    index = (index + 1) % numbers.length;
    return next % 2 == 0;
  }
}

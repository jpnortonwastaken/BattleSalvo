package cs3500.pa03;

import java.util.Random;

/**
 * Random Decorator
 */
public class RandomDecorator implements Randomable {
  private final Random rand;

  // Create the Decorator exactly the same way you would create Random normally
  public RandomDecorator() {
    this.rand = new Random();
  }

  public RandomDecorator(int seed) {
    this.rand = new Random(seed);
  }

  // Implement the method from the interface; use the Random object
  @Override
  public int nextInt() {
    return this.rand.nextInt();
  }

  // Implement the method from the interface; use the Random object
  @Override
  public int nextInt(int value) {
    return this.rand.nextInt(value);
  }


  // Implement the method from the interface; use the Random object
  @Override
  public boolean nextBoolean() {
    return this.rand.nextBoolean();
  }


}


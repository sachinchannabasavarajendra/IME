public class Pixel {

  private final int redComponent;
  private final int greenComponent;
  private final int blueComponent;

  public Pixel(int red, int green, int blue) {
    redComponent = red;
    greenComponent = green;
    blueComponent = blue;
  }

  public int getValue() {
    return Math.max(redComponent, Math.max(blueComponent, greenComponent));
  }

  public double getIntensity() {
    return ((double) redComponent + greenComponent + blueComponent) / 3;
  }

  public double getLuma() {
    return (0.2126 * redComponent) + (0.7152 * greenComponent) + (0.0722 * blueComponent);
  }

  public int getRedComponent() {
    return redComponent;
  }

  public int getGreenComponent() {
    return greenComponent;
  }

  public int getBlueComponent() {
    return blueComponent;
  }
}

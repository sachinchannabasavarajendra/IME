package model;

/**
 * This class represents a single pixel of an image which contains the red, green and blue
 * components respectively.
 */
public class Pixel implements IPixel {

  private final int redComponent;
  private final int greenComponent;
  private final int blueComponent;

  /**
   * This is a constructor which is used to instantiate the above class.
   *
   * @param red   the red component of the image
   * @param green the green component of the image
   * @param blue  the blue component of the image
   */
  public Pixel(int red, int green, int blue) {
    redComponent = red;
    greenComponent = green;
    blueComponent = blue;
  }

  /**
   * This method is used to get the maximum value of the three components for each pixel.
   *
   * @return the maximum value of the three components for each pixel
   */
  public int getValue() {
    return Math.max(redComponent, Math.max(blueComponent, greenComponent));
  }

  /**
   * This method is used to get the average of the three components for each pixel.
   *
   * @return the average of the three components for each pixel
   */
  public int getIntensity() {
    return (int) Math.round(((double) redComponent + greenComponent + blueComponent) / 3);
  }

  /**
   * This method is used to get the weighted sum of the pixel components.
   *
   * @return the weighted sum
   */
  public int getLuma() {
    return (int) Math.round(
        (0.2126 * redComponent) + (0.7152 * greenComponent) + (0.0722 * blueComponent));
  }

  /**
   * This method is used to get the value of the red component of the pixel.
   *
   * @return the value of the red component
   */
  public int getRedComponent() {
    return redComponent;
  }

  /**
   * This method is used to get the value of the green component of the pixel.
   *
   * @return the value of the green component
   */
  public int getGreenComponent() {
    return greenComponent;
  }

  /**
   * This method is used to get the value of the blue component of the pixel.
   *
   * @return the value of the blue component
   */
  public int getBlueComponent() {
    return blueComponent;
  }
}

package model;

/**
 * This interface represents a pixel of an image.
 */
public interface IPixel {

  /**
   * This method is used to get the maximum value of the three components for each pixel.
   *
   * @return the maximum value of the three components for each pixel
   */
  int getValue();

  /**
   * This method is used to get the average of the three components for each pixel.
   *
   * @return the average of the three components for each pixel
   */
  int getIntensity();

  /**
   * This method is used to get the weighted sum of the pixel components.
   *
   * @return the weighted sum
   */
  int getLuma();

  /**
   * This method is used to get the value of the red component of the pixel.
   *
   * @return the value of the red component
   */
  int getRedComponent();

  /**
   * This method is used to get the value of the green component of the pixel.
   *
   * @return the value of the green component
   */
  int getGreenComponent();

  /**
   * This method is used to get the value of the blue component of the pixel.
   *
   * @return the value of the blue component
   */
  int getBlueComponent();
}

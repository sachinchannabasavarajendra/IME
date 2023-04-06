package model.helpers;

import model.IPixel;

/**
 * Helper class to manipulate a pixel/set of pixels in an image.
 */
public class PixelHelper {

  /**
   * This is a helper method used to clamp a value between 0 and 255.
   *
   * @param value the value to be clamped between 0 and 255
   * @return the clamped value
   */
  public static double clamp(double value) {
    return Math.min(Math.max(value, 0), 255);
  }

  /**
   * This is a helper method used to reverse the contents of an array.
   *
   * @param a the input array
   * @return the reversed array
   */
  public static IPixel[] reverse(IPixel[] a) {
    int n = a.length;
    IPixel[] newRow = a.clone();
    IPixel t;
    for (int i = 0; i < n / 2; i++) {
      t = newRow[i];
      newRow[i] = newRow[n - i - 1];
      newRow[n - i - 1] = t;
    }
    return newRow;
  }
}

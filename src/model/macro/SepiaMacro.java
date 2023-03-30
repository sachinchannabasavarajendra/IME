package model.macro;

import model.IMEModel;
import model.helpers.ColorTransform;

/**
 * Macro function class to convert an image to a sepia tone.
 */
public class SepiaMacro implements IMacro{

  /**
   * This is a method used to transform the color of the image and give it a sepia tone based on the
   * kernel filter value.
   *
   * @param model the image that needs to be transformed
   * @return the resultant sepia color transformed image data
   */
  public IMEModel execute(IMEModel model) {
    double[][] kernel = new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
    return ColorTransform.transform(kernel, model);
  }
}
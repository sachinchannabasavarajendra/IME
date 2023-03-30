package model.macro;

import model.IMEModel;
import model.helpers.ColorTransform;

/**
 * Marco class to convert an image to greyscale image.
 */
public class GreyscaleMacro implements IMacro{

  /**
   * This is a method used to transform the color of the image and give it a greyscale tone based on the
   * kernel filter value.
   *
   * @param model the image that needs to be transformed.
   * @return the resultant sepia color transformed image data
   */
  @Override
  public IMEModel execute(IMEModel model) {
    double[][] kernel = new double[][]{{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
    return ColorTransform.transform(kernel, model);
  }
}

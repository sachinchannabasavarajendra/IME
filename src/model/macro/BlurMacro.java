package model.macro;

import model.IMEModel;
import model.helpers.FilterImage;

/**
 * Marco class to convert an image to blurred image.
 */
public class BlurMacro implements IMacro {

  /**
   * This is a method used to blur the image based on the kernel filter value.
   *
   * @param model the image that needs to be blurred
   * @return the resultant blurred image data
   */
  @Override
  public IMEModel execute(IMEModel model) {
    double[][] kernel = new double[][]{{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
    return FilterImage.filter(kernel, model);
  }
}

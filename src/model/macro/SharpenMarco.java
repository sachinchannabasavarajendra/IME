package model.macro;

import model.IMEModel;
import model.helpers.FilterImage;

/**
 * Marco class to sharpen an image.
 */

public class SharpenMarco implements IMacro{

  /**
   * This is a method used to sharpen the image based on the kernel filter value.
   *
   * @param model the image that needs to be sharpened
   *
   * @return the resultant sharpened image data
   */
  @Override
  public IMEModel execute(IMEModel model) {
    double[][] kernel = new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}};
    return FilterImage.filter(kernel, model);
  }
}

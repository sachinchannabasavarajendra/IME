package model.helpers;

import model.IMEModel;
import model.IMEModelImpl;
import model.IPixel;
import model.Pixel;

import static model.helpers.PixelHelper.clamp;

/**
 * Helper class to transform an image based on color.
 */
public class ColorTransform {

  /**
   * This is a helper method used to apply a color transformation to the image based on the input
   * kernel filter value.
   *
   * @param kernel the filter value which is used to transform the components of the image
   * @param image  the image that needs to be transformed
   * @return the resultant color transformed image data
   */
  public static IMEModel transform(double[][] kernel, IMEModel image) {
    int height = image.getImageHeight();
    int width = image.getImageWidth();
    IPixel[][] imageData = image.getImageData();
    IPixel[][] newImageData = new Pixel[height][width];
    double red;
    double green;
    double blue;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double[][] rgbMatrix = {
            {imageData[i][j].getRedComponent()},
            {imageData[i][j].getGreenComponent()},
            {imageData[i][j].getBlueComponent()}};

        int row1 = kernel.length;
        int col1 = kernel[0].length;
        int col2 = rgbMatrix[0].length;

        double[][] result = new double[row1][col2];

        for (int x = 0; x < row1; x++) {
          for (int y = 0; y < col2; y++) {
            for (int z = 0; z < col1; z++) {
              result[x][y] += kernel[x][z] * rgbMatrix[z][y];
            }
          }
        }

        red = clamp(result[0][0]);
        green = clamp(result[1][0]);
        blue = clamp(result[2][0]);

        newImageData[i][j] = new Pixel((int) red, (int) green, (int) blue);
      }
    }
    return new IMEModelImpl(newImageData, height, width, image.getMaxValue());
  }
}

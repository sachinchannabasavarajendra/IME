package model.helpers;

import model.IMEModel;
import model.IMEModelImpl;
import model.IPixel;
import model.Pixel;

import static model.helpers.PixelHelper.clamp;

/**
 * Helper class to convert an image based on a filter.
 */
public class FilterImage {

  /**
   * This is a helper method used to filter the image based on the input kernel filter value.
   *
   * @param kernel the filter value which is used to filter the components of the image
   * @param image the image that needs to be filtered
   * @return the resultant filtered image
   */
  public static IMEModel filter(double[][] kernel, IMEModel image) {
    int height = image.getImageHeight();
    int width = image.getImageWidth();
    IPixel[][] imageData = image.getImageData();
    int kernelSize = kernel.length;
    int offset = kernelSize / 2;

    IPixel[][] newImageData = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double red = 0;
        double green = 0;
        double blue = 0;
        for (int k = -offset; k <= offset; k++) {
          for (int l = -offset; l <= offset; l++) {
            int x = i + k;
            int y = j + l;
            if (x >= 0 && x < height && y >= 0 && y < width) {
              red += imageData[x][y].getRedComponent() * kernel[k + offset][l + offset];
              green += imageData[x][y].getGreenComponent() * kernel[k + offset][l + offset];
              blue += imageData[x][y].getBlueComponent() * kernel[k + offset][l + offset];
            }
          }
        }
        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);
        newImageData[i][j] = new Pixel((int) red, (int) green, (int) blue);
      }
    }
    return new IMEModelImpl(newImageData, height, width, image.getMaxValue());
  }
}

package model.helpers;

import model.IMEModel;
import model.IMEModelImpl;
import model.IPixel;
import model.Pixel;

import static model.helpers.PixelHelper.clamp;

public class ColorTransform {

  /**
   * This is a helper method used to apply a color transformation to the image based on the input
   * kernel filter value.
   *
   * @param kernel the filter value which is used to transform the components of the image
   * @param image the image that needs to be tranformed
   * @return the resultant color transformed image data
   */
  public static IMEModel transform(double[][] kernel, IMEModel image) {
    int height = image.getImageHeight();
    int width = image.getImageWidth();
    IPixel[][] imageData= image.getImageData();
    IPixel[][] newImageData = new Pixel[height][width];
    double red;
    double green;
    double blue;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red = kernel[0][0] * imageData[i][j].getRedComponent()
                + kernel[0][1] * imageData[i][j].getGreenComponent()
                + kernel[0][2] * imageData[i][j].getBlueComponent();
        green = kernel[1][0] * imageData[i][j].getRedComponent()
                + kernel[1][1] * imageData[i][j].getGreenComponent()
                + kernel[1][2] * imageData[i][j].getBlueComponent();
        blue = kernel[2][0] * imageData[i][j].getRedComponent()
                + kernel[2][1] * imageData[i][j].getGreenComponent()
                + kernel[2][2] * imageData[i][j].getBlueComponent();

        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);

        newImageData[i][j] = new Pixel((int) red, (int) green, (int) blue);
      }
    }
    return new IMEModelImpl(newImageData, height, width, image.getMaxValue());
  }
}

package model.macro;

import model.IMEModel;
import model.IMEModelImpl;
import model.IPixel;
import model.Pixel;

public class DitherMacro implements IMacro{

  /**
   * This is a method used to dither an image.
   *
   * @param model the image that needs to be dithered
   *
   * @return the resultant dithered image data.
   */
  @Override
  public IMEModel execute(IMEModel model) {
    int height = model.getImageHeight();
    int width = model.getImageWidth();
    int maxValue = model.getMaxValue();
    IMEModel greyScaleImage = model.greyScaleImage(IPixel::getLuma);
    IPixel[][] newImageData = new Pixel[height][width];
    IPixel[][] oldImageData = greyScaleImage.getImageData();
    for (int i = 0; i < greyScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < greyScaleImage.getImageWidth(); j++) {
        int oldColor = oldImageData[i][j].getRedComponent();
        int newColor = maxValue - oldColor <= 127 ? maxValue : 0;
        int error = oldColor - newColor;
        newImageData[i][j] = new Pixel(newColor, newColor, newColor);

        if (j + 1 < greyScaleImage.getImageWidth()) {
          int rightError = (int) Math.round(
                  oldImageData[i][j + 1].getRedComponent() + ((7.0 * error) / 16.0));
          oldImageData[i][j + 1] = new Pixel(rightError, rightError, rightError);
        }

        if (j > 1 && i + 1 < greyScaleImage.getImageHeight()) {
          int leftBottomError = (int) Math.round(
                  oldImageData[i + 1][j - 1].getRedComponent() + ((3.0 * error) / 16.0));
          oldImageData[i + 1][j - 1] = new Pixel(leftBottomError, leftBottomError, leftBottomError);
        }

        if (i + 1 < greyScaleImage.getImageHeight()) {
          int bottom = (int) Math.round(
                  oldImageData[i + 1][j].getRedComponent() + ((5.0 * error) / 16.0));
          oldImageData[i + 1][j] = new Pixel(bottom, bottom, bottom);
        }

        if (i + 1 < greyScaleImage.getImageHeight() && j + 1 < greyScaleImage.getImageWidth()) {
          int bottomRight = (int) Math.round(
                  oldImageData[i + 1][j + 1].getRedComponent() + (error / 16.0));
          oldImageData[i + 1][j + 1] = new Pixel(bottomRight, bottomRight, bottomRight);
        }
      }
    }

    return new IMEModelImpl(newImageData, height, width, maxValue);
  }
}

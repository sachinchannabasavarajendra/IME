package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class IMEModelImpl implements IMEModel {

  private final Pixel[][] imageData;
  private final int height;
  private final int width;

  public IMEModelImpl(Pixel[][] imageData, int height, int width) {
    this.imageData = imageData;
    this.height = height;
    this.width = width;
  }

  @Override
  public Pixel[][] getImageData() {
    return imageData;
  }

  @Override
  public int getImageHeight() {
    return this.height;
  }

  @Override
  public int getImageWidth() {
    return this.width;
  }

  private IMEModel greyScaleImage(Function<Pixel, Integer> func) {
    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = func.apply(imageData[i][j]);
        newImageData[i][j] = new Pixel(value, value, value);
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel redGreyScaleImage() {
    return greyScaleImage(Pixel::getRedComponent);
  }

  @Override
  public IMEModel greenGreyScaleImage() {
    return greyScaleImage(Pixel::getGreenComponent);
  }

  @Override
  public IMEModel blueGreyScaleImage() {
    return greyScaleImage(Pixel::getBlueComponent);
  }

  @Override
  public IMEModel valueGreyScaleImage() {
    return greyScaleImage(Pixel::getValue);
  }

  @Override
  public IMEModel lumaGreyScaleImage() {
    return greyScaleImage(Pixel::getLuma);
  }

  @Override
  public IMEModel intensityGreyScaleImage() {
    return greyScaleImage(Pixel::getIntensity);
  }

  @Override
  public IMEModel horizontalFlipImage() {
    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < imageData.length; i++) {
      newImageData[i] = reverse(imageData[i]);
    }

    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel verticalFlipImage() {
    Pixel[][] newImageData = new Pixel[height][width];
    int n = imageData.length - 1;
    for (int i = n; i >= 0; i--) {
      newImageData[n - i] = imageData[i];
    }

    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel alterBrightness(int delta) {

    Pixel[][] newImageData = new Pixel[height][width];

    if (delta < 0) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int redComponent = Math.max(this.imageData[i][j].getRedComponent() + delta, 0);
          int greenComponent = Math.max(this.imageData[i][j].getGreenComponent() + delta, 0);
          int blueComponent = Math.max(this.imageData[i][j].getBlueComponent() + delta, 0);

          newImageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
        }
      }
    } else {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int redComponent = Math.min(this.imageData[i][j].getRedComponent() + delta, 255);
          int greenComponent = Math.min(this.imageData[i][j].getGreenComponent() + delta, 255);
          int blueComponent = Math.min(this.imageData[i][j].getBlueComponent() + delta, 255);

          newImageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
        }
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public List<IMEModel> rgbSplit() {
    IMEModel redImage = this.redGreyScaleImage();
    IMEModel greenImage = this.greenGreyScaleImage();
    IMEModel blueImage = this.blueGreyScaleImage();
    return new ArrayList<>(Arrays.asList(redImage, greenImage, blueImage));
  }

  @Override
  public IMEModel combineRGBImage(IMEModel redScaleImage, IMEModel blueScaleImage,
      IMEModel greenScaleImage) {

    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newImageData[i][j] = new Pixel(
            redScaleImage.getImageData()[i][j].getRedComponent(),
            greenScaleImage.getImageData()[i][j].getGreenComponent(),
            blueScaleImage.getImageData()[i][j].getBlueComponent());
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  static Pixel[] reverse(Pixel[] a) {
    int n = a.length;
    Pixel[] newRow = a.clone();
    Pixel t;
    for (int i = 0; i < n / 2; i++) {
      t = newRow[i];
      newRow[i] = newRow[n - i - 1];
      newRow[n - i - 1] = t;
    }
    return newRow;
  }
}
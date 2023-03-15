package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * This class represents an implementation of the IMEModel interface which provides the
 * operations/manipulations that can be performed on a given image.
 */
public class IMEModelImpl implements IMEModel {

  private final Pixel[][] imageData;
  private final int height;
  private final int width;

  /**
   * This is a constructor used to instantiate the above class by taking in the image data, height
   * and width of the image.
   *
   * @param imageData the image data in the form of an array
   * @param height    the height of the image
   * @param width     the width of the image
   */
  public IMEModelImpl(Pixel[][] imageData, int height, int width) {
    this.imageData = imageData;
    this.height = height;
    this.width = width;
  }

  /**
   * This is a method used to get the processed image data.
   *
   * @return the processed image data
   */
  @Override
  public Pixel[][] getImageData() {
    return imageData;
  }

  /**
   * This is a method used to get the height of the image.
   *
   * @return the height of the image
   */
  @Override
  public int getImageHeight() {
    return this.height;
  }

  /**
   * This is a method used to get the width of the image.
   *
   * @return the width of the image
   */
  @Override
  public int getImageWidth() {
    return this.width;
  }

  /**
   * This is a method used to convert the given image into a greyscale image of the given
   * component.
   *
   * @param func function which takes in the color component
   * @return the greyscale image data
   */
  @Override
  public IMEModel greyScaleImage(Function<Pixel, Integer> func) {
    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = func.apply(imageData[i][j]);
        newImageData[i][j] = new Pixel(value, value, value);
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  /**
   * This is a method used to flip the given image horizontally.
   *
   * @return the image data of the flipped image
   */
  @Override
  public IMEModel horizontalFlipImage() {
    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < imageData.length; i++) {
      newImageData[i] = reverse(imageData[i]);
    }

    return new IMEModelImpl(newImageData, height, width);
  }

  /**
   * This is a method used to flip the given image vertically.
   *
   * @return the image data of the flipped image
   */
  @Override
  public IMEModel verticalFlipImage() {
    Pixel[][] newImageData = new Pixel[height][width];
    int n = imageData.length - 1;
    for (int i = n; i >= 0; i--) {
      newImageData[n - i] = imageData[i];
    }

    return new IMEModelImpl(newImageData, height, width);
  }

  /**
   * This is a method used to brighten or darken the given image based on the increment or decrement
   * input value.
   *
   * @param delta the increment or decrement input value
   * @return the image data of the manipulated image
   */
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

  /**
   * This is a method used to split the given image into three greyscale images containing its red,
   * green and blue components respectively.
   *
   * @return three greyscale images containing the red, green and blue components
   */
  @Override
  public List<IMEModel> rgbSplit() {
    IMEModel redImage = this.greyScaleImage(Pixel::getRedComponent);
    IMEModel greenImage = this.greyScaleImage(Pixel::getGreenComponent);
    IMEModel blueImage = this.greyScaleImage(Pixel::getBlueComponent);
    return new ArrayList<>(Arrays.asList(redImage, greenImage, blueImage));
  }

  /**
   * This is a method used to combine the three greyscale images into a single image that gets its
   * red, green and blue components from the three images respectively.
   *
   * @param redScaleImage   the image from which the red component needs to be taken
   * @param greenScaleImage the image from which the green component needs to be taken
   * @param blueScaleImage  the image from which the blue component needs to be taken
   * @return combined greyscale image with all the three components
   */
  @Override
  public IMEModel combineRGBImage(IMEModel redScaleImage, IMEModel greenScaleImage,
      IMEModel blueScaleImage) {

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

  /**
   * This is a helper method used to reverse the contents of an array.
   *
   * @param a the input array
   * @return the reversed array
   */
  private Pixel[] reverse(Pixel[] a) {
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
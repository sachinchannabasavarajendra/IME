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

  /*
  Each Model object represents an Image loaded.
  An image is represented as a 2D array of IPixels where in each IPixel represent a particular
  type of Pixel implementation (RGB, PNG, etc..).
  Irrespective of the type of file imported the image will always be represented as an 2D array of
  IPixel objects.
   */
  private final IPixel[][] imageData;
  private final int height;
  private final int width;
  private final int maxValue;

  /**
   * This is a constructor used to instantiate the above class by taking in the image data, height
   * and width of the image.
   *
   * @param imageData the image data in the form of an array
   * @param height    the height of the image
   * @param width     the width of the image
   * @param maxValue  the max value of each component of a pixel
   */
  public IMEModelImpl(IPixel[][] imageData, int height, int width, int maxValue) {
    this.imageData = imageData;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
  }

  /**
   * This is a method used to get the processed image data.
   *
   * @return the processed image data
   */
  @Override
  public IPixel[][] getImageData() {
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

  @Override
  public int getMaxValue() {
    return this.maxValue;
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
  public IMEModel greyScaleImage(Function<IPixel, Integer> func) {
    IPixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = func.apply(imageData[i][j]);
        newImageData[i][j] = new Pixel(value, value, value);
      }
    }
    return new IMEModelImpl(newImageData, height, width, this.maxValue);
  }

  /**
   * This is a method used to flip the given image horizontally.
   *
   * @return the image data of the flipped image
   */
  @Override
  public IMEModel horizontalFlipImage() {
    IPixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < imageData.length; i++) {
      newImageData[i] = reverse(imageData[i]);
    }

    return new IMEModelImpl(newImageData, height, width, maxValue);
  }

  /**
   * This is a method used to flip the given image vertically.
   *
   * @return the image data of the flipped image
   */
  @Override
  public IMEModel verticalFlipImage() {
    IPixel[][] newImageData = new Pixel[height][width];
    int n = imageData.length - 1;
    for (int i = n; i >= 0; i--) {
      newImageData[n - i] = imageData[i];
    }

    return new IMEModelImpl(newImageData, height, width, maxValue);
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

    IPixel[][] newImageData = new Pixel[height][width];

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
          int redComponent =
              Math.min(this.imageData[i][j].getRedComponent() + delta, this.maxValue);
          int greenComponent =
              Math.min(this.imageData[i][j].getGreenComponent() + delta, this.maxValue);
          int blueComponent =
              Math.min(this.imageData[i][j].getBlueComponent() + delta, this.maxValue);

          newImageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
        }
      }
    }
    return new IMEModelImpl(newImageData, height, width, maxValue);
  }

  /**
   * This is a method used to split the given image into three greyscale images containing its red,
   * green and blue components respectively.
   *
   * @return three greyscale images containing the red, green and blue components
   */
  @Override
  public List<IMEModel> rgbSplit() {
    IMEModel redImage = this.greyScaleImage(IPixel::getRedComponent);
    IMEModel greenImage = this.greyScaleImage(IPixel::getGreenComponent);
    IMEModel blueImage = this.greyScaleImage(IPixel::getBlueComponent);
    return new ArrayList<>(Arrays.asList(redImage, greenImage, blueImage));
  }

  /**
   * This is a method used to combine the three greyscale images into a single image that gets its
   * red, green and blue components from the three images respectively.
   *
   * @param greenScaleImage the image from which the green component needs to be taken
   * @param blueScaleImage  the image from which the blue component needs to be taken
   * @return combined greyscale image with all the three components
   */
  @Override
  public IMEModel combineRGBImage(IMEModel greenScaleImage,
      IMEModel blueScaleImage) {

    if (!(this.height == greenScaleImage.getImageHeight()
        && this.height == blueScaleImage.getImageHeight())
        || !(this.width == greenScaleImage.getImageWidth()
        && this.width == blueScaleImage.getImageWidth())) {
      throw new IllegalStateException("The greyscale images are of different sizes!");
    }
    IPixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newImageData[i][j] = new Pixel(
            this.getImageData()[i][j].getRedComponent(),
            greenScaleImage.getImageData()[i][j].getGreenComponent(),
            blueScaleImage.getImageData()[i][j].getBlueComponent());
      }
    }
    return new IMEModelImpl(newImageData, height, width, maxValue);
  }

  /**
   * This is a method used to blur or sharpen the image based on the kernel filter value passed to
   * this method.
   *
   * @param kernel the filter value to blur or sharpen the image
   * @return the resultant blurred or sharpened image data
   */
  @Override
  public IMEModel filterImage(double[][] kernel) {
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
              red += this.imageData[x][y].getRedComponent() * kernel[k + offset][l + offset];
              green += this.imageData[x][y].getGreenComponent() * kernel[k + offset][l + offset];
              blue += this.imageData[x][y].getBlueComponent() * kernel[k + offset][l + offset];
            }
          }
        }
        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);
        newImageData[i][j] = new Pixel((int) red, (int) green, (int) blue);
      }
    }
    return new IMEModelImpl(newImageData, height, width, maxValue);
  }

  /**
   * This is a method used to transform the color of the image based on the kernel filter value
   * passed to this method.
   *
   * @param kernel the filter value to transform the color of the image
   * @return the resultant color transformed image data
   */
  @Override
  public IMEModel colorTransform(double[][] kernel) {
    IPixel[][] newImageData = new Pixel[height][width];
    double red;
    double green;
    double blue;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red = kernel[0][0] * this.imageData[i][j].getRedComponent()
            + kernel[0][1] * this.imageData[i][j].getGreenComponent()
            + kernel[0][2] * this.imageData[i][j].getBlueComponent();
        green = kernel[1][0] * this.imageData[i][j].getRedComponent()
            + kernel[1][1] * this.imageData[i][j].getGreenComponent()
            + kernel[1][2] * this.imageData[i][j].getBlueComponent();
        blue = kernel[2][0] * this.imageData[i][j].getRedComponent()
            + kernel[2][1] * this.imageData[i][j].getGreenComponent()
            + kernel[2][2] * this.imageData[i][j].getBlueComponent();

        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);

        newImageData[i][j] = new Pixel((int) red, (int) green, (int) blue);
      }
    }
    return new IMEModelImpl(newImageData, height, width, maxValue);
  }

  public IMEModel dither() {
    IMEModel greyScaleImage = this.greyScaleImage(IPixel::getLuma);
    IPixel[][] newImageData = new Pixel[height][width];
    IPixel[][] oldImageData = greyScaleImage.getImageData();
    for(int i=0; i < greyScaleImage.getImageHeight(); i++) {
      for(int j=0; j < greyScaleImage.getImageWidth(); j++) {
        int oldColor = oldImageData[i][j].getRedComponent();
        int newColor = this.maxValue - oldColor <= 127 ? this.maxValue : 0;
        int error = oldColor - newColor;
        newImageData[i][j] = new Pixel(newColor, newColor, newColor);

        if(j+1 < greyScaleImage.getImageWidth()) {
          int rightError = (int) Math.round(oldImageData[i][j+1].getRedComponent() + ((7 * (double)error) / 16));
          oldImageData[i][j+1] = new Pixel(rightError, rightError, rightError);
        }

        if(j>1 && i+1 < greyScaleImage.getImageHeight()) {
          int leftBottomError = (int) Math.round(oldImageData[i+1][j-1].getRedComponent() + ((3 * (double)error) / 16));
          oldImageData[i+1][j-1] = new Pixel(leftBottomError, leftBottomError, leftBottomError);
        }

        if(i+1 < greyScaleImage.getImageHeight()) {
          int bottom = (int) Math.round(oldImageData[i+1][j].getRedComponent() + ((5 * (double)error) / 16));
          oldImageData[i+1][j] = new Pixel(bottom, bottom, bottom);
        }

        if(i+1 < greyScaleImage.getImageHeight() && j+1 < greyScaleImage.getImageWidth()) {
          int bottomright = (int) Math.round(oldImageData[i+1][j+1].getRedComponent() + ((1 * (double)error) / 16));
          oldImageData[i+1][j+1] = new Pixel(bottomright, bottomright, bottomright);
        }
      }
    }

    return new IMEModelImpl(newImageData, height, width, maxValue);
  }

  /**
   * This is a helper method used to reverse the contents of an array.
   *
   * @param a the input array
   * @return the reversed array
   */
  private IPixel[] reverse(IPixel[] a) {
    int n = a.length;
    IPixel[] newRow = a.clone();
    IPixel t;
    for (int i = 0; i < n / 2; i++) {
      t = newRow[i];
      newRow[i] = newRow[n - i - 1];
      newRow[n - i - 1] = t;
    }
    return newRow;
  }

  /**
   * This is a helper method used to clamp a value between 0 and 255
   *
   * @param value the value to be clamped between 0 and 255
   * @return the clamped value
   */
  private double clamp(double value) {
    return Math.min(Math.max(value, 0), 255);
  }
}
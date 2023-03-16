package model;

import java.util.List;
import java.util.function.Function;

/**
 * This is an interface which specifies the operations/manipulations to be performed on an image.
 */
public interface IMEModel {

  /**
   * This is a method used to get the processed image data.
   *
   * @return the processed image data
   */
  Pixel[][] getImageData();

  /**
   * This is a method used to get the height of the image.
   *
   * @return the height of the image
   */
  int getImageHeight();

  /**
   * This is a method used to get the width of the image.
   *
   * @return the width of the image
   */
  int getImageWidth();

  /**
   * This is a method used to get the max value of each component of the image.
   *
   * @return the max value of the each component.
   */
  int getMaxValue();

  /**
   * This is a method used to convert the given image into a greyscale image of the given
   * component.
   *
   * @param func function which takes in the color component
   * @return the greyscale image data
   */
  IMEModel greyScaleImage(Function<Pixel, Integer> func);

  /**
   * This is a method used to flip the given image horizontally.
   *
   * @return the image data of the flipped image
   */
  IMEModel horizontalFlipImage();

  /**
   * This is a method used to flip the given image vertically.
   *
   * @return the image data of the flipped image
   */
  IMEModel verticalFlipImage();

  /**
   * This is a method used to brighten or darken the given image based on the increment or decrement
   * input value.
   *
   * @param delta the increment or decrement input value
   * @return the image data of the manipulated image
   */
  IMEModel alterBrightness(int delta);

  /**
   * This is a method used to split the given image into three greyscale images containing its red,
   * green and blue components respectively.
   *
   * @return three greyscale images containing the red, green and blue components
   */
  List<IMEModel> rgbSplit();

  /**
   * This is a method used to combine the three greyscale images into a single image that gets its
   * red, green and blue components from the three images respectively.
   *
   * @param greenScaleImage the image from which the green component needs to be taken
   * @param blueScaleImage  the image from which the blue component needs to be taken
   * @return combined greyscale image with all the three components
   */
  IMEModel combineRGBImage(
          IMEModel greenScaleImage,
          IMEModel blueScaleImage
  );
}

package controller;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This is an interface which represents the features supported by the application.
 */
public interface Features {

  /**
   * Returns the buffered image given the name of the image file.
   *
   * @param name the name of the image
   * @return buffered image
   */
  BufferedImage GetLoadedImage(String name);

  /**
   * Load image with the given image name at the specified path.
   *
   * @param imagePath the path of the image
   * @param imageName the name of the image file
   */
  void LoadImage(String imagePath, String imageName);

  /**
   * This is a method used to blur the image.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void BlurImage(String src, String dest);

  /**
   * This is a method used to brighten or darken the image based on the given delta value.
   *
   * @param delta the delta which determines whether the image is to be brightened or darkened
   * @param src   the name of the source image on which the operation is to be performed
   * @param dest  the name of the resultant image obtained after manipulation
   */
  void Brighten(String delta, String src, String dest);

  /**
   * This is a method used to perform the operation of dithering the image.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void Dither(String src, String dest);

  /**
   * This is a method used to perform the operation of converting the given image into a greyscale
   * image of the given component.
   *
   * @param src       the name of the source image on which the operation is to be performed
   * @param dest      the name of the resultant image obtained after manipulation
   * @param component the color component based on which the image is to be greyscaled into
   */
  void Greyscale(String src, String dest, String component);

  /**
   * This is a method used to perform the operation of transforming the color of the image to give
   * it a greyscale tone based on the kernel filter value.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void GreyscaleColorTransform(String src, String dest);

  /**
   * This is a method used to perform the operation of flipping the given image horizontally.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void HorizontalFlip(String src, String dest);

  /**
   * This is a method used to perform the operation of flipping the given image vertically.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void VerticalFlip(String src, String dest);

  /**
   * This is a method used to perform the operation of transforming the color of the image to give
   * it a sepia tone based on the kernel filter value.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void Sepia(String src, String dest);

  /**
   * This is a method used to perform the operation of sharpening the given image.
   *
   * @param src  the name of the source image on which the operation is to be performed
   * @param dest the name of the resultant image obtained after manipulation
   */
  void Sharpen(String src, String dest);

  /**
   * This method is used to save the image with the given name at the specified path.
   *
   * @param imagePath the path where the image should be saved at
   * @param src       the image name
   */
  void SaveImage(String imagePath, String src);

  /**
   * This is a method used to perform the operation of combining the three images into a single
   * image that gets its red, green and blue components from the three images respectively.
   *
   * @param dest   the name of the resultant image obtained after manipulation
   * @param images three source images
   */
  void RGBCombine(String dest, List<String> images);

  /**
   * This is a method used to perform the operation of splitting the given image into three images
   * containing its red, green and blue components respectively.
   *
   * @param src   the name of the source image on which the operation is to performed
   * @param red   the destination image name of the red image component
   * @param green the destination image name of the green image component
   * @param blue  the destination image name of the blue image component
   */
  void RGBSplit(String src, String red, String green, String blue);
}

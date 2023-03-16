package service.imageFileLoader;

import java.io.FileNotFoundException;

import model.IMEModel;

/**
 * This is an interface for loading of an image file which provides flexibility to load an image
 * file of any format.
 */
public interface LoadImage {

  /**
   * This method is used to load an image file located at the given path.
   *
   * @param imagePath the path of the image file
   * @param imageName the name of the image file
   * @return processed image data
   * @throws FileNotFoundException if file is not located at the given file path
   */
  IMEModel load(String imagePath, String imageName) throws FileNotFoundException;
}

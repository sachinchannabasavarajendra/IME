package service.imageFileSaver;

import java.io.IOException;

import model.IMEModel;

/**
 * This is an interface for saving the manipulated image file which provides flexibility to save an
 * image file of any format.
 */
public interface SaveImage {

  /**
   * This method is used to save the manipulated image file at the given path.
   *
   * @param imagePath the path where the file should be saved at
   * @param model     image data of the image to be saved in the file
   * @throws IOException if the given path is incorrect
   */
  void save(String imagePath, IMEModel model) throws IOException;
}

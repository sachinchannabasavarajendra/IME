package service.imagefilesaver;

import java.io.IOException;
import model.IMEModel;

/**
 * This class is used to perform the operation of saving the manipulated image files in png format.
 */
public class SavePNG extends AbstractSaveImage {

  /**
   * This method is used to save the manipulated image file at the given path with png format.
   *
   * @param imagePath the path where the file should be saved at
   * @param model     image data of the image to be saved in the file
   * @throws IOException if the given path is incorrect
   */
  @Override
  public void save(String imagePath, IMEModel model) throws IOException {
    saveImage("png", imagePath, model);
  }
}
package controller.commands;

import service.imageFileSaver.SaveImage;
import service.imageFileSaver.SavePPM;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.util.Map;
import model.IMEModel;

/**
 * This class is used to perform the operation of saving the image with the given name to the
 * specified path which should include the name of the file.
 */
public class Save extends AbstractIMECommand {

  private final String imagePath;
  private final String imageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param imagePath the path where the image file needs to be stored at
   * @param imageName the name of the image
   */
  public Save(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  /**
   * This method saves the given image at the specified location.
   *
   * @param objectMap the map to store the image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    String fileType = imagePath.substring(imagePath.lastIndexOf(".") + 1);
    IMEModel imageObjectToBeSaved = null;
    switch (fileType) {
      case "ppm":
        SaveImage savePPM = new SavePPM();
        imageObjectToBeSaved = getModelObject(objectMap, imageName);
        try {
          savePPM.save(imagePath, imageObjectToBeSaved);
        } catch (IOException e) {
          throw new IllegalPathStateException(e.getMessage());
        }
        break;

      default:
        System.out.println("Given file type is not valid");
        break;
    }
  }
}

package controller.commands;

import service.imageFileLoader.LoadImage;
import service.imageFileLoader.LoadPPM;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import model.IMEModel;

/**
 * This class is used to perform the operation of loading an image from the specified path and refer
 * it to henceforth in the program by the given image name.
 */
public class Load extends AbstractIMECommand {

  private InputStream in;
  private final String imagePath;
  private final String imageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param in        the input stream to process the file
   * @param imagePath the path of the image file
   * @param imageName the name of the image file
   */
  public Load(InputStream in, String imagePath, String imageName) {
    this.in = in;
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  /**
   * This method is used to load and process the given image file and puts the loaded image data
   * object to the map.
   *
   * @param objectMap the map to store the image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel loadedImageObject = null;
    String fileType = imagePath.substring(imagePath.lastIndexOf(".") + 1);
    switch (fileType) {
      case "ppm":
        LoadImage loadPPM = new LoadPPM(this.in);
        try {
          loadedImageObject = loadPPM.load(imagePath, imageName);
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("PPM image file not found");
        }
        break;

      default:
        System.out.println("Given file type is not valid");
        break;
    }

    if (loadedImageObject != null) {
      objectMap.put(imageName, loadedImageObject);
    }
  }
}

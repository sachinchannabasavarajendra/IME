package controller.commands;

import controller.imageFileLoader.LoadImage;
import controller.imageFileLoader.LoadPPM;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import model.IMEModel;

public class Load extends AbstractIMECommand {

  private InputStream in;
  private final String imagePath;
  private final String imageName;

  public Load(InputStream in, String imagePath, String imageName) {
    this.in = in;
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

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

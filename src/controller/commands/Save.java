package controller.commands;

import controller.imageFileSaver.SaveImage;
import controller.imageFileSaver.SavePPM;
import java.io.IOException;
import java.util.Map;
import model.IMEModel;

public class Save extends AbstractIMECommand {

  private final String imagePath;
  private final String imageName;

  public Save(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel savedImageObject = null;
    String fileType = imagePath.substring(imagePath.lastIndexOf(".") + 1);
    IMEModel imageObjectToBeSaved = null;
    switch (fileType) {
      case "ppm":
        SaveImage savePPM = new SavePPM();
        imageObjectToBeSaved = getModelObject(objectMap, imageName);
        try {
          savePPM.save(imagePath, imageName, imageObjectToBeSaved);
        } catch (IOException e) {
          throw new IllegalArgumentException("Error saving ppm file");
        }
        break;

      default:
        System.out.println("Given file type is not valid");
        break;
    }

    if (imageObjectToBeSaved != null) {
      objectMap.put(imageName, imageObjectToBeSaved);
    }
  }
}

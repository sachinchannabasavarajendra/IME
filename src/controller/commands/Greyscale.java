package controller.commands;

import java.util.Map;
import model.IMEModel;

public class Greyscale extends AbstractIMECommand {

  private final String component;
  private final String sourceImageName;
  private final String destinationImageName;

  public Greyscale(String component, String sourceImageName, String destinationImageName) {
    this.component = component;
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }


  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel resultantImage;
    switch (component) {
      case "red-component" -> resultantImage = callingObject.redGreyScaleImage();
      case "green-component" -> resultantImage = callingObject.greenGreyScaleImage();
      case "blue-component" -> resultantImage = callingObject.blueGreyScaleImage();
      default ->
          throw new IllegalArgumentException(String.format("Unknown component %s", component));
    }
    objectMap.put(destinationImageName, resultantImage);
  }
}

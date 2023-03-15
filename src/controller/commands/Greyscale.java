package controller.commands;

import java.util.Map;
import model.IMEModel;
import model.Pixel;

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
      case "red-component":
        resultantImage = callingObject.greyScaleImage(Pixel::getRedComponent);
        break;
      case "green-component":
        resultantImage = callingObject.greyScaleImage(Pixel::getGreenComponent);
        break;
      case "blue-component":
        resultantImage = callingObject.greyScaleImage(Pixel::getBlueComponent);
        break;
      case "value-component":
        resultantImage = callingObject.greyScaleImage(Pixel::getValue);
        break;
      case "intensity-component":
        resultantImage = callingObject.greyScaleImage(Pixel::getIntensity);
        break;
      case "luma-component":
        resultantImage = callingObject.greyScaleImage(Pixel::getLuma);
        break;
      default:
          throw new IllegalArgumentException(String.format("Unknown component %s", component));
    }
    objectMap.put(destinationImageName, resultantImage);
  }
}

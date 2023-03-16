package controller.commands;

import java.util.Map;

import model.IMEModel;
import model.IPixel;

/**
 * This class is used to perform the operation of convert the given image into a greyscale image of
 * the given component.
 */
public class Greyscale extends AbstractIMECommand {

  private final String component;
  private final String sourceImageName;
  private final String destinationImageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param component            the color component based on which the image is to be greyscaled
   *                             into
   * @param sourceImageName      the name of the source image on which the operation is to be
   *                             performed
   * @param destinationImageName the name of the resultant image obtained after manipulation
   */
  public Greyscale(String component, String sourceImageName, String destinationImageName) {
    this.component = component;
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  /**
   * This method converts the given image into a greyscale image based on the given component and
   * puts the resultant image data object to the map.
   *
   * @param objectMap the map to store the manipulated image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel resultantImage;
    switch (component) {
      case "red-component":
        resultantImage = callingObject.greyScaleImage(IPixel::getRedComponent);
        break;
      case "green-component":
        resultantImage = callingObject.greyScaleImage(IPixel::getGreenComponent);
        break;
      case "blue-component":
        resultantImage = callingObject.greyScaleImage(IPixel::getBlueComponent);
        break;
      case "value-component":
        resultantImage = callingObject.greyScaleImage(IPixel::getValue);
        break;
      case "intensity-component":
        resultantImage = callingObject.greyScaleImage(IPixel::getIntensity);
        break;
      case "luma-component":
        resultantImage = callingObject.greyScaleImage(IPixel::getLuma);
        break;
      default:
        throw new IllegalArgumentException(String.format("Unknown component %s", component));
    }
    objectMap.put(destinationImageName, resultantImage);
  }
}

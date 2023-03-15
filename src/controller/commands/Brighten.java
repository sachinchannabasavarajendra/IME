package controller.commands;

import java.util.Map;
import model.IMEModel;

/**
 * This class is used to perform the operation of brightening or darkening the image based on the
 * given value.
 */
public class Brighten extends AbstractIMECommand {

  private final int increment;
  private final String sourceImageName;
  private final String destinationImageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param increment            the delta which determines whether the image is to be brightened or
   *                             darkened
   * @param sourceImageName      the name of the source image on which the operation is to be
   *                             performed
   * @param destinationImageName the name of the resultant image obtained after manipulation
   */
  public Brighten(int increment, String sourceImageName, String destinationImageName) {
    this.increment = increment;
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  /**
   * This method brightens or darkens the image based on the given delta value and puts the
   * resultant image data object to the map.
   *
   * @param objectMap the map to store the manipulated image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel brightenedImage = callingObject.alterBrightness(this.increment);
    objectMap.put(destinationImageName, brightenedImage);
  }
}

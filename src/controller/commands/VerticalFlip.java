package controller.commands;

import java.util.Map;

import model.IMEModel;

/**
 * This class is used to perform the operation of flipping the given image vertically.
 */
public class VerticalFlip extends AbstractIMECommand {

  private final String sourceImageName;
  private final String destinationImageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param sourceImageName      the name of the source image on which the operation is to be
   *                             performed
   * @param destinationImageName the name of the resultant image obtained after manipulation
   */
  public VerticalFlip(String sourceImageName, String destinationImageName) {
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  /**
   * This method flips the given image vertically and puts the resultant image data object to the
   * map.
   *
   * @param objectMap the map to store the manipulated image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel flippedImage = callingObject.verticalFlipImage();
    objectMap.put(destinationImageName, flippedImage);
  }
}

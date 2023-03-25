package controller.commands;

import java.util.Map;

import model.IMEModel;

/**
 * This class is used to perform the operation of combining the three greyscale images into a single
 * image that gets its red, green and blue components from the three images respectively.
 */
public class RGBCombine extends AbstractIMECommand {

  private final String destinationImageName;
  private final String redImage;
  private final String greenImage;
  private final String blueImage;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param destinationImageName the name of the resultant image obtained after manipulation
   * @param redImage             the image from which the red component needs to be taken
   * @param greenImage           the image from which the green component needs to be taken
   * @param blueImage            the image from which the blue component needs to be taken
   */
  public RGBCombine(String destinationImageName, String redImage, String greenImage,
                    String blueImage) {
    this.destinationImageName = destinationImageName;
    this.redImage = redImage;
    this.greenImage = greenImage;
    this.blueImage = blueImage;
  }

  /**
   * This method combines the three greyscale images into a single image and puts the resultant
   * image data object to the map.
   *
   * @param objectMap the map to store the image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel redImageComponent = getModelObject(objectMap, redImage);
    IMEModel greenImageComponent = getModelObject(objectMap, greenImage);
    IMEModel blueImageComponent = getModelObject(objectMap, blueImage);
    IMEModel combinedImage = redImageComponent.combineRGBImage(
            greenImageComponent, blueImageComponent);
    objectMap.put(destinationImageName, combinedImage);
  }
}

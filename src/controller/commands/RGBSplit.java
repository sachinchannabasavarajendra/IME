package controller.commands;

import java.util.List;
import java.util.Map;
import model.IMEModel;

/**
 * This class is used to perform the operation of splitting the given image into three greyscale
 * images containing its red, green and blue components respectively.
 */
public class RGBSplit extends AbstractIMECommand {

  private final String sourceImageName;
  private final String redScaleDestinationImageName;
  private final String greenScaleDestinationImageName;
  private final String blueScaleDestinationImageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param sourceImageName                the name of the source image on which the operation is to
   *                                       be performed
   * @param redScaleDestinationImageName   the destination image name of the red image component
   * @param greenScaleDestinationImageName the destination image name of the green image component
   * @param blueScaleDestinationImageName  the destination image name of the blue image component
   */
  public RGBSplit(String sourceImageName, String redScaleDestinationImageName,
      String greenScaleDestinationImageName,
      String blueScaleDestinationImageName) {
    this.sourceImageName = sourceImageName;
    this.redScaleDestinationImageName = redScaleDestinationImageName;
    this.greenScaleDestinationImageName = greenScaleDestinationImageName;
    this.blueScaleDestinationImageName = blueScaleDestinationImageName;
  }

  /**
   * This method splits the given image into three greyscale images and puts the manipulated image
   * data of all the images respectively in the map.
   *
   * @param objectMap the map to store the image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    List<IMEModel> resultantImageList = callingObject.rgbSplit();
    objectMap.put(redScaleDestinationImageName, resultantImageList.get(0));
    objectMap.put(greenScaleDestinationImageName, resultantImageList.get(1));
    objectMap.put(blueScaleDestinationImageName, resultantImageList.get(2));
  }
}

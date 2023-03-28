package controller.commands;

import java.util.Map;
import model.IMEModel;

/**
 * This class is used to perform the operation of transforming the color of the image to give it a
 * sepia tone based on the kernel filter value.
 */
public class SepiaColorTransform extends AbstractIMECommand {

  private final double[][] kernel;
  private final String sourceImageName;
  private final String destinationImageName;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param sourceImageName      the name of the source image on which the operation is to be
   *                             performed
   * @param destinationImageName the name of the resultant image obtained after manipulation
   */
  public SepiaColorTransform(String sourceImageName, String destinationImageName) {
    this.kernel = new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}};
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  /**
   * This method transforms the color of the image and gives it a sepia tone based on the kernel
   * filter value and puts the resultant image data object to the map.
   *
   * @param objectMap the map to store the manipulated image and its image data object respectively
   */
  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel greyscaleColorTransformedImage = callingObject.colorTransform(this.kernel);
    objectMap.put(destinationImageName, greyscaleColorTransformedImage);
  }
}

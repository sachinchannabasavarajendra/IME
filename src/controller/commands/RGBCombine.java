package controller.commands;

import java.util.Map;
import model.IMEModel;

public class RGBCombine extends AbstractIMECommand {

  private final String destinationImageName;
  private final String redImage;
  private final String greenImage;
  private final String blueImage;

  public RGBCombine(String destinationImageName, String redImage, String greenImage,
      String blueImage) {
    this.destinationImageName = destinationImageName;
    this.redImage = redImage;
    this.greenImage = greenImage;
    this.blueImage = blueImage;
  }

  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel redImageComponent = getModelObject(objectMap, redImage);
    IMEModel greenImageComponent = getModelObject(objectMap, greenImage);
    IMEModel blueImageComponent = getModelObject(objectMap, blueImage);
    IMEModel combinedImage = redImageComponent.combineRGBImage(redImageComponent,
        greenImageComponent, blueImageComponent);
    objectMap.put(destinationImageName, combinedImage);
  }
}

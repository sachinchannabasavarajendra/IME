package controller.commands;

import java.util.Map;
import model.IMEModel;

public class Brighten extends AbstractIMECommand {

  private final int increment;
  private final String sourceImageName;
  private final String destinationImageName;

  public Brighten(int increment, String sourceImageName, String destinationImageName) {
    this.increment = increment;
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel brightenedImage = callingObject.alterBrightness(this.increment);
    objectMap.put(destinationImageName, brightenedImage);
  }
}

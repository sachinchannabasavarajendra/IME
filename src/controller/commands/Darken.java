package controller.commands;

import java.util.Map;
import model.IMEModel;

public class Darken extends AbstractIMECommand {

  private final int decrement;
  private final String sourceImageName;
  private final String destinationImageName;

  public Darken(int decrement, String sourceImageName, String destinationImageName) {
    this.decrement = decrement;
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel darkenedImage = callingObject.alterBrightness(-decrement);
    objectMap.put(destinationImageName, darkenedImage);
  }
}

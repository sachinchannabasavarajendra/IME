package controller.commands;

import java.util.Map;

import model.IMEModel;

public class Dither extends AbstractIMECommand{

  private final String sourceImageName;
  private final String destinationImageName;

  public Dither(String sourceImageName, String destinationImageName) {
    this.sourceImageName = sourceImageName;
    this.destinationImageName = destinationImageName;
  }

  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    IMEModel ditheredImage = callingObject.dither();
    objectMap.put(destinationImageName, ditheredImage);
  }
}

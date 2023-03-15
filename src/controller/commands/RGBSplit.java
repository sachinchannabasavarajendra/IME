package controller.commands;

import java.util.List;
import java.util.Map;

import model.IMEModel;

public class RGBSplit extends AbstractIMECommand{
  private final String sourceImageName;
  private final String redScaleImageName;
  private final String greenScaleImageName;
  private final String blueScaleImageName;

  public RGBSplit(String sourceImageName, String redScaleImageName, String greenScaleImageName,
                  String blueScaleImageName) {
    this.sourceImageName = sourceImageName;
    this.redScaleImageName = redScaleImageName;
    this.greenScaleImageName = greenScaleImageName;
    this.blueScaleImageName = blueScaleImageName;
  }


  @Override
  public void execute(Map<String, IMEModel> objectMap) {
    IMEModel callingObject = getModelObject(objectMap, sourceImageName);
    List<IMEModel> resultantImageList;
    resultantImageList = callingObject.rgbSplit();
    objectMap.put(redScaleImageName, resultantImageList.get(0));
    objectMap.put(greenScaleImageName, resultantImageList.get(1));
    objectMap.put(blueScaleImageName, resultantImageList.get(2));
  }
}

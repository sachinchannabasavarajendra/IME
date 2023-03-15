package controller.commands;

import java.util.Map;
import model.IMEModel;

public abstract class AbstractIMECommand implements IMEModelCommand{
  public IMEModel getModelObject(Map<String, IMEModel> objectMap, String key) {
    try {
      return objectMap.get(key);
    } catch (Exception e) {
      throw new IllegalArgumentException("Object not found" + e.getMessage());
    }
  }
}

package controller.commands;

import java.util.Map;
import model.IMEModel;

/**
 * This is an abstract class which implements the command model interface which provides the
 * functionality of fetching the image data object against the corresponding image key.
 */
public abstract class AbstractIMECommand implements IMEModelCommand {

  /**
   * This method is used to fetch the image data object against the corresponding image key.
   *
   * @param objectMap the map which stores the image data object against the corresponding image
   *                  key
   * @param key       the image key against which the image data object is to be fetched
   * @return the image data object corresponding to the given key
   * @throws IllegalArgumentException if the given key is not found in the map
   */
  public IMEModel getModelObject(Map<String, IMEModel> objectMap, String key) {
    try {
      return objectMap.get(key);
    } catch (Exception e) {
      throw new IllegalArgumentException("Object not found" + e.getMessage());
    }
  }
}

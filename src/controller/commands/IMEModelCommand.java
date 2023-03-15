package controller.commands;

import java.util.Map;
import model.IMEModel;

/**
 * This is an interface for the commands to be executed for image manipulation.
 */
public interface IMEModelCommand {

  /**
   * This method specifies the operation to be performed on an image based on the given command.
   *
   * @param objectMap the map to store the image and its image data object respectively
   */
  void execute(Map<String, IMEModel> objectMap);
}

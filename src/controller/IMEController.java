package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The controller interface for the image manipulation and enhancement program.
 */
public interface IMEController {

  /**
   * Start the program, i.e. give control to the controller.
   */
  void execute() throws IOException;

  /**
   * Invokes the command object given the command type and required parameters.
   *
   * @param inputCommand the parameters for the command
   */
  void invokeCommand(String[] inputCommand);

  /**
   * Gets the image from the object hashmap.
   *
   * @param name the key which is used to get the image
   * @return BufferedImage instance of the image
   */
  BufferedImage getLoadedImage(String name);
}

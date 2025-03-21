package controller;

import java.io.IOException;

/**
 * The controller interface for the image manipulation and enhancement program.
 */
public interface IMEController {

  /**
   * Start the program, i.e. give control to the controller.
   */
  void execute() throws IOException;
}

package view;

import controller.Features;
import java.awt.image.BufferedImage;

/**
 * This represents the view of the application consisting of an image, its histogram and various
 * manipulations supported on it.
 */
public interface IView {

  /**
   * This is a method which handles the functionality of performing the action based on the set
   * action listener event.
   *
   * @param features the different methods supported by the application
   */
  void addFeatures(Features features);

  /**
   * This is a method used to display the histogram of the image that is currently being viewed by
   * the user of the application.
   *
   * @param image the buffered image
   */
  void drawHistogram(BufferedImage image);

  /**
   * Shows the error message as a pop up.
   *
   * @param message the description of the error to be shown.
   */
  void showErrorMessage(String message);
}

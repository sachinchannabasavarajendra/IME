package view;

import controller.Features;
import java.awt.image.BufferedImage;
import model.IMEModel;

public interface IView {

  void addFeatures(Features features);

  void drawHistogram(BufferedImage image);

  /**
   * Shows the error message as a pop up.
   * @param message the description of the error to be shown.
   */
  void ShowErrorMessage(String message);
}

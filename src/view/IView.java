package view;

import controller.Features;
import java.awt.image.BufferedImage;
import model.IMEModel;

public interface IView {
  void addFeatures(Features features);

  void drawHistogram(BufferedImage image);
}

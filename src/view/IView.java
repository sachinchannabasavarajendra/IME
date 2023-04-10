package view;

import controller.Features;
import model.IMEModel;

public interface IView {
  void addFeatures(Features features);

  void drawHistogram(IMEModel image);
}

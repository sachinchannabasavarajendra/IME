package controller;

import java.io.FileNotFoundException;
import view.IView;

public class GRIMEController implements Features {

  private IView view;

  public void setView(IView v) throws FileNotFoundException {
    view = v;
    view.addFeatures(this);
  }
}

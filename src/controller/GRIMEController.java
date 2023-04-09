package controller;

import view.IView;

public class GRIMEController implements Features {

  private IView view;

  public void setView(IView v) {
    view = v;
  }
}

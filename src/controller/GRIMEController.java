package controller;

import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.util.List;
import view.IView;


/**
 * This is an implementation of the features interface which supports the manipulations on the image
 * by the graphical user interface.
 */

public class GRIMEController implements Features {

  IView view;

  private final IMEController imeController;

  /**
   * This is a constructor used to instantiate the above class.
   */
  public GRIMEController() {
    imeController = new IMEControllerImpl(new InputStreamReader(System.in), System.out);
  }

  /**
   * This is a method used to set up the view to be displayed to the user
   *
   * @param v the view implementation object
   */
  public void setView(IView v) {
    this.view = v;
    v.addFeatures(this);
  }

  private void invokeCommand(String[] inputCommand) {
    try {
      imeController.invokeCommand(inputCommand);
    } catch (Exception e) {
      this.view.ShowErrorMessage(e.getMessage());
    }
  }

  @Override
  public BufferedImage GetLoadedImage(String name) {
    return imeController.GetLoadedImage(name);
  }

  @Override
  public void LoadImage(String imagePath, String imageName) {
    String command = "load";
    invokeCommand(new String[]{command, imagePath, imageName});
  }

  @Override
  public void BlurImage(String src, String dest) {
    String command = "blur";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void Brighten(String delta, String src, String dest) {
    String command = "brighten";
    invokeCommand(new String[]{command, delta, src, dest});
  }

  @Override
  public void Dither(String src, String dest) {
    String command = "dither";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void Greyscale(String src, String dest, String component) {
    String command = "greyscale";
    invokeCommand(new String[]{command, component, src, dest});
  }

  @Override
  public void GreyscaleColorTransform(String src, String dest) {
    String command = "greyscale";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void HorizontalFlip(String src, String dest) {
    String command = "horizontal-flip";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void VerticalFlip(String src, String dest) {
    String command = "vertical-flip";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void Sepia(String src, String dest) {
    String command = "sepia";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void Sharpen(String src, String dest) {
    String command = "sharpen";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void SaveImage(String imagePath, String src) {
    String command = "save";
    invokeCommand(new String[]{command, imagePath, src});
  }

  @Override
  public void RGBCombine(String dest, List<String> images) {
    this.LoadImage(images.get(0), dest + "red");
    this.LoadImage(images.get(1), dest + "green");
    this.LoadImage(images.get(2), dest + "blue");

    String command = "rgb-combine";
    invokeCommand(new String[]{command, dest, dest + "red", dest + "green", dest + "blue"});
  }

  @Override
  public void RGBSplit(String src, String red, String green, String blue) {
    String command = "rgb-split";
    invokeCommand(new String[]{command, src, red, green, blue});
  }
}

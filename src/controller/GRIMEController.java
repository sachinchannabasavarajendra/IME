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

  private IView view;

  private final IMEController imeController;

  /**
   * This is a constructor used to instantiate the above class.
   */
  public GRIMEController() {
    imeController = new IMEControllerImpl(new InputStreamReader(System.in), System.out);
  }

  /**
   * This is a method used to set up the view to be displayed to the user.
   *
   * @param view the view implementation object
   */
  public void setView(IView view) {
    this.view = view;
    view.addFeatures(this);
  }

  private void invokeCommand(String[] inputCommand) {
    try {
      imeController.invokeCommand(inputCommand);
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public BufferedImage getLoadedImage(String name) {
    return imeController.getLoadedImage(name);
  }

  @Override
  public void loadImage(String imagePath, String imageName) {
    String command = "load";
    invokeCommand(new String[]{command, imagePath, imageName});
  }

  @Override
  public void blurImage(String src, String dest) {
    String command = "blur";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void brighten(String delta, String src, String dest) {
    String command = "brighten";
    invokeCommand(new String[]{command, delta, src, dest});
  }

  @Override
  public void dither(String src, String dest) {
    String command = "dither";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void greyscale(String src, String dest, String component) {
    String command = "greyscale";
    invokeCommand(new String[]{command, component, src, dest});
  }

  @Override
  public void greyscaleColorTransform(String src, String dest) {
    String command = "greyscale";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void horizontalFlip(String src, String dest) {
    String command = "horizontal-flip";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void verticalFlip(String src, String dest) {
    String command = "vertical-flip";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void sepiaColorTransform(String src, String dest) {
    String command = "sepia";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void sharpen(String src, String dest) {
    String command = "sharpen";
    invokeCommand(new String[]{command, src, dest});
  }

  @Override
  public void saveImage(String imagePath, String src) {
    String command = "save";
    invokeCommand(new String[]{command, imagePath, src});
  }

  @Override
  public void rgbCombine(String dest, List<String> images) {
    this.loadImage(images.get(0), dest + "red");
    this.loadImage(images.get(1), dest + "green");
    this.loadImage(images.get(2), dest + "blue");

    String command = "rgb-combine";
    invokeCommand(new String[]{command, dest, dest + "red", dest + "green", dest + "blue"});
  }

  @Override
  public void rgbSplit(String src, String red, String green, String blue) {
    String command = "rgb-split";
    invokeCommand(new String[]{command, src, red, green, blue});
  }
}

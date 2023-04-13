package controller;

import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import controller.commands.IMEModelCommand;
import model.IMEModel;
import service.imagefilesaver.SaveHelper;
import view.IView;

public class GRIMEController extends IMEControllerImpl implements Features {

  private IView view;

  public GRIMEController() {
    super(new InputStreamReader(System.in), System.out);
  }

  public void setView(IView v) {
    view = v;
    view.addFeatures(this);
  }

  private void invokeModelMethod(String command, String[] inputCommand) {
    IMEModelCommand imeModelCommand;
    Function<String[], IMEModelCommand> cmd =
        knownCommands.getOrDefault(command, null);
    imeModelCommand = cmd.apply(inputCommand);
    imeModelCommand.execute(this.objectMap);
  }

  @Override
  public BufferedImage GetLoadedImage(String name) {
    IMEModel image = this.objectMap.get(name);
    return SaveHelper.createRGBBufferedImage(image);
  }

  @Override
  public void LoadImage(String imagePath, String imageName) {
    String command = "load";
    invokeModelMethod(command, new String[]{command, imagePath, imageName});
  }

  @Override
  public void BlurImage(String src, String dest) {
    String command = "blur";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void Brighten(String delta, String src, String dest) {
    String command = "brighten";
    invokeModelMethod(command, new String[]{command, delta, src, dest});
  }

  @Override
  public void Dither(String src, String dest) {
    String command = "dither";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void Greyscale(String src, String dest, String component) {
    String command = "greyscale";
    invokeModelMethod(command, new String[]{command, component, src, dest});
  }

  @Override
  public void GreyscaleColorTransform(String src, String dest) {
    String command = "greyscale";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void HorizontalFlip(String src, String dest) {
    String command = "horizontal-flip";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void VerticalFlip(String src, String dest) {
    String command = "vertical-flip";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void Sepia(String src, String dest) {
    String command = "sepia";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void Sharpen(String src, String dest) {
    String command = "sharpen";
    invokeModelMethod(command, new String[]{command, src, dest});
  }

  @Override
  public void SaveImage(String imagePath, String src) {
    String command = "save";
    invokeModelMethod(command, new String[]{command, imagePath, src});
  }

  @Override
  public void RGBCombine(String dest, List<String> images) {
    this.LoadImage(images.get(0), dest + "red");
    this.LoadImage(images.get(1), dest + "green");
    this.LoadImage(images.get(2), dest + "blue");

    String command = "rgb-combine";
    invokeModelMethod(command, new String[]{command, dest, dest + "red", dest + "green", dest + "blue"});
  }

  @Override
  public void RGBSplit(String src, String red, String green, String blue) {
    String command = "rgb-split";
    invokeModelMethod(command, new String[]{command, src, red, green, blue});
  }
}

import controller.Features;
import controller.GRIMEController;
import controller.IMEController;
import controller.IMEControllerImpl;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import view.IView;
import view.JFrameView;

/**
 * This class represents the entry point to the Image manipulator by calling the main method.
 */
public class Main {

  /**
   * Represents the starting point of the program, initialises a controller and begins the
   * execution.
   *
   * @param args command line arguments of the program.
   */
  public static void main(String[] args) {
    IMEController imeController;
    Features grimeController;
    try {
      if (args.length == 2 && args[0].equalsIgnoreCase("-file")) {
        imeController = new IMEControllerImpl(new InputStreamReader(new FileInputStream(args[1])),
            System.out);
        imeController.execute();
      } else if (args.length == 1 && args[0].equalsIgnoreCase("-text")) {
        imeController = new IMEControllerImpl(new InputStreamReader(System.in), System.out);
        imeController.execute();
      } else if (args.length == 0) {
        IView view = new JFrameView("Image Processing Application");
        grimeController = new GRIMEController(view);
      } else {
        System.out.println("Invalid command-line arguments.");
        System.exit(1);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
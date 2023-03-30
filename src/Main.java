import controller.IMEController;
import controller.IMEControllerImpl;

import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * This class represents the entry point to the Image manipulator by calling the main method.
 */
public class Main {
  /**
   * Represents the starting point of the program, initialises a controller and begins the
   * execution.
   * @param args command line arguments of the program.
   */
  public static void main(String[] args) {
    IMEController controller;
    try {
      if(args.length > 0 && args[0].equalsIgnoreCase("-file")) {
        controller = new IMEControllerImpl(new InputStreamReader(new FileInputStream(args[1])), System.out);
      } else {
        controller = new IMEControllerImpl(new InputStreamReader(System.in), System.out);
      }

      controller.execute();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
import controller.IMEController;
import controller.IMEControllerImpl;
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
    IMEController controller = new IMEControllerImpl(new InputStreamReader(System.in), System.out);
    try {
      controller.execute();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
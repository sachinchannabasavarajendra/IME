import java.io.InputStreamReader;

import controller.IMEController;
import controller.IMEControllerImpl;

public class Main {
  public static void main(String[] args) {
    IMEController controller = new IMEControllerImpl(new InputStreamReader(System.in), System.out);
    try {
      controller.go();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
import controller.IMEController;
import controller.IMEControllerImpl;

public class Main {
  public static void main(String[] args) {
    IMEController controller = new IMEControllerImpl(System.in, System.out);
    controller.go();
  }
}
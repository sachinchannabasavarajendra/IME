package controller.imageFileSaver;

import java.io.IOException;
import model.IMEModel;

public interface SaveImage {

  void save(String imagePath, IMEModel model) throws IOException;
}

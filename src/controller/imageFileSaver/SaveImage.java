package controller.imageFileSaver;

import java.io.IOException;
import model.IMEModel;

public interface SaveImage {

  void save(String imagePath, String imageName, IMEModel model) throws IOException;
}

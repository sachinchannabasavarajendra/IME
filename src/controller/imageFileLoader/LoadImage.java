package controller.imageFileLoader;

import java.io.FileNotFoundException;
import model.IMEModel;

public interface LoadImage {
  IMEModel load(String imagePath, String imageName) throws FileNotFoundException;
}

package service.imagefilesaver;

import java.awt.*;
import java.awt.image.BufferedImage;
import model.IMEModel;
import model.IPixel;

/**
 * This is a helper class used to convert the model image to buffered image.
 */
public class SaveHelper {

  /**
   * This function converts the model image to buffered image.
   *
   * @param model image of model type
   * @return buffered image
   */
  public static BufferedImage createRGBBufferedImage(IMEModel model) {
    BufferedImage image = new BufferedImage(model.getImageWidth(), model.getImageHeight(),
        BufferedImage.TYPE_INT_RGB);
    IPixel[][] imageData = model.getImageData();
    for (int i = 0; i < model.getImageHeight(); i++) {
      for (int j = 0; j < model.getImageWidth(); j++) {
        Color color = new Color(imageData[i][j].getRedComponent(),
            imageData[i][j].getGreenComponent(), imageData[i][j].getBlueComponent());
        image.setRGB(j, i, color.getRGB());
      }
    }
    return image;
  }
}

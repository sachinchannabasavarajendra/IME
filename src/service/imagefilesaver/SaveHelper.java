package service.imagefilesaver;

import java.awt.*;
import java.awt.image.BufferedImage;

import model.IMEModel;
import model.IPixel;

public class SaveHelper {
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

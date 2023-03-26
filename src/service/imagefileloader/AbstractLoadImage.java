package service.imagefileloader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import model.IMEModel;
import model.IMEModelImpl;
import model.IPixel;
import model.Pixel;

public abstract class AbstractLoadImage implements LoadImage {

  /**
   * This method is used to load and process image files of bmp, png and jpg/jpeg formats.
   *
   * @param image the BufferedImage to be processed
   * @return processed image data
   */
  protected IMEModel processImage(BufferedImage image) {
    IPixel[][] imageData;
    int height;
    int width;
    int[] pixelData = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
        new int[image.getWidth() * image.getHeight()], 0, image.getWidth());
    height = image.getHeight();
    width = image.getWidth();
    imageData = new Pixel[height][width];
    int index = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color color = new Color(pixelData[index++]);
        imageData[i][j] = new Pixel(color.getRed(), color.getGreen(), color.getBlue());
      }
    }
    return new IMEModelImpl(imageData, height, width, 255);
  }
}
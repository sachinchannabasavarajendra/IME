package service.imagefileloader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import model.IMEModel;
import model.IMEModelImpl;
import model.IPixel;
import model.Pixel;

public class LoadImageFile implements LoadImage {

  /**
   * This method is used to load a jpg/jpeg/png/bmp image file located at the given path.
   *
   * @param imagePath the path of the image file
   * @param imageName the name of the image file
   * @return processed image data
   * @throws FileNotFoundException if file is not located at the given file path
   */
  @Override
  public IMEModel load(String imagePath, String imageName) throws FileNotFoundException {
    try {
      BufferedImage image = ImageIO.read(new File(imagePath));
      return processImage(image);
    } catch (Exception e) {
      throw new FileNotFoundException("File " + imagePath + " not found!");
    }
  }

  /**
   * This method is used to load and process image files of bmp, png and jpg/jpeg formats.
   *
   * @param image the BufferedImage to be processed
   * @return processed image data
   */
  private IMEModel processImage(BufferedImage image) {
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

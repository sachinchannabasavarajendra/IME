package service.imagefilesaver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.IMEModel;
import model.IPixel;

/**
 * This is an abstract class which implements the SaveImage interface and provides the functionality
 * of saving images of different formats at the specified path.
 */
public abstract class AbstractSaveImage implements SaveImage {

  /**
   * This method is used to save the images of different formats at the specified path.
   *
   * @param format    the file format in which the image is to be saved
   * @param imagePath the path where the file should be saved at
   * @param model     image data of the image to be saved in the file
   * @throws IOException if the given path is incorrect
   */
  protected void saveImage(String format, String imagePath, IMEModel model)
      throws IOException {
    try {
      BufferedImage image = SaveHelper.createRGBBufferedImage(model);
      File output = new File(imagePath);
      output.mkdirs();
      ImageIO.write(image, format, output);
    } catch (Exception e) {
      throw new IOException(String.format("Error saving %s file: %s", format, imagePath));
    }
  }
}
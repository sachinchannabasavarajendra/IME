package service.imagefileloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import model.IMEModel;

/**
 * This class is used to perform the operation of loading and processing image files of jpg/jpeg
 * format.
 */
public class LoadJPG extends AbstractLoadImage {

  /**
   * This method is used to load a jpg/jpeg image file located at the given path.
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
}
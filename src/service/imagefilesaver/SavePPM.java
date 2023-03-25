package service.imagefilesaver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import model.IMEModel;
import model.IPixel;

/**
 * This is an implementation of the SaveImage interface which is used to save the manipulated image
 * files of ppm format.
 */
public class SavePPM implements SaveImage {

  /**
   * This method is used to save the manipulated image file at the given path.
   *
   * @param imagePath the path where the file should be saved at
   * @param model     image data of the image to be saved in the file
   * @throws IOException if the given path is incorrect
   */
  @Override
  public void save(String imagePath, IMEModel model) throws IOException {
    try {
      IPixel[][] imageData = model.getImageData();
      int height = model.getImageHeight();
      int width = model.getImageWidth();
      File output = new File(imagePath);
      output.mkdirs();
      if (output.exists()) {
        output.delete();
        output = new File(imagePath);
      }
      Writer writer = new FileWriter(output);
      writer.write("P3\n");

      writer.write(width + " ");
      writer.write(height + "\n");
      writer.write(model.getMaxValue() + "\n");

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          writer.write(imageData[i][j].getRedComponent() + "\n");
          writer.write(imageData[i][j].getGreenComponent() + "\n");
          writer.write(imageData[i][j].getBlueComponent() + "\n");
        }
      }
      writer.close();
    } catch (Exception e) {
      throw new IOException(String.format("Error saving ppm file: %s", imagePath));
    }
  }
}

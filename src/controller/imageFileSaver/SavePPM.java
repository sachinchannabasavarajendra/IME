package controller.imageFileSaver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.IMEModel;
import model.Pixel;

public class SavePPM implements SaveImage {

  @Override
  public void save(String imagePath, String imageName, IMEModel model) throws IOException {
    try {
      Pixel[][] imageData = model.getImageData();
      int height = model.getImageHeight();
      int width = model.getImageWidth();
      File output = new File(imagePath);
      Writer writer = new FileWriter(output);
      writer.write("P3\n");

      writer.write("# Image data of the resultant ppm\n");

      writer.write(width + " ");
      writer.write(height + "\n");
      writer.write(255 + "\n");

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          writer.write(imageData[i][j].getRedComponent() + "\n");
          writer.write(imageData[i][j].getGreenComponent() + "\n");
          writer.write(imageData[i][j].getBlueComponent() + "\n");
        }
      }
      writer.close();
    } catch (IOException e) {
      throw new IOException("Error saving ppm file");
    }
  }
}

package service.imageFileLoader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import model.IMEModel;
import model.IMEModelImpl;
import model.Pixel;

/**
 * This is an implementation of the LoadImage interface which is used to load and process image
 * files of ppm format.
 */
public class LoadPPM implements LoadImage {

  private final InputStream in;

  /**
   * This is a constructor which is used to instantiate the above class.
   *
   * @param in input stream for the file to be processed
   */
  public LoadPPM(InputStream in) {
    this.in = in;
  }

  /**
   * This method is used to load an image file located at the given path.
   *
   * @param imagePath the path of the image file
   * @param imageName the name of the image file
   * @return processed image data
   * @throws FileNotFoundException if file is not located at the given file path
   */
  @Override
  public IMEModel load(String imagePath, String imageName) throws FileNotFoundException, IllegalStateException {
    Scanner sc;
    Pixel[][] imageData;
    int height;
    int width;

    try {
      sc = new Scanner(this.in);
    } catch (Exception e) {
      throw new FileNotFoundException("File " + imagePath + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    width = sc.nextInt();
    height = sc.nextInt();
    int maxValue = sc.nextInt();

    imageData = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int redComponent = sc.nextInt();
        int greenComponent = sc.nextInt();
        int blueComponent = sc.nextInt();

        if (redComponent > maxValue || blueComponent > maxValue || greenComponent > maxValue) {
          throw new IllegalArgumentException("Component Value cannot be more than max value " + maxValue);
        }

        if (redComponent < 0 || blueComponent < 0 || greenComponent < 0) {
          throw new IllegalArgumentException("Component Value cannot be negative");
        }

        imageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
      }
    }

    return new IMEModelImpl(imageData, height, width, maxValue);
  }
}
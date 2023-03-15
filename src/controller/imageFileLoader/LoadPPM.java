package controller.imageFileLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.IMEModel;
import model.IMEModelImpl;
import model.Pixel;

public class LoadPPM implements LoadImage {

  @Override
  public IMEModel load(String imagePath, String imageName) throws FileNotFoundException {
    Scanner sc;
    Pixel[][] imageData;
    int height;
    int width;

    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
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
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
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

        imageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
      }
    }

    return new IMEModelImpl(imageData, height, width);
  }
}
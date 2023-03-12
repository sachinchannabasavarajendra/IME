import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.function.Function;

public class IMEModelImpl implements IMEModel {

  private final Pixel[][] imageData;
  private final int height;
  private final int width;

  private IMEModelImpl(Pixel[][] imageData, int height, int width) {
    this.imageData = imageData;
    this.height = height;
    this.width = width;
  }

  public static ImageBuilder getBuilder() {
    return new ImageBuilder();
  }

  @Override
  public Pixel[][] getImageData() {
    return imageData;
  }

  @Override
  public void saveImageFile(String imageName, String filePath, String fileType) {
    try {
      File output = new File(filePath + imageName + "." + fileType);
      Writer writer = new FileWriter(output);
      writer.write("P3\n");

      writer.write("# Image data of the resultant ppm\n");

      writer.write(this.width + " \n");
      writer.write(this.height + "\n");
      writer.write(255 + " \n");

      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          writer.write(this.imageData[i][j].getRedComponent() + "\n");
          writer.write(this.imageData[i][j].getGreenComponent() + "\n");
          writer.write(this.imageData[i][j].getBlueComponent() + "\n");
        }
      }
      writer.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private IMEModel greyScaleImage(Function<Pixel, Integer> func) {
    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = func.apply(imageData[i][j]);
        newImageData[i][j] = new Pixel(value, value, value);
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel redGreyScaleImage() {
    return greyScaleImage(Pixel::getRedComponent);
  }

  @Override
  public IMEModel greenGreyScaleImage() {
    return greyScaleImage(Pixel::getGreenComponent);
  }

  @Override
  public IMEModel blueGreyScaleImage() {
    return greyScaleImage(Pixel::getBlueComponent);
  }

  @Override
  public IMEModel horizontalFlipImage() {
    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < imageData.length; i++) {
      newImageData[i] = reverse(imageData[i]);
    }

    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel verticalFlipImage() {
    Pixel[][] newImageData = new Pixel[height][width];
    int n = imageData.length - 1;
    for (int i = n; i >= 0; i--) {
      newImageData[n - i] = imageData[i];
    }

    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel alterBrightness(int delta) {

    Pixel[][] newImageData = new Pixel[height][width];

    if (delta < 0) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int redComponent = Math.max(this.imageData[i][j].getRedComponent() + delta, 0);
          int greenComponent = Math.max(this.imageData[i][j].getGreenComponent() + delta, 0);
          int blueComponent = Math.max(this.imageData[i][j].getBlueComponent() + delta, 0);

          newImageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
        }
      }
    } else {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int redComponent = Math.min(this.imageData[i][j].getRedComponent() + delta, 255);
          int greenComponent = Math.min(this.imageData[i][j].getGreenComponent() + delta, 255);
          int blueComponent = Math.min(this.imageData[i][j].getBlueComponent() + delta, 255);

          newImageData[i][j] = new Pixel(redComponent, greenComponent, blueComponent);
        }
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  @Override
  public IMEModel combineRGBImage(IMEModel redScaleImage, IMEModel blueScaleImage, IMEModel greenScaleImage) {

    Pixel[][] newImageData = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newImageData[i][j] = new Pixel(
                redScaleImage.getImageData()[i][j].getRedComponent(),
                greenScaleImage.getImageData()[i][j].getGreenComponent(),
                blueScaleImage.getImageData()[i][j].getBlueComponent());
      }
    }
    return new IMEModelImpl(newImageData, height, width);
  }

  static Pixel[] reverse(Pixel[] a) {
    int n = a.length;
    Pixel[] newRow = a.clone();
    Pixel t;
    for (int i = 0; i < n / 2; i++) {
      t = newRow[i];
      newRow[i] = newRow[n - i - 1];
      newRow[n - i - 1] = t;
    }
    return newRow;
  }

  public static class ImageBuilder {
    Pixel[][] imageData;
    int height;
    int width;

    public ImageBuilder loadFile(String filename) throws FileNotFoundException {

      Scanner sc;

      try {
        sc = new Scanner(new FileInputStream(filename));
      } catch (FileNotFoundException e) {
        throw new FileNotFoundException("File " + filename + " not found!");
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

      return this;
    }

    public IMEModel build () {
      return new IMEModelImpl(imageData, height, width);
    }

  }

}

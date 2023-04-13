package controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

public class GRIMEControllerTest {
  GRIMEController grimeController;

  public GRIMEControllerTest() {
    grimeController = new GRIMEController();
  }

  @Test
  public void loadImage() throws Exception{
    grimeController.loadImage("./input/koala.ppm", "test");
    grimeController.saveImage("./output/koalaSave.ppm", "test");
    assertArrayEquals(Files.readAllBytes(Path.of("./input/koala.ppm")),
            Files.readAllBytes(Path.of("output/koalaSave.ppm")));
  }

  @Test
  public void blurImage() throws Exception{
    grimeController.loadImage("./input/ms.png", "test");
    grimeController.blurImage("test", "testBlur");
    grimeController.saveImage("./output/msBlur.png", "testBlur");
    assertArrayEquals(Files.readAllBytes(Path.of("./input/msb.png")),
            Files.readAllBytes(Path.of("output/msBlur.png")));
  }

  @Test
  public void brighten() throws Exception{
    grimeController.loadImage("./input/koala.ppm", "test");
    grimeController.brighten("100", "test", "testBlur");
    grimeController.saveImage("./output/koalaB.ppm", "testBlur");
    assertArrayEquals(Files.readAllBytes(Path.of("./input/koala-brighten.ppm")),
            Files.readAllBytes(Path.of("output/koalaB.ppm")));
  }

  @Test
  public void dither() throws Exception{
    grimeController.loadImage("./input/ms.jpg", "test");
    grimeController.dither("test", "testBlur");
    grimeController.saveImage("output/msd.png", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/msdd.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msd.png"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);
  }

  @Test
  public void greyscale() throws Exception{

    grimeController.loadImage("input/koala.ppm", "test");
    grimeController.greyscale("test", "testBlur", "red-component");
    grimeController.saveImage("output/ms-red.png", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/koalaRed.png"));
    BufferedImage img2 = ImageIO.read(new File("output/ms-red.png"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);

  }

  @Test
  public void greyscaleColorTransform() throws Exception {

    grimeController.loadImage("input/koala.ppm", "test");
    grimeController.greyscaleColorTransform("test", "testBlur");
    grimeController.saveImage("output/koalaLuma.bmp", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/koalaLuma.png"));
    BufferedImage img2 = ImageIO.read(new File("output/koalaLuma.bmp"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);

  }

  @Test
  public void horizontalFlip() throws Exception {
    grimeController.loadImage("input/koalHFlip.bmp", "test");
    grimeController.horizontalFlip("test", "testBlur");
    grimeController.saveImage("output/koalHFlip.bmp", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/koalHFlip.png"));
    BufferedImage img2 = ImageIO.read(new File("output/koalHFlip.bmp"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);
  }

  @Test
  public void verticalFlip() throws Exception{
    grimeController.loadImage("input/ms.bmp", "test");
    grimeController.verticalFlip("test", "testBlur");
    grimeController.saveImage("output/msV.png", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/msV.bmp"));
    BufferedImage img2 = ImageIO.read(new File("output/msV.png"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);
  }

  @Test
  public void sepiaColorTransform() throws Exception{

    grimeController.loadImage("input/ms.bmp", "test");
    grimeController.sepiaColorTransform("test", "testBlur");
    grimeController.saveImage("output/mssepia.png", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/mssepia.png"));
    BufferedImage img2 = ImageIO.read(new File("output/mssepia.png"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);
  }

  @Test
  public void sharpen() throws Exception {
    grimeController.loadImage("input/ms.bmp", "test");
    grimeController.sharpen("test", "testBlur");
    grimeController.saveImage("output/msSharpen.bmp", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/mss.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msSharpen.bmp"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);
  }

  @Test
  public void rgbSplitandCombine() throws Exception{
    grimeController.loadImage("input/koala.ppm", "test");
    grimeController.rgbSplit("test", "testRed", "testGreen", "testBlue");
    grimeController.rgbCombine("testBlur", new ArrayList<String>(
            Arrays.asList("input/koala-red.ppm", "input/koala-green.ppm", "input/koala-blue.ppm")));
    grimeController.saveImage("output/msCombine.bmp", "testBlur");

    BufferedImage img1 = ImageIO.read(new File("input/msCombine.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msCombine.bmp"));

    assertEquals(0.0, compareImages(img1, img2), 0.0);
  }

  private double compareImages(BufferedImage img1, BufferedImage img2) {
    int w1 = img1.getWidth();
    int w2 = img2.getWidth();
    int h1 = img1.getHeight();
    int h2 = img2.getHeight();
    if ((w1 != w2) || (h1 != h2)) {
      return 100.0;
    } else {
      long diff = 0;
      for (int j = 0; j < h1; j++) {
        for (int i = 0; i < w1; i++) {
          //Getting the RGB values of a pixel
          int pixel1 = img1.getRGB(i, j);
          Color color1 = new Color(pixel1, true);
          int r1 = color1.getRed();
          int g1 = color1.getGreen();
          int b1 = color1.getBlue();
          int pixel2 = img2.getRGB(i, j);
          Color color2 = new Color(pixel2, true);
          int r2 = color2.getRed();
          int g2 = color2.getGreen();
          int b2 = color2.getBlue();
          //sum of differences of RGB values of the two images
          long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
          diff = diff + data;
        }
      }
      double avg = diff / (w1 * h1 * 3);
      return (avg / 255) * 100;
    }
  }
}
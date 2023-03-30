package controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test class to test functionity of the controller, command through inputs given as text commands.
 */
public class IMEControllerImplTest {

  IMEController controller;

  @Before
  public void setup() {
    controller = null;
  }

  @Test
  public void loadImageFailure() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala1.ppm koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: koala1.ppm (No such file or directory)", out.toString());
  }

  @Test
  public void testLoadWithBrightenAndSave() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "brighten 100 koala koala1\n" +
            "save output/koala-brighten.ppm koala1");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-brighten.ppm")),
            Files.readAllBytes(Path.of("output/koala-brighten.ppm")));
  }

  @Test
  public void testLoadFileWithWrongFormat() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/loadTest.txt koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: Given file type is not valid",
            out.toString());
  }

  @Test
  public void testLoadLargeFile() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/koala.ppm koala\n" +
            "save output/koala.ppm koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala.ppm")),
            Files.readAllBytes(Path.of("output/koala.ppm")));
  }

  @Test
  public void testLoadInvalidPPMWithValueGreaterThanMax() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/max_great.ppm koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: Component Value cannot be more than max value 255", out.toString());
  }

  @Test
  public void testLoadInvalidPPMWithNegativeComponentValue() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/min_less.ppm koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: Component Value cannot be negative", out.toString());
  }

  @Test
  public void testLoadImageThroughScript() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run input/loadTest.txt");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala.ppm")),
            Files.readAllBytes(Path.of("output/koala.ppm")));
  }

  @Test
  public void testRunInvalidCommands() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("loadImage input/min_less.ppm koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: Bad input command :- loadImage", out.toString());
  }

  @Test
  public void testLoadImageFileDirectly() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "save output/koala.ppm koala");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("koala.ppm")),
            Files.readAllBytes(Path.of("output/koala.ppm")));
  }

  @Test
  public void testInvalidLoadCommandParams() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala test");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: Load expects 2 parameters", out.toString());
  }

  @Test
  public void testRedGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "greyscale red-component koala koala-red\n" +
            "save output/koala-red.ppm koala-red");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-red.ppm")),
            Files.readAllBytes(Path.of("output/koala-red.ppm")));
  }

  @Test
  public void testBlueGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "greyscale blue-component koala koala-blue\n" +
            "save output/koala-blue.ppm koala-blue");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-blue.ppm")),
            Files.readAllBytes(Path.of("output/koala-blue.ppm")));
  }

  @Test
  public void testGreenGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "greyscale green-component koala koala-green\n" +
            "save output/koala-green.ppm koala-green");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-green.ppm")),
            Files.readAllBytes(Path.of("output/koala-green.ppm")));
  }

  @Test
  public void testValueGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "greyscale value-component koala koala-value\n" +
            "save output/koala-value.ppm koala-value");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-value.ppm")),
            Files.readAllBytes(Path.of("output/koala-value.ppm")));
  }

  @Test
  public void testIntensityGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "greyscale intensity-component koala koala-intensity\n" +
            "save output/koala-intensity.ppm koala-intensity");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-intensity.ppm")),
            Files.readAllBytes(Path.of("output/koala-intensity.ppm")));
  }

  @Test
  public void tesLumaGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "greyscale luma-component koala koala-luma\n" +
            "save output/koala-luma.ppm koala-luma");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-luma.ppm")),
            Files.readAllBytes(Path.of("output/koala-luma.ppm")));
  }

  @Test
  public void testImageHorizontalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "horizontal-flip koala koala-horizontal\n" +
            "save output/koala-horizontal.ppm koala-horizontal");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-horizontal.ppm")),
            Files.readAllBytes(Path.of("output/koala-horizontal.ppm")));
  }

  @Test
  public void testImageVerticalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "vertical-flip koala koala-vertical\n" +
            "save output/koala-vertical.ppm koala-vertical");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-vertical.ppm")),
            Files.readAllBytes(Path.of("output/koala-vertical.ppm")));
  }

  @Test
  public void testHorizontalVertical() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "vertical-flip koala koala-vertical\n" +
            "horizontal-flip koala-vertical koala-ver-hor\n" +
            "horizontal-flip koala koala-horizontal\n" +
            "vertical-flip koala-horizontal koala-hor-ver\n" +
            "save output/koala-hor-ver.ppm koala-hor-ver\n" +
            "save output/koala-ver-hor.ppm koala-ver-hor");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("output/koala-ver-hor.ppm")),
            Files.readAllBytes(Path.of("output/koala-hor-ver.ppm")));
  }

  @Test
  public void testTwoFlips() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "vertical-flip koala koala-vertical\n" +
            "vertical-flip koala-vertical koala-ver-ver\n" +
            "horizontal-flip koala koala-horizontal\n" +
            "horizontal-flip koala-horizontal koala-hor-hor\n" +
            "save output/koala-hor-hor.ppm koala-hor-hor\n" +
            "save output/koala-ver-ver.ppm koala-ver-ver");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("output/koala-hor-hor.ppm")),
            Files.readAllBytes(Path.of("koala.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("output/koala-ver-ver.ppm")),
            Files.readAllBytes(Path.of("koala.ppm")));
  }

  @Test
  public void testDarkenImage() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "brighten -50 koala koala-darken\n" +
            "save output/koala-darken.ppm koala-darken");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-darken.ppm")),
            Files.readAllBytes(Path.of("output/koala-darken.ppm")));
  }

  @Test
  public void testBrightenImageWith0() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "brighten 0 koala koala-brighten-1\n" +
            "save output/koala-brighten-1.ppm koala-brighten-1");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("koala.ppm")),
            Files.readAllBytes(Path.of("output/koala-brighten-1.ppm")));
  }

  @Test
  public void testBrightenImageMaxValue() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "brighten 256 koala koala-brightest\n" +
            "save output/koala-brightest.ppm koala-brightest");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-brightest.ppm")),
            Files.readAllBytes(Path.of("output/koala-brightest.ppm")));
  }

  @Test
  public void testDarkenImageMinValue() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "brighten -256 koala koala-black\n" +
            "save output/koala-black.ppm koala-black");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-black.ppm")),
            Files.readAllBytes(Path.of("output/koala-black.ppm")));
  }

  @Test
  public void testSplitImage() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "rgb-split koala koala-red koala-green koala-blue\n" +
            "save output/koala-red-1.ppm koala-red\n" +
            "save output/koala-green-1.ppm koala-green\n" +
            "save output/koala-blue-1.ppm koala-blue");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-red.ppm")),
            Files.readAllBytes(Path.of("output/koala-red-1.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-green.ppm")),
            Files.readAllBytes(Path.of("output/koala-green-1.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-blue.ppm")),
            Files.readAllBytes(Path.of("output/koala-blue-1.ppm")));
  }

  @Test
  public void testRedSplitImage() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/koala-red.ppm koala\n" +
            "rgb-split koala koala-red koala-green koala-blue\n" +
            "save output/koala-red-2.ppm koala-red\n" +
            "save output/koala-green-2.ppm koala-green\n" +
            "save output/koala-blue-2.ppm koala-blue");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-red.ppm")),
            Files.readAllBytes(Path.of("output/koala-red-2.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-red.ppm")),
            Files.readAllBytes(Path.of("output/koala-green-2.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-red.ppm")),
            Files.readAllBytes(Path.of("output/koala-blue-2.ppm")));
  }

  @Test
  public void testGreenSplitImage() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/koala-green.ppm koala\n" +
            "rgb-split koala koala-red koala-green koala-blue\n" +
            "save output/koala-red-3.ppm koala-red\n" +
            "save output/koala-green-3.ppm koala-green\n" +
            "save output/koala-blue-3.ppm koala-blue");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-green.ppm")),
            Files.readAllBytes(Path.of("output/koala-red-3.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-green.ppm")),
            Files.readAllBytes(Path.of("output/koala-green-3.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-green.ppm")),
            Files.readAllBytes(Path.of("output/koala-blue-3.ppm")));
  }

  @Test
  public void testBlueSplitImage() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/koala-blue.ppm koala\n" +
            "rgb-split koala koala-red koala-green koala-blue\n" +
            "save output/koala-red-4.ppm koala-red\n" +
            "save output/koala-green-4.ppm koala-green\n" +
            "save output/koala-blue-4.ppm koala-blue");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-blue.ppm")),
            Files.readAllBytes(Path.of("output/koala-red-4.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-blue.ppm")),
            Files.readAllBytes(Path.of("output/koala-green-4.ppm")));
    assertArrayEquals(Files.readAllBytes(Path.of("input/koala-blue.ppm")),
            Files.readAllBytes(Path.of("output/koala-blue-4.ppm")));
  }

  @Test
  public void testImageCombine() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\n" +
            "rgb-split koala koala-red koala-green koala-blue\n" +
            "rgb-combine koala-combine koala-red koala-green koala-blue\n" +
            "save output/koala-combine.ppm koala-combine");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertArrayEquals(Files.readAllBytes(Path.of("koala.ppm")),
            Files.readAllBytes(Path.of("output/koala-combine.ppm")));
  }

  @Test
  public void testImageCombineError() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/koala-red-size.ppm koala-red\n" +
            "load input/koala-blue.ppm koala-blue\n" +
            "load input/koala-green.ppm koala-green\n" +
            "rgb-combine koala-combine koala-red koala-green koala-blue");
    controller = new IMEControllerImpl(in, out);
    controller.execute();
    assertEquals("Error!: The greyscale images are of different sizes!", out.toString());
  }

  @Test
  public void testLoadAndSavepngImage() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "save output/ms.png ms");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/ms.png"));
    BufferedImage img2 = ImageIO.read(new File("output/ms.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageBlur() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "blur ms msb\n" +
            "save output/msb.png msb");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/msb.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msb.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageBlurTwice() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "blur ms msb\n" +
            "blur msb msbb\n" +
            "save output/msbb.png msbb");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/msbb.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msbb.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageSharpen() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "sharpen ms mss\n" +
            "save output/mss.png mss");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/mss.png"));
    BufferedImage img2 = ImageIO.read(new File("output/mss.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageSparpenTwice() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "sharpen ms mss\n" +
            "sharpen mss msss\n" +
            "save output/msss.png msss");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/msss.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msss.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageGreyScale() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "greyscale ms msg\n" +
            "save output/msg.png msg");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/msg.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msg.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageSepia() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "sepia ms mssepia\n" +
            "save output/mssepia.png mssepia");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/mssepia.png"));
    BufferedImage img2 = ImageIO.read(new File("output/mssepia.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  @Test
  public void testImageDither() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load input/ms.png ms\n" +
            "dither ms msd\n" +
            "save output/msd.png msd");
    controller = new IMEControllerImpl(in, out);
    controller.execute();

    BufferedImage img1 = ImageIO.read(new File("input/msd.png"));
    BufferedImage img2 = ImageIO.read(new File("output/msd.png"));

    assertEquals(0.0, CompareImages(img1, img2), 0.0);
  }

  public double CompareImages(BufferedImage img1, BufferedImage img2) {
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
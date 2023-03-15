package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.geom.IllegalPathStateException;
import java.io.FileInputStream;
import java.util.List;
import org.junit.Test;
import service.imageFileLoader.LoadImage;
import service.imageFileLoader.LoadPPM;

public class IMEModelImplTest {

  IMEModel image;

  public IMEModelImplTest() {
    try {
      LoadImage imageLoader = new LoadPPM(new FileInputStream("test.ppm"));
      image = imageLoader.load("test.ppm", "test");
    } catch (Exception e) {
      throw new IllegalPathStateException(e.getMessage());
    }
  }

  @Test
  public void getImageData() {
    assertEquals(768, image.getImageData().length);
    assertEquals(1024, image.getImageData()[0].length);
  }

  @Test
  public void getImageHeight() {
    assertEquals(768, image.getImageHeight());
  }

  @Test
  public void getImageWidth() {
    assertEquals(1024, image.getImageWidth());
  }

  @Test
  public void redGreyScaleImage() {
    IMEModel redScaleImage = image.greyScaleImage(Pixel::getRedComponent);
    for (int i = 0; i < redScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < redScaleImage.getImageWidth(); j++) {
        int expected = image.getImageData()[i][j].getRedComponent();
        assertEquals(expected, redScaleImage.getImageData()[i][j].getRedComponent());
        assertEquals(expected, redScaleImage.getImageData()[i][j].getBlueComponent());
        assertEquals(expected, redScaleImage.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void blueGreyScaleImage() {
    IMEModel blueScaleImage = image.greyScaleImage(Pixel::getBlueComponent);
    for (int i = 0; i < blueScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < blueScaleImage.getImageWidth(); j++) {
        int expected = image.getImageData()[i][j].getBlueComponent();
        assertEquals(expected, blueScaleImage.getImageData()[i][j].getRedComponent());
        assertEquals(expected, blueScaleImage.getImageData()[i][j].getBlueComponent());
        assertEquals(expected, blueScaleImage.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void greenGreyScaleImage() {
    IMEModel greenScaleImage = image.greyScaleImage(Pixel::getGreenComponent);
    for (int i = 0; i < greenScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < greenScaleImage.getImageWidth(); j++) {
        int expected = image.getImageData()[i][j].getGreenComponent();
        assertEquals(expected, greenScaleImage.getImageData()[i][j].getRedComponent());
        assertEquals(expected, greenScaleImage.getImageData()[i][j].getBlueComponent());
        assertEquals(expected, greenScaleImage.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void valueGreyScaleImage() {
    IMEModel valueScaleImage = image.greyScaleImage(Pixel::getValue);
    for (int i = 0; i < valueScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < valueScaleImage.getImageWidth(); j++) {
        int expected = image.getImageData()[i][j].getValue();
        assertEquals(expected, valueScaleImage.getImageData()[i][j].getRedComponent());
        assertEquals(expected, valueScaleImage.getImageData()[i][j].getBlueComponent());
        assertEquals(expected, valueScaleImage.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void intensityGreyScaleImage() {
    IMEModel intensityScaleImage = image.greyScaleImage(Pixel::getIntensity);
    for (int i = 0; i < intensityScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < intensityScaleImage.getImageWidth(); j++) {
        int expected = image.getImageData()[i][j].getIntensity();
        assertEquals(expected, intensityScaleImage.getImageData()[i][j].getRedComponent());
        assertEquals(expected, intensityScaleImage.getImageData()[i][j].getBlueComponent());
        assertEquals(expected, intensityScaleImage.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void lumaGreyScaleImage() {
    IMEModel lumaScaleImage = image.greyScaleImage(Pixel::getLuma);
    for (int i = 0; i < lumaScaleImage.getImageHeight(); i++) {
      for (int j = 0; j < lumaScaleImage.getImageWidth(); j++) {
        int expected = image.getImageData()[i][j].getLuma();
        assertEquals(expected, lumaScaleImage.getImageData()[i][j].getRedComponent());
        assertEquals(expected, lumaScaleImage.getImageData()[i][j].getBlueComponent());
        assertEquals(expected, lumaScaleImage.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void horizontalFlipImage() {
    IMEModel horizontal = image.horizontalFlipImage();
    for (int i = 0; i < horizontal.getImageHeight(); i++) {
      assertArrayEquals(reverse(image.getImageData()[i]), horizontal.getImageData()[i]);
    }
  }

  @Test
  public void verticalFlipImage() {
    IMEModel vertical = image.verticalFlipImage();
    for (int i = 0; i < vertical.getImageHeight() / 2; i++) {
      assertArrayEquals(image.getImageData()[image.getImageHeight() - 1 - i],
          vertical.getImageData()[i]);
    }
  }

  @Test
  public void alterBrightness() {
    int delta = 100;
    IMEModel brighten = image.alterBrightness(delta);
    for (int i = 0; i < brighten.getImageHeight(); i++) {
      for (int j = 0; j < brighten.getImageWidth(); j++) {
        assertEquals(Math.min(image.getImageData()[i][j].getRedComponent() + delta, 255),
            brighten.getImageData()[i][j].getRedComponent());
        assertEquals(Math.min(image.getImageData()[i][j].getBlueComponent() + delta, 255),
            brighten.getImageData()[i][j].getBlueComponent());
        assertEquals(Math.min(image.getImageData()[i][j].getGreenComponent() + delta, 255),
            brighten.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void darkenImage() {
    int delta = -100;
    IMEModel brighten = image.alterBrightness(delta);
    for (int i = 0; i < brighten.getImageHeight(); i++) {
      for (int j = 0; j < brighten.getImageWidth(); j++) {
        assertEquals(Math.max(image.getImageData()[i][j].getRedComponent() + delta, 0),
            brighten.getImageData()[i][j].getRedComponent());
        assertEquals(Math.max(image.getImageData()[i][j].getBlueComponent() + delta, 0),
            brighten.getImageData()[i][j].getBlueComponent());
        assertEquals(Math.max(image.getImageData()[i][j].getGreenComponent() + delta, 0),
            brighten.getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void rgbSplit() {
    List<IMEModel> images = image.rgbSplit();
    for (int i = 0; i < image.getImageHeight(); i++) {
      for (int j = 0; j < image.getImageWidth(); j++) {
        assertEquals(image.getImageData()[i][j].getRedComponent(),
            images.get(0).getImageData()[i][j].getRedComponent());
        assertEquals(image.getImageData()[i][j].getRedComponent(),
            images.get(0).getImageData()[i][j].getBlueComponent());
        assertEquals(image.getImageData()[i][j].getRedComponent(),
            images.get(0).getImageData()[i][j].getGreenComponent());
        assertEquals(image.getImageData()[i][j].getGreenComponent(),
            images.get(1).getImageData()[i][j].getRedComponent());
        assertEquals(image.getImageData()[i][j].getGreenComponent(),
            images.get(1).getImageData()[i][j].getBlueComponent());
        assertEquals(image.getImageData()[i][j].getGreenComponent(),
            images.get(1).getImageData()[i][j].getGreenComponent());
        assertEquals(image.getImageData()[i][j].getBlueComponent(),
            images.get(2).getImageData()[i][j].getRedComponent());
        assertEquals(image.getImageData()[i][j].getBlueComponent(),
            images.get(2).getImageData()[i][j].getBlueComponent());
        assertEquals(image.getImageData()[i][j].getBlueComponent(),
            images.get(2).getImageData()[i][j].getGreenComponent());
      }
    }
  }

  @Test
  public void combineRGBImage() {
    List<IMEModel> images = image.rgbSplit();
    IMEModel combinedRGBImage = images.get(0).combineRGBImage(images.get(1), images.get(2));
    for (int i = 0; i < combinedRGBImage.getImageHeight(); i++) {
      for (int j = 0; j < combinedRGBImage.getImageWidth(); j++) {
        assertEquals(combinedRGBImage.getImageData()[i][j].getRedComponent(),
            images.get(0).getImageData()[i][j].getRedComponent());
        assertEquals(combinedRGBImage.getImageData()[i][j].getGreenComponent(),
            images.get(1).getImageData()[i][j].getGreenComponent());
        assertEquals(combinedRGBImage.getImageData()[i][j].getBlueComponent(),
            images.get(2).getImageData()[i][j].getBlueComponent());
      }
    }
  }

  private Pixel[] reverse(Pixel[] a) {
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

}
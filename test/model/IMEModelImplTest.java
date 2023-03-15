package model;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.imageFileLoader.LoadImage;
import controller.imageFileLoader.LoadPPM;

import static org.junit.Assert.assertEquals;

public class IMEModelImplTest {

  LoadImage imageLoader;

  @Before
  public void setUp() throws FileNotFoundException{
    try {
      imageLoader = new LoadPPM(new FileInputStream("test.ppm"));
    } catch(Exception e) {
      throw new FileNotFoundException(e.getMessage());
    }
  }

  @Test
  public void getImageData() {
    try {
      IMEModel image = imageLoader.load("test.ppm", "test");
      assertEquals(768, image.getImageData().length);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void getImageHeight() {
  }

  @Test
  public void getImageWidth() {
  }

  @Test
  public void greyScaleImage() {
  }

  @Test
  public void horizontalFlipImage() {
  }

  @Test
  public void verticalFlipImage() {
  }

  @Test
  public void alterBrightness() {
  }

  @Test
  public void rgbSplit() {
  }

  @Test
  public void combineRGBImage() {
  }

  @Test
  public void reverse() {
  }
}
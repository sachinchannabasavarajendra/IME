import java.io.FileNotFoundException;

public class IMEController {

  public static IMEModel loadImageFile(String filename) throws FileNotFoundException {
    return IMEModelImpl.getBuilder().loadFile(filename).build();
  }

  public static void main(String []args) throws FileNotFoundException{
    String filename;

    if (args.length>0) {
        filename = args[0];
    }
    else {
        filename = "sample.ppm";
    }

    IMEModel loadedImage = loadImageFile(filename);

    IMEModel newBrightenedImage = loadedImage.verticalFlipImage();

    IMEModel newBrightImage = newBrightenedImage.alterBrightness(100);

    IMEModel redGreyScale = loadedImage.redGreyScaleImage();

    newBrightenedImage.saveImageFile("verticalFlipImage", "", "ppm");

    redGreyScale.saveImageFile("redGreyScaleImage", "", "ppm");

    newBrightImage.saveImageFile("verticalFlipImageBright", "", "ppm");

    loadedImage.saveImageFile("unchangedImage", "", "ppm");
  }
}

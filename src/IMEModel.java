import java.util.function.Predicate;

public interface IMEModel {

  // void loadImageFile(String imageName, String filePath, String fileType);
  Pixel[][] getImageData();

  void saveImageFile(String imageName, String filePath, String fileType);

  IMEModel redGreyScaleImage();

  IMEModel greenGreyScaleImage();

  IMEModel blueGreyScaleImage();

  IMEModel horizontalFlipImage();

  IMEModel verticalFlipImage();

  IMEModel alterBrightness(int delta);

  IMEModel combineRGBImage(
          IMEModel redScaleImage,
          IMEModel blueScaleImage,
          IMEModel greenScaleImage
  );
}

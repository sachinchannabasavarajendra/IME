package model;

import java.util.List;
import java.util.function.Function;

public interface IMEModel {

  Pixel[][] getImageData();

  int getImageHeight();

  int getImageWidth();

  IMEModel greyScaleImage(Function<Pixel, Integer> func);

  IMEModel horizontalFlipImage();

  IMEModel verticalFlipImage();

  IMEModel alterBrightness(int delta);

  List<IMEModel> rgbSplit();

  IMEModel combineRGBImage(
      IMEModel redScaleImage,
      IMEModel blueScaleImage,
      IMEModel greenScaleImage
  );
}

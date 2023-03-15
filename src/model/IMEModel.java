package model;

import java.util.List;

public interface IMEModel {

  Pixel[][] getImageData();

  int getImageHeight();

  int getImageWidth();

  IMEModel redGreyScaleImage();

  IMEModel greenGreyScaleImage();

  IMEModel blueGreyScaleImage();

  IMEModel valueGreyScaleImage();

  IMEModel lumaGreyScaleImage();

  IMEModel intensityGreyScaleImage();

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

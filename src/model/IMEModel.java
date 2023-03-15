package model;

public interface IMEModel {

  Pixel[][] getImageData();

  int getImageHeight();

  int getImageWidth();

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

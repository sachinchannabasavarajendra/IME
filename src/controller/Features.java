package controller;

public interface Features {
  void LoadImage(String imagePath, String imageName);
  void BlurImage(String src, String dest);
  void Brighten(String delta, String src, String dest);
  void Dither(String src, String dest);
  void GreyScale(String src, String dest, String component);
  void HorizontalFlip(String src, String dest);
  void VerticalFlip(String src, String dest);
  void Sepia(String src, String dest);
  void Sharpen(String src, String dest);
  void SaveImage(String imagePath, String src);
}

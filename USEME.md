# Supported commands

1. Load Image

   `load imagePath imageName`

    imagePath - Path of the image that needs to be loaded.

    Supported formats of Image: .ppm, .png, .bmp, .jpg, .jpeg

    imageName - Name using which the loaded image should be referred in the program.

    
2. Save Image
    
    `save imagePath imageName`

   imagePath - Path of the image where the image needs to be saved.

   Supported formats of Image: .ppm, .png, .bmp, .jpg, .jpeg

   imageName - Name of the image.



3. Brighten Image
    
    `brighten offset sourceImageName destinationImageName`
    
    The offset can be either negative or positive, if it is negative the image gets darkened and if it is positive the image brightens.


4. Horizontal Flip
    
    `horizontal-flip sourceImageName destinationImageName`


5. Vertical Flip

    `vertical-flip sourceImageName destinationImageName`
    

6. Greyscale Image
    
    `greyscale component sourceImageName destinationImageName`

    component can be either of the following: red-component, green-component, blue-component, luma-component, value-component, intensity-component


7. RUN Script file
    
    `run filepath`


8. RGB Split
    
    `rgb-split sourceImageName redGreyScaleImageName greenGreyScaleImageName blueGreyScaleImageName`

    The order should be red followed by green followed by blue.


9. RGB Combine
    
    `rgb-combine destinationImageName redGreyScaleSourceImageName greenGreyScaleSourceImageName blueGreyScaleSourceImageName`

   The order should be red followed by green followed by blue.


10. Greyscale using color transformation logic
    
    `greyscale sourceImageName destinationImageName`


11. Sepia

    `sepia sourceImageName destinationImageName`


12. Blur
    
    `blur sourceImageName destinationImageName`


13. Dither

    `dither sourceImageName destinationImageName`


14. Sharpen

    `sharpen sourceImageName destinationImageName`

    
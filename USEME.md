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

# GUI usage steps for executing commands :-

1. Load Image - Click on the 'Load Image' button and select the image to be loaded. If the image selected belongs to any of the formats not supported by the application an error popup is displayed.

2. Save Image - Click on the 'Save Image' button and choose the path where the image should be saved at. The image name should be entered along with format in which the user wants to save the image. If the format is not specified in the image name or if an unsupported format is given an error popup is displayed.

3. Brighten Image - Click on the 'Brighten Image'. It opens a dialog box to enter the delta value to brighten or darken the image. This dialog box expects an integer number to be entered. If any string is entered an error pop up is displayed.

4. Horizontal Flip - Click on the 'Horizontal Flip' button to flip the image horizontally.

5. Vertical Flip - Click on the 'Vertical Flip' button to flip the image vertically.

6. Greyscale Image - Click on the 'Greyscale' button which opens a popup consisting of radio buttons. Select the value component from it.

7. Blur - Click on the 'Blur' button to blur the image.

8. Dither - Click on the 'Dither' button to dither the image.

9. Sharpen - Click on the 'Sharpen' button to sharpen the image.

10. Greyscale Color Transform - Click on the 'Greyscale Color Transform' button to give the image a greyscale tone.

11. Sepia Color Transform - Click on the 'Sepia Color Transform' button to give the image a sepia tone.

12. RGB Combine - Click on the 'RGB Combine' button which opens a popup. Load three images into the popup and click 'OK'.

13. RGB Split - Click on the 'RGB Split' button. It opens a popup which the 3 images. Under each image there is a provision to specify the path and name of the image to be saved and click 'OK'.
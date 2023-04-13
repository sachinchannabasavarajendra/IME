# IME
This application of Image Manipulation and Enhancement is implemented based on the Model-View-Controller pattern. The model package has two parts. The first part consists of an 'IMEModel' interface which specifies the operations/manipulations to be performed on an image. The 'IMEModelImpl' class which implements the 'IMEModel' interface represents an image. The model also has 'Pixel' class which implements the 'IPixel' interface representing one pixel of the image and provides the functionality to compute the value, intensity and luma along with the values of the red, green and blue color components of each pixel.

The controller package has an 'IMEController' interface which gets invoked by the main() method and starts the program and gives control to the controller. The 'IMEControllerImpl' class implements this 'IMEController' interface and processes the commands sent to it. The controller has an object hashmap used to store all the model data objects against its corresponding image key. The controller processes the input commands based on the Command Design pattern. The commands part of the controller package has an 'IMEModelCommand' interface implemented by each command classes which connects the controller and the model by executing the input command on the given image and manipulates it.

This application consists of a service package which handles the loading and saving of an image file. This has been implemented in a future proof way in order to handle the loading and saving of the image files of different formats. It has the 'LoadImage' and 'SaveImage' interfaces which are implemented by the 'LoadPPM' and 'SavePPM' classes respectively and performs the operations of loading and saving the image files of the 'ppm' format which is the format supported by this application currently. These interfaces can be implemented by different formats, if needed, in the future.

The application also has different exception handling mechanisms and handles various types of errors that are encountered during the execution of the application.

Instructions to run the script file :-

Along with the console input commands, this user-interactive application also takes the text based script file and executes the supported commands in it. This application has a commands file called 'script.txt' which can be executed when the program runs using the console command - "run script.txt".

Other supported commands:
- load imagePath reindeer
- brighten 50 reindeer reindeer-brighter
- save imagePath reindeer-brighter
- brighten -50 reindeer reindeer-darken (darken)
- horizontal-flip reindeer reindeer-horizontal
- vertical-flip reindeer-horizontal reindeer-horizontal-vertical
- greyscale red-component reindeer reindeer-red-greyscale
- greyscale green-component reindeer reindeer-green-greyscale
- greyscale blue-component reindeer reindeer-blue-greyscale
- greyscale value-component reindeer reindeer-value-greyscale
- greyscale intensity-component reindeer reindeer-intensity-greyscale
- greyscale luma-component reindeer reindeer-luma-greyscale
- run filepath
- rgb-split reindeer reindeer-red-greyscale reindeer-green-greyscale reindeer-blue-greyscale
- rgb-combine reindeer-combine reindeer-red-greyscale reindeer-green-greyscale reindeer-blue-greyscale
- greyscale reindeer reindeer-greyscale (Uses color transformation logic)
- sepia reindeer reindeer-sepia
- blur reindeer reindeer-blur
- dither reindeer reindeer-dither
- sharpen reindeer reindeer-sharpen

Use 'q' or 'quit' to stop running the application.

## Citation of the image used Filename: reindeer.ppm/png/bpm/jpg/jpeg

Creator: Sachin Channabasavarajendra.

Location: Woodstock New Hampshire.

From the creator's personal collection, permitted to use the image.

## Parts of the Program that are complete

1. Main Function (Entry point of the application).
2. Controller Interface and Implementation. 
3. Command Design pattern to invoke model methods.
4. Controller contains the object hashmap that holds all the images that are loaded and manipulated by the application.
5. Model interface and implementation with manipulation functions from assignment 4.
6. Each model represents an image and each image is represented as a 2D array of IPixel objects.
7. Additional macro method added to the model interface that accepts a macro object and performs the manipulation on the image.
8. New manipulations are created as macro classes.
9. Load and save image file that supports multiple formats (Written in service layer).
10. The application supports command inputs from the user through keyboard and also through program arguments.
11. The application also supports running commands from a script file using the run command.
12. The application has a GUI which the user can use to interactively load, save and manipulate images.
13. The application GUI displays the histogram of the image that is loaded.
14. The application GUI also contains a set of buttons which performs manipulation on the image.

## Design Enhancements 

The previous implementatation of IMEModel had all the manipulations as functions within the model and as well in the interface. As part of this
iteration we have extended the model by adding a new method that accepts a macro object which represents a manipulation 
and performs those operation on the image. Therefore in the future it will be easier to add more manipulations to an image without
changing the model. Each manipulation is represented as a Macro class that implements the IMacro interface which accepts the image that needs
to be manipulated and runs the operation on this image and returns a new manipulated image. The reason why we added a new method to the existing interface 
is because each model in our design represents one image and once we manipulate an image we are returning a new model implementation that represents the new manipulated image.
If we had to write a new interface called MIMEModel that extends IMEModel, the return type of all those existing model functions should have
been updated. And for this very reason we have added only one new function called executeMacro() that accepts an object of IMacro interface and executes the macro function 
on the image that is represented by the model. We could have moved all the existing manipulations also as macro functions but since the previous implementation is already being used by the controller and
commands we didn't modify the existing flow. But in the future if there are more manipulations to be added all we need to do is add a new command in the command packge and a new macro in the macro package.

As part of the next iteration, we have added a View and a new Controller called GrimeController which is responsible for displaying the view. 
The GrimeController has an instance of IMEController (Composition) to call the invokeCommand method and get the loaded image from the object hashmap. The GrimeController by itself does not have
access to the knowncommands or the object hashmap, it has to use the IMEController instance to access those maps. The View contains three major areas, the top left shows the histogram of the image and the bottom left shows the buttons 
that performs manipulations on the image, the top right contians the load and save buttons and the bottom right shows the image that is currently loaded.





# IME
This application of Image Manipulation and Enhancement is implemented based on the Model-View-Controller pattern. The model package has two parts. The first part consists of an 'IMEModel' interface which specifies the operations/manipulations to be performed on an image. The 'IMEModelImpl' class which implements the 'IMEModel' interface represents an image. The model also has 'Pixel' class which implements the 'IPixel' interface representing one pixel of the image and provides the functionality to compute the value, intensity and luma along with the values of the red, green and blue color components of each pixel.

The controller package has an 'IMEController' interface which gets invoked by the main() method and starts the program and gives control to the controller. The 'IMEControllerImpl' class implements this 'IMEController' interface and processes the commands sent to it. The controller has an object hashmap used to store all the model data objects against its corresponding image key. The controller processes the input commands based on the Command Design pattern. The commands part of the controller package has an 'IMEModelCommand' interface implemented by each command classes which connects the controller and the model by executing the input command on the given image and manipulates it.

This application consists of a service package which handles the loading and saving of an image file. This has been implemented in a future proof way in order to handle the loading and saving of the image files of different formats. It has the 'LoadImage' and 'SaveImage' interfaces which are implemented by the 'LoadPPM' and 'SavePPM' classes respectively and performs the operations of loading and saving the image files of the 'ppm' format which is the format supported by this application currently. These interfaces can be implemented by different formats, if needed, in the future.

The application also has different exception handling mechanisms and handles various types of errors that are encountered during the execution of the application.

Instructions to run the script file :-

Along with the console input commands, this user-interactive application also takes the text based script file and executes the supported commands in it. This application has a commands file called 'script.txt' which can be executed when the program runs using the console command - "run script.txt".

Use 'q' or 'quit' to stop running the application.

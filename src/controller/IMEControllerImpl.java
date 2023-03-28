package controller;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Dither;
import controller.commands.Greyscale;
import controller.commands.HorizontalFlip;
import controller.commands.IMEModelCommand;
import controller.commands.Load;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.Save;
import controller.commands.SepiaColorTransform;
import controller.commands.Sharpen;
import controller.commands.VerticalFlip;
import java.awt.geom.IllegalPathStateException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.IMEModel;

/**
 * This is an implementation of the controller interface which supports both text-based scripting
 * and loading of and running the script commands in the specified file.
 */
public class IMEControllerImpl implements IMEController {

  private final Map<String, IMEModel> objectMap;
  private final Readable in;
  private final Appendable out;

  /**
   * This is a constructor which is used to instantiate the above class.
   *
   * @param in  commands to be processed which are passed as input stream
   * @param out output stream used to display messages to the user
   */
  public IMEControllerImpl(Readable in, Appendable out) {
    this.objectMap = new HashMap<>();
    this.in = in;
    this.out = out;
  }

  /**
   * This method processes and executes the given command.
   */
  public void execute() throws IOException {
    Scanner sc = new Scanner(this.in);
    IMEModelCommand imeModelCommand;
    boolean isScriptRunning = false;

    Map<String, Function<String[], IMEModelCommand>> knownCommands = new HashMap<>();
    knownCommands.put("load", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Load expects 2 parameters");
      }
      try {
        return new Load(new FileInputStream(inputCommand[1]), inputCommand[1], inputCommand[2]);
      } catch (FileNotFoundException e) {
        throw new IllegalPathStateException(e.getMessage());
      }
    });
    knownCommands.put("save", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Save expects 2 parameters");
      }
      return new Save(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("brighten", inputCommand -> {
      if (inputCommand.length != 4) {
        throw new IllegalArgumentException("Brighten expects 3 parameters");
      }
      return new Brighten(Integer.parseInt(inputCommand[1]),
          inputCommand[2], inputCommand[3]);
    });
    knownCommands.put("vertical-flip", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Vertical Flip expects 2 parameters");
      }
      return new VerticalFlip(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("horizontal-flip", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Horizontal Flip expects 2 parameters");
      }
      return new HorizontalFlip(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("greyscale", inputCommand -> {
      if(inputCommand.length == 3) {
        return new Greyscale("luma-component", inputCommand[2], inputCommand[3]);
      }
      if (inputCommand.length != 4) {
        throw new IllegalArgumentException("Greyscale expects 3 parameters");
      }
      return new Greyscale(inputCommand[1], inputCommand[2], inputCommand[3]);
    });
    knownCommands.put("rgb-split", inputCommand -> {
      if (inputCommand.length != 5) {
        throw new IllegalArgumentException("RGB Split expects 4 parameters");
      }
      return new RGBSplit(inputCommand[1], inputCommand[2], inputCommand[3], inputCommand[4]);
    });
    knownCommands.put("rgb-combine", inputCommand -> {
      if (inputCommand.length != 5) {
        throw new IllegalArgumentException("RGB combine expects 4 parameters");
      }
      return new RGBCombine(inputCommand[1], inputCommand[2], inputCommand[3], inputCommand[4]);
    });
    knownCommands.put("blur", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Blur expects 2 parameters");
      }
      return new Blur(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("sharpen", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Sharpen expects 2 parameters");
      }
      return new Sharpen(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("sepia", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Sepia color transformation expects 2 parameters");
      }
      return new SepiaColorTransform(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("dither", inputCommand -> {
      if (inputCommand.length != 3) {
        throw new IllegalArgumentException("Dither transformation expects 2 parameters");
      }
      return new Dither(inputCommand[1], inputCommand[2]);
    });

    while (sc.hasNextLine() || isScriptRunning) {
      if (!sc.hasNextLine() && isScriptRunning) {
        isScriptRunning = false;
        sc = new Scanner(this.in);
        this.out.append("The script file has completed execution!");
        continue;
      }
      String in = sc.nextLine();
      while (in.startsWith("#") || in.trim().equals("")) {
        in = sc.nextLine();
      }
      try {
        String[] inputCommand = in.trim().split(" ");
        if (inputCommand[0].equalsIgnoreCase("q")
            || inputCommand[0].equalsIgnoreCase("quit")) {
          return;
        }

        if (inputCommand[0].equalsIgnoreCase("run")) {
          sc = new Scanner(new FileInputStream(inputCommand[1]));
          isScriptRunning = true;
          continue;
        }

        Function<String[], IMEModelCommand> cmd =
            knownCommands.getOrDefault(inputCommand[0], null);
        if (cmd == null) {
          throw new IllegalArgumentException("Bad input command :- " + inputCommand[0]);
        } else {
          imeModelCommand = cmd.apply(inputCommand);
          imeModelCommand.execute(this.objectMap);
        }
      } catch (Exception e) {
        this.out.append("Error!: ").append(e.getMessage());
      }
    }
  }
}
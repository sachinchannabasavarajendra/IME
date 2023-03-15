package controller;

import controller.commands.Brighten;
import controller.commands.Greyscale;
import controller.commands.HorizontalFlip;
import controller.commands.IMEModelCommand;
import controller.commands.Load;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.Save;
import controller.commands.VerticalFlip;

import java.awt.geom.IllegalPathStateException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import model.IMEModel;

public class IMEControllerImpl implements IMEController {

  private final Map<String, IMEModel> objectMap;
  private final InputStream in;
  private OutputStream out;

  public IMEControllerImpl(InputStream in, OutputStream out) {
    this.objectMap = new HashMap<>();
    this.in = in;
    this.out = out;
  }

  public void go() {
    Scanner sc = new Scanner(this.in);
    IMEModelCommand imeModelCommand = null;

    Map<String, Function<String[], IMEModelCommand>> knownCommands = new HashMap<>();
    knownCommands.put("load", inputCommand -> {
      if(inputCommand.length !=3) {
        throw new IllegalArgumentException("Load expects 2 parameters");
      }
      try {
        return new Load(new FileInputStream(inputCommand[1]), inputCommand[1], inputCommand[2]);
      } catch (FileNotFoundException e) {
        throw new IllegalPathStateException(e.getMessage());
      }
    });
    knownCommands.put("save", inputCommand -> {
      if(inputCommand.length !=3) {
        throw new IllegalArgumentException("Save expects 2 parameters");
      }
      return new Save(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("brighten",  inputCommand -> {
      if(inputCommand.length !=4) {
        throw new IllegalArgumentException("Brighten expects 3 parameters");
      }
      return new Brighten(Integer.parseInt(inputCommand[1]),
              inputCommand[2], inputCommand[3]);
    });
    knownCommands.put("vertical-flip", inputCommand -> {
      if(inputCommand.length !=3) {
        throw new IllegalArgumentException("Vertical Flip expects 2 parameters");
      }
      return new VerticalFlip(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("horizontal-flip", inputCommand -> {
      if(inputCommand.length !=3) {
        throw new IllegalArgumentException("Horizontal Flip expects 2 parameters");
      }
      return new HorizontalFlip(inputCommand[1], inputCommand[2]);
    });
    knownCommands.put("greyscale", inputCommand -> {
      if(inputCommand.length !=4) {
        throw new IllegalArgumentException("Greyscale expects 3 parameters");
      }
      return new Greyscale(inputCommand[1], inputCommand[2], inputCommand[3]);
    });
    knownCommands.put("rgb-split", inputCommand -> {
      if(inputCommand.length !=5) {
        throw new IllegalArgumentException("RGB Split expects 4 parameters");
      }
      return new RGBSplit(inputCommand[1], inputCommand[2], inputCommand[3], inputCommand[4]);
    });
    knownCommands.put("rgb-combine", inputCommand -> {
      if(inputCommand.length !=5) {
        throw new IllegalArgumentException("RGB combine expects 4 parameters");
      }
      return new RGBCombine(inputCommand[1], inputCommand[2], inputCommand[3], inputCommand[4]);
    });

    while(sc.hasNextLine()) {
      String in = sc.nextLine();
      while(in.startsWith("#") || in.trim().equals("")) {
        in = sc.nextLine();
      }
      try {
        String[] inputCommand = in.trim().split(" ");
        if (inputCommand[0].equalsIgnoreCase("q") ||
                inputCommand[0].equalsIgnoreCase("quit")) {
          return;
        }

        if (inputCommand[0].equalsIgnoreCase("run")) {
          sc = new Scanner(new FileInputStream(inputCommand[1]));
          continue;
        }

        Function<String[], IMEModelCommand> cmd = knownCommands.getOrDefault(inputCommand[0], null);
        if (cmd == null) {
          throw new IllegalArgumentException("Bad input command :- " + inputCommand[0]);
        } else {
          imeModelCommand = cmd.apply(inputCommand);
          imeModelCommand.execute(this.objectMap);
        }
      } catch (Exception e) {
        System.out.println("Error!: " +e.getMessage());
      }
    }
  }
}
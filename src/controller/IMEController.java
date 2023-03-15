package controller;

import controller.commands.Brighten;
import controller.commands.Darken;
import controller.commands.Greyscale;
import controller.commands.HorizontalFlip;
import controller.commands.IMEModelCommand;
import controller.commands.Load;
import controller.commands.RGBCombine;
import controller.commands.Save;
import controller.commands.VerticalFlip;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import model.IMEModel;

public class IMEController {

  private final Map<String, IMEModel> objectMap;

  public IMEController() {
    this.objectMap = new HashMap<>();
  }

  public void execute(String[] args) {
    Scanner sc = new Scanner(System.in);
    IMEModelCommand imeModelCommand = null;
    while (sc.hasNext()) {
      String in = sc.next();
      try {
        switch (in) {
          case "q":
          case "quit":
            return;

          case "load":
            imeModelCommand = new Load(sc.next(), sc.next());
            break;

          case "save":
            imeModelCommand = new Save(sc.next(), sc.next());
            break;

          case "brighten":
            imeModelCommand = new Brighten(sc.nextInt(), sc.next(), sc.next());
            break;

          case "darken":
            imeModelCommand = new Darken(sc.nextInt(), sc.next(), sc.next());
            break;

          case "vertical-flip":
            imeModelCommand = new VerticalFlip(sc.next(), sc.next());
            break;

          case "horizontal-flip":
            imeModelCommand = new HorizontalFlip(sc.next(), sc.next());
            break;

          case "greyscale":
            imeModelCommand = new Greyscale(sc.next(), sc.next(), sc.next());
            break;

          case "rgb-split":
            //TODO : Add this part of adding method to model
            break;

          case "rgb-combine":
            imeModelCommand = new RGBCombine(sc.next(), sc.next(), sc.next(), sc.next());
            break;

          default:
            System.out.printf("Unknown command %s%n", in);
            break;
        }
        if (imeModelCommand != null) {
          imeModelCommand.execute(objectMap);
        }
      } catch (Exception e) {
        throw new InputMismatchException("Bad input command :- " + in);
      }
    }
  }
}
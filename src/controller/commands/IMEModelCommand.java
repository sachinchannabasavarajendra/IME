package controller.commands;

import java.util.Map;
import model.IMEModel;

public interface IMEModelCommand {

  void execute(Map<String, IMEModel> objectMap);
}

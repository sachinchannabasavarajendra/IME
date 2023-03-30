package model.macro;

import model.IMEModel;

/**
 * Macro interface that allows manipulations on the image passed.
 */
public interface IMacro {

  /**
   * Executes the macro image transformation on the image passed
   * @param model the image that needs to be manipulated
   * @return the manipulated image
   */
  IMEModel execute(IMEModel model);
}

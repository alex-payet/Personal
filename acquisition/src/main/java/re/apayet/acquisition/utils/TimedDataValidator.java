package re.apayet.acquisition.utils;

import re.apayet.acquisition.model.data.ActionKey;
import re.apayet.acquisition.model.base.abstraite.NativeData;
import re.apayet.acquisition.model.base.abstraite.ImageData;

public class TimedDataValidator {

  public static Boolean canDeleteData(NativeData pressed, NativeData released, NativeData tested) {
    return isSameClass(pressed, tested)
      && ( ( isActionButton(tested) && (tested instanceof ImageData))
        || ( isSameUUID(pressed, tested) && ( isPressedButton(tested) || isActionButton(tested) ))
      )
      && isBetween(pressed, released, tested)
    ;
  }

  public static Boolean isNextData(NativeData pressed, NativeData tested) {
    return isValidButton(pressed, tested) 
      && ( isNextKeyRelease(pressed, tested) 
        || isNextMouseRelease(pressed, tested) 
        || isNextProcessAction(pressed, tested)
        || isNextScreenAction(pressed, tested)
      ); 
  }

  public static Boolean isNextProcessAction(NativeData pressed, NativeData tested) {
    return isActionButton(pressed) && isActionButton(tested) && isSameUUID(pressed, tested);
  }
  
  public static Boolean isNextScreenAction(NativeData pressed, NativeData tested) {
    return isActionButton(pressed) && isActionButton(tested);
  }

  public static Boolean isNextKeyRelease(NativeData pressed, NativeData tested) {
    return isReleaseButton(tested) && isKeyEvent(pressed) && isSameUUID(pressed, tested);
  }

  public static Boolean isNextMouseRelease(NativeData pressed, NativeData tested) {
    return isReleaseButton(tested) && isMouseEvent(tested);
  }
  
  public static Boolean isBetween(NativeData pressed, NativeData realeased, NativeData tested) {
    return pressed.getTime() <= tested.getTime() && realeased.getTime() >= tested.getTime();
  }
  
  public static Boolean isActionButton(NativeData key) {
    return ActionKey.KEY_ACTION.equals(key.getAction()) || ActionKey.MOUSE_ACTION.equals(key.getAction());
  }

  public static Boolean isKeyEvent(NativeData key) {
    return ActionKey.KEY_RELEASE.equals(key.getAction()) || ActionKey.KEY_PRESSED.equals(key.getAction()); 
  }
  
  public static Boolean isMouseEvent(NativeData mouse) {
    return ActionKey.MOUSE_RELEASE.equals(mouse.getAction()) || ActionKey.MOUSE_PRESSED.equals(mouse.getAction()); 
  }
  
  public static Boolean isPressedButton(NativeData key) {
    return ActionKey.KEY_PRESSED.equals(key.getAction()) || ActionKey.MOUSE_PRESSED.equals(key.getAction());
  }
  
  public static Boolean isReleaseButton(NativeData key) {
    return ActionKey.KEY_RELEASE.equals(key.getAction()) || ActionKey.MOUSE_RELEASE.equals(key.getAction());
  }

  public static Boolean isValidButton(NativeData pressed, NativeData release) {
    return isSameClass(pressed, release) && isValidInterval(pressed, release);
  }
  
  public static Boolean isSameUUID(NativeData pressed, NativeData release) {
    return pressed.getId().equals(release.getId());
  }
  
  public static Boolean isSameClass(NativeData pressed, NativeData release) {
    return pressed.getClass().equals(release.getClass());
  }
  
  public static Boolean isValidInterval(NativeData pressed, NativeData release) {
    return release.getTime() >= pressed.getTime() ;
  }
}

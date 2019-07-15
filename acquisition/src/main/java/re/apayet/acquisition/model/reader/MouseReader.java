package re.apayet.acquisition.model.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.data.ActionKey;
import re.apayet.acquisition.model.data.MouseData;

public class MouseReader extends Reader<MouseData> implements NativeMouseInputListener {
  
  private static Logger LOG = LogManager.getLogger(MouseReader.class.getName());

  @Override
  public void nativeMousePressed(NativeMouseEvent input) {
    LOG.trace("nativeMousePressed");
    addEvent(input, ActionKey.MOUSE_PRESSED);
    
  }

  @Override
  public void nativeMouseReleased(NativeMouseEvent input) {
    LOG.trace("nativeMouseReleased");
    addEvent(input, ActionKey.MOUSE_RELEASE);
  }

  // Do nothing
  @Override public void nativeMouseDragged(NativeMouseEvent input) { LOG.trace("nativeMouseDragged - nothing"); }
  @Override public void nativeMouseMoved(NativeMouseEvent input)   { LOG.trace("nativeMouseMoved - nothing");   }
  @Override public void nativeMouseClicked(NativeMouseEvent input) { LOG.trace("nativeMouseClicked - nothing"); }

  public void addEvent(NativeMouseEvent input, ActionKey action) {
    if (isWork()) {
      Long when = System.currentTimeMillis();
      addData(new MouseData(input.getX(), input.getY(), when, action));
      LOG.debug(action + " : Mouse added [ x : " + input.getX() + " ] - [ y : " + input.getY() + " ] - [ When : " + when + " ]");
    }
  }
}

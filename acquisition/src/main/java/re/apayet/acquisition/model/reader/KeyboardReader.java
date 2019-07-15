package re.apayet.acquisition.model.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.data.ActionKey;
import re.apayet.acquisition.model.data.KeyboardData;

public class KeyboardReader extends Reader<KeyboardData> implements NativeKeyListener {

  public static final Logger LOG = LogManager.getLogger(KeyboardReader.class.getName());

  @Override
  public void nativeKeyPressed(NativeKeyEvent input) {
    LOG.trace("nativeKeyPressed [ " + input.paramString() + " ]");
    addEvent(input, ActionKey.KEY_PRESSED);
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent input) {
    LOG.trace("nativeKeyReleased [ " + input.paramString() + " ]");
    addEvent(input, ActionKey.KEY_RELEASE);
  }

  @Override public void nativeKeyTyped(NativeKeyEvent input) {
    LOG.trace("nativeKeyTyped - do nothing [ " + input.paramString() + " ]");
  }

  public void addEvent(NativeKeyEvent input, ActionKey action) {
    if (isWork()) {
      Long when = System.currentTimeMillis();
      addData(new KeyboardData(input.getKeyCode(),when, action));
      LOG.debug(action + " : Key Added [ " + NativeKeyEvent.getKeyText(input.getKeyCode()) + " ] - [ When : " + when + " ]");
    }
  }
}

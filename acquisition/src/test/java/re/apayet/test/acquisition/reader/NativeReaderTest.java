package re.apayet.test.acquisition.reader;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.junit.Test;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;
import re.apayet.acquisition.model.base.configuration.LocalConfiguration;
import re.apayet.acquisition.model.data.KeyboardData;
import re.apayet.acquisition.model.data.MouseData;
import re.apayet.acquisition.model.data.ProcessData;
import re.apayet.acquisition.model.base.abstraite.ImageData;
import re.apayet.test.acquisition.Launcher.AcquisitionTestHelper;

import java.awt.event.KeyEvent;
import static org.junit.Assert.*;

public class NativeReaderTest extends AcquisitionTestHelper {

  @Test
  public void keyboardReaderTest() {
    ConfigurationValues val = ConfigurationValues.KEYBOARD;
    LocalConfiguration cfg = configNativeReaderAndLaunch(val);
    pushKey(KeyEvent.VK_A, KeyEvent.VK_L, KeyEvent.VK_E, KeyEvent.VK_X);
    Boolean a= false, l= false, e = false, x = false;
    
    for (Object key : validate(cfg, val, 8)) {
      if ( key instanceof KeyboardData) {
        if ( "a".equals(NativeKeyEvent.getKeyText(((KeyboardData) key).getValue()).toLowerCase())) {
          a = true;
        }
        if ( "l".equals(NativeKeyEvent.getKeyText(((KeyboardData) key).getValue()).toLowerCase())) {
          l = true;
        }
        if ( "e".equals(NativeKeyEvent.getKeyText(((KeyboardData) key).getValue()).toLowerCase())) {
          e = true;
        }
        if ( "x".equals(NativeKeyEvent.getKeyText(((KeyboardData) key).getValue()).toLowerCase())) {
          x = true;
        }
      } else {
        fail("L'instance data n'est pas correct : " + key.getClass().getSimpleName());
      }
    }
    assertAll(a, l, e, x);
  }

  @Test
  public void mouseReaderTest() {
    ConfigurationValues val = ConfigurationValues.MOUSE;
    LocalConfiguration cfg = configNativeReaderAndLaunch(val);
    int nbCount = 4 ;
    moveMouse(nbCount);
    Boolean clickZero= false, clickOne= false, clickTwo = false, clickThree = false;
    
    for (Object key : validate(cfg, val, nbCount)) {
      if ( key instanceof MouseData) {
        if ( ( (MouseData) key).getX().intValue() % 10 == 0 ) {
          if ( (( (MouseData) key).getX().intValue() / 10 ) == 1 ) {
            clickOne = true;
          }
          if ( (( (MouseData) key).getX().intValue() / 10 ) == 2 ) {
            clickTwo = true;
          }
          if ( (( (MouseData) key).getX().intValue() / 10 ) == 3 ) {
            clickThree = true;
          }
          if ( (( (MouseData) key).getX().intValue() / 10 ) == 0 ) {
            clickZero = true;
          }
        }
      } else {
        fail("L'instance data n'est pas correct : " + key.getClass().getSimpleName());
      }
    }
    assertAll(clickOne, clickTwo, clickThree, clickZero);
  }

  @Test
  public void processReaderTest() {
    ConfigurationValues val = ConfigurationValues.PROCESS;
    LocalConfiguration cfg = configNativeReaderAndLaunch(val);
    pushKey(KeyEvent.VK_A, KeyEvent.VK_L, KeyEvent.VK_E, KeyEvent.VK_X);
    moveMouse(4);
    for (Object key : validate(cfg, val, 16)) {
      if ( key instanceof ProcessData) {
        assertTrue(!(( ProcessData ) key).getProcess().isEmpty());
      } else {
        fail("L'instance data n'est pas correcte : " + key.getClass().getSimpleName());
      }
    }
  }
  
  @Test
  public void screenReaderTest() {
    ConfigurationValues val = ConfigurationValues.SCREEN;
    LocalConfiguration cfg = configNativeReaderAndLaunch(val);
    pushKey(KeyEvent.VK_A, KeyEvent.VK_L, KeyEvent.VK_E, KeyEvent.VK_X);
    moveMouse(4);
    for (Object key : validate(cfg, val, 8)) {
      if ( ! (key instanceof ImageData)) {
        fail("L'instance data n'est pas correcte : " + key.getClass().getSimpleName());
      }
    }
  }
}

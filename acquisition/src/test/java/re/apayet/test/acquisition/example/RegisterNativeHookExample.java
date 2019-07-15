package re.apayet.test.acquisition.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import re.apayet.acquisition.model.reader.KeyboardReader;

import static org.junit.Assert.fail;

@SuppressWarnings("deprecation")
public class RegisterNativeHookExample {

  public void test() throws InterruptedException {
    try {
      GlobalScreen.registerNativeHook();
    } catch (NativeHookException ex) {
      fail("There was a problem registering the native hook.");
      System.err.println("There was a problem registering the native hook.");
      System.err.println(ex.getMessage());

      System.exit(1);
    }
    // Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);
    // Construct the example object.
    KeyboardReader example = new KeyboardReader();
    // Add the appropriate listeners.
    GlobalScreen.addNativeKeyListener(example);
  }
}

package re.apayet.test.acquisition.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalMouseListenerExample implements NativeMouseInputListener {
  public void nativeMouseClicked(NativeMouseEvent e) {
    System.out.println("Mouse Clicked: " + e.getClickCount());
  }

  public void nativeMousePressed(NativeMouseEvent e) {
    System.out.println("Mouse Pressed: " + e.getButton());
    printScreen();
  }

  public void nativeMouseReleased(NativeMouseEvent e) {
    System.out.println("Mouse Released: " + e.getButton());
  }

  public void nativeMouseMoved(NativeMouseEvent e) {
    System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
  }

  public void nativeMouseDragged(NativeMouseEvent e) {
    System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
  }

  public static void printScreen() {
    BufferedImage image;
    try {
      image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
      ImageIO.write(image, "png", new File(System.currentTimeMillis() + "-screenshot.png"));
    } catch (HeadlessException | AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      GlobalScreen.registerNativeHook();
    } catch (NativeHookException ex) {
      System.err.println("There was a problem registering the native hook.");
      System.err.println(ex.getMessage());

      System.exit(1);
    }
    Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);
    // Construct the example object.
    GlobalMouseListenerExample example = new GlobalMouseListenerExample();

    // Add the appropriate listeners.
    GlobalScreen.addNativeMouseListener(example);
    GlobalScreen.addNativeMouseMotionListener(example);
  }
}

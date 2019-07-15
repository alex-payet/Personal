package re.apayet.acquisition.model.reader;

import com.github.sarxos.webcam.Webcam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.data.ActionKey;
import re.apayet.acquisition.model.base.abstraite.ImageData;
import re.apayet.acquisition.model.data.WebCamData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CamReader extends Reader<ImageData> implements NativeKeyListener, NativeMouseListener {

  private static Logger LOG = LogManager.getLogger(CamReader.class.getName());

  @Override public void nativeKeyPressed(NativeKeyEvent input) {
    addEvent(input, ActionKey.KEY_ACTION);
    LOG.trace("nativeKeyPressed");
  }

  @Override
  public void nativeMouseClicked(NativeMouseEvent input) {
    LOG.trace("nativeMouseClicked");
    addEvent(input, ActionKey.MOUSE_ACTION);
  }

  @Override public void nativeKeyTyped(NativeKeyEvent input)        { LOG.trace("Not used : nativeKeyTyped");}
  @Override public void nativeKeyReleased(NativeKeyEvent input)     { LOG.trace("Not used : nativeKeyReleased");  }
  @Override public void nativeMousePressed(NativeMouseEvent input)  { LOG.trace("Not used : nativeMousePressed"); }
  @Override public void nativeMouseReleased(NativeMouseEvent input) { LOG.trace("Not used : nativeMouseReleased");}

  public void addEvent(NativeKeyEvent input, ActionKey action) {
    if (isWork()) {
      Long when = System.currentTimeMillis();
      addData(new WebCamData(createCamCapture(), when, action));
      LOG.debug(action + " : Cam Adding [ " + input.getKeyChar() + " ] - [ When : " + when + " ]");
    }
  }

  public void addEvent(NativeMouseEvent input, ActionKey action) {
    if (isWork()) {
      Long when = System.currentTimeMillis();
      addData(new WebCamData(createCamCapture(), when, action));
      LOG.debug(action + " : Cam Adding [ x : " + input.getX() + " ] - [ y : " + input.getY() + " ] - [ When : " + when + " ]");
    }
  }

  private byte[] createCamCapture() {
    byte[] value = null;
    BufferedImage image;
    try {
      Webcam webcam = Webcam.getDefault();
      webcam.open();
      image = webcam.getImage();
      value = super.createImage(image);
    } catch (HeadlessException e) {
      e.printStackTrace();
    }
    return value;
  }
}

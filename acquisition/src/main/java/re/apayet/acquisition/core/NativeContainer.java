package re.apayet.acquisition.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseListener;
import re.apayet.acquisition.model.base.abstraite.Container;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.base.abstraite.NativeData;

public class NativeContainer<T extends NativeData> extends Container<T> {

  private static final Logger LOG = LogManager.getLogger(NativeContainer.class.getName()) ;

  @Override
  public void run() {
    launch();
  }

  protected void instantiateReader() {
    LOG.info("Instantiation of readers [ " + readers.size() + " ]");
    for ( Reader reader : readers) {
      LOG.debug("Instantiation of " + reader.getClass().getSimpleName());
      if ( reader instanceof NativeKeyListener) {
        LOG.trace("Adding reader " + reader.getClass().getSimpleName() + " like KEYBOARD Listener.");
        GlobalScreen.addNativeKeyListener((NativeKeyListener) reader);
      }
      if ( reader instanceof NativeMouseListener) {
        LOG.trace("Adding reader " + reader.getClass().getSimpleName() + " like MOUSE Listener.");
        GlobalScreen.addNativeMouseListener((NativeMouseListener) reader);
      }
    }
    super.instantiateReader();
  }

  @Override
  protected void cleanData() {
    for (T data : datas) {
      data.createInternalValue();
      LOG.trace("[ " + data.getTime() + " ] " + data.getId());
    }
  }

}

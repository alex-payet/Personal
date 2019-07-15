package re.apayet.test.acquisition.Launcher;

import static org.junit.Assert.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.jnativehook.GlobalScreen;
import re.apayet.acquisition.LauncherContainerService;
import re.apayet.acquisition.core.NativeContainer;
import re.apayet.acquisition.model.reader.KeyboardReader;
import re.apayet.acquisition.model.reader.MouseReader;
import re.apayet.acquisition.model.reader.ProcessReader;
import re.apayet.acquisition.model.reader.ScreenReader;
import re.apayet.acquisition.model.writer.MemoryWriter;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.abstraite.Container;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;
import re.apayet.acquisition.model.base.configuration.LocalConfiguration;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AcquisitionTestHelper {

  protected LauncherContainerService service = null;

  protected void launch(Configuration cfg) {
    setLvl(cfg);
    service = new LauncherContainerService(cfg);
    new Thread(service).start();
  }

  protected void validateNativeService() {
    assertTrue(service != null );
    assertTrue(service.getContainers() != null && service.getContainers().size() == 1);
  }
  
  protected LocalConfiguration configEventReaderAndLaunch(ConfigurationValues val) {
    LocalConfiguration cfg = config(val);
    cfg.enable(ConfigurationValues.EVENT_MEMORY_WRITER);
    launch(cfg);
    return cfg;
  }

  protected LocalConfiguration configNativeReaderAndLaunch(ConfigurationValues val) {
    LocalConfiguration cfg = config(val);
    cfg.enable(ConfigurationValues.MEMORY_WRITER);
    launch(cfg);
    return cfg;
  }
  
  private LocalConfiguration config(ConfigurationValues val) {
    LocalConfiguration cfg = new LocalConfiguration();
    cfg.disableAllNativeReader();
    cfg.enable(val);
    return cfg;
  }
  
  protected List validate(LocalConfiguration cfg, ConfigurationValues val, int nb) {
    sleep(cfg.getTime(ConfigurationValues.DEFAULT_LIVE_TIME) * 1000L);
    validateNativeService();
    validateNativeReader(val);
    List result = validateWriter();
    assertTrue("La liste est null ou vide ", result != null && ! result.isEmpty());
    assertTrue("La liste n'a pas le bon nombre d'element ", result.size() >= nb);
    return result;
  }
  
  protected void validateNativeReader(ConfigurationValues val) {
    List<Reader<?>> readers  = new ArrayList<>();
    for ( Container<? extends Data> c : service.getContainers()) {
      readers.addAll(c.getReaders());
    }
    assertTrue(readers != null );
    assertTrue(readers.size() != 0 );
    Boolean contains = false ;
    for (Object reader : readers) {
      if ( ConfigurationValues.KEYBOARD.equals(val) && reader instanceof KeyboardReader) {
        contains = true ;
        break;
      }
      if ( ConfigurationValues.MOUSE.equals(val) && reader instanceof MouseReader) {
        contains = true ;
        break;
      }
      if ( ConfigurationValues.PROCESS.equals(val) && reader instanceof ProcessReader) {
        contains = true ;
        break;
      }
      if ( ConfigurationValues.SCREEN.equals(val) && reader instanceof ScreenReader) {
        contains = true ;
        break;
      }
    }
    assertTrue(contains);
  }
  
  protected List<Data> validateWriter(Container<? extends Data> c) {
    List<?> writers = c.getWriters();
    List<Data> result = new ArrayList<>();
    assertTrue(writers != null );
    assertTrue(writers.size() != 0 );
    Boolean contains = false ;
    for (Object writer : writers) {
      if (writer instanceof MemoryWriter) {
        contains = true ;
        result.addAll(((MemoryWriter) writer).getResult());
        break;
      }
    }
    assertTrue(contains);
    return result;
  }


  protected List<Data> validateWriter() {
    return validateWriter(service.getContainers().get(0));
  }

  protected void sleep(Long intervale) {
    try {
      Thread.sleep(intervale);
    } catch (InterruptedException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  private void setLvl(Configuration cfg) {
    Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(java.util.logging.Level.OFF);
    Configurator.setLevel(NativeContainer.class.getName(), Level.DEBUG);
    Configurator.setLevel(Container.class.getName(), Level.DEBUG);
    if ( cfg.isActif(ConfigurationValues.KEYBOARD)) {
      Configurator.setLevel(KeyboardReader.class.getName(), Level.DEBUG);
    }
    if ( cfg.isActif(ConfigurationValues.MOUSE)) {
      Configurator.setLevel(MouseReader.class.getName(), Level.DEBUG);
    }
    if ( cfg.isActif(ConfigurationValues.PROCESS)) {
      Configurator.setLevel(ProcessReader.class.getName(), Level.DEBUG);
    }
    if ( cfg.isActif(ConfigurationValues.SCREEN)) {
      Configurator.setLevel(ScreenReader.class.getName(), Level.DEBUG);
    }
  }

  protected void assertAll(Boolean... results) {
    for (Boolean res : results) {
      assertTrue(res);
    }
  }
  
  protected void moveMouse(int nb) {
    Robot r = null ;
    try {
      r = new Robot();
    } catch (AWTException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    for (int i = 0; i< nb; i++) {
      sleep(100L);
      r.mouseMove(10*i, 100);
      sleep(100L);
      r.mousePress(InputEvent.BUTTON1_MASK);
      sleep(100L);
      r.mouseRelease(InputEvent.BUTTON1_MASK);
    }
  }
  
  protected void pushKey(int... keys) {
    Robot r = null ;
    try {
      r = new Robot();
    } catch (AWTException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    for (int key : keys) {
      sleep(200L);
      r.keyPress(key);
      sleep(200L);
      r.keyRelease(key);
    }
  }
}

package re.apayet.acquisition.core.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import re.apayet.acquisition.core.NativeContainer;
import re.apayet.acquisition.model.base.abstraite.Container;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;
import re.apayet.acquisition.model.data.*;
import re.apayet.acquisition.model.base.abstraite.ImageData;
import re.apayet.acquisition.model.base.abstraite.NativeData;
import re.apayet.acquisition.model.reader.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static re.apayet.acquisition.model.base.configuration.ConfigurationValues.*;

public class ContainerManager {

  private static final Logger LOG = LogManager.getLogger(ContainerManager.class.getName()) ;
  private static List<ConfigurationValues> TYPED_CONTAINER = new ArrayList<>();

  static {
    TYPED_CONTAINER.add(KEYBOARD);
    TYPED_CONTAINER.add(MOUSE);
    TYPED_CONTAINER.add(PROCESS);
    TYPED_CONTAINER.add(SCREEN);
    TYPED_CONTAINER.add(CAM);
  }

  private Configuration cfg = null ;
  private Set<Container<? extends Data>> containers = new HashSet<>();

  private NativeContainer<? extends NativeData> nativeContainer;
  
  public ContainerManager(Configuration cfg) {
    this.cfg = cfg ;
    setUp();
  }

  private void setUp() {
    uniqueInstance();
    for (re.apayet.acquisition.model.base.configuration.ConfigurationValues val : TYPED_CONTAINER) {
      if ( cfg.isActif(val)) {
        LOG.debug("[ "+ val.name() + " ] : Building ... ");
        buildContainer(val);
      } else {
        LOG.warn("[ "+ val.name() + " ] : None instantiate.");
      }
    }
  }

  private void uniqueInstance() {
    if ( cfg.isActif(NATIVE_CONTAINER_INSTANCE_UNIQUE)) {
      this.nativeContainer = new NativeContainer<NativeData>() {
      };
    }
  }

  private void buildContainer(ConfigurationValues val) {
    AtomicReference<BuilderContainer> prop = new AtomicReference<>();
    if (KEYBOARD.equals(val)) {
      prop.set(new BuilderContainer(KEY_FLUSH_TIME, KEY_LIVE_TIME, new KeyboardReader(), getContainer(val)));
    }
    if (MOUSE.equals(val)) {
      prop.set(new BuilderContainer(MOUSE_FLUSH_TIME, MOUSE_LIVE_TIME, new MouseReader(), getContainer(val)));
    }
    if (PROCESS.equals(val)) {
      prop.set(new BuilderContainer(PROCESS_FLUSH_TIME, PROCESS_LIVE_TIME, new ProcessReader(), getContainer(val)));
    }
    if (SCREEN.equals(val)) {
      prop.set(new BuilderContainer(SCREEN_FLUSH_TIME, SCREEN_LIVE_TIME, new ScreenReader(), getContainer(val)));
    }
    if (CAM.equals(val)) {
      prop.set(new BuilderContainer(CAM_FLUSH_TIME, CAM_LIVE_TIME, new CamReader(), getContainer(val)));
    }
    if ( prop.get() != null ) {
      containers.add(prop.get().build(cfg));
    }
  }

  private NativeContainer<? extends NativeData> getContainer(re.apayet.acquisition.model.base.configuration.ConfigurationValues val) {
    NativeContainer<? extends NativeData> container = null;
    if ( cfg.isActif(NATIVE_CONTAINER_INSTANCE_UNIQUE)) {
      container = nativeContainer;
    } else {
      switch (val) {
        case SCREEN   : container = new NativeContainer<ScreenData>(); break;
        case KEYBOARD : container = new NativeContainer<KeyboardData>(); break;
        case MOUSE    : container = new NativeContainer<MouseData>(); break;
        case PROCESS  : container = new NativeContainer<ProcessData>(); break;
        case CAM      : container = new NativeContainer<WebCamData>(); break;
      }
    }
    return container;
  }

  public Collection<Container<? extends Data>> getContainers() {
    LOG.info("Container number [ " + containers.size() + " ]" );
    for (Container<? extends Data> c : containers) {
      LOG.debug("Reader number : [ " + c.getReaders().size() + " ] - " + c.getClass().getSimpleName() );
      LOG.debug("Writer number : [ " + c.getWriters().size() + " ] - " + c.getClass().getSimpleName() );
    }
    
    return containers;
  }


}

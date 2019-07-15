package re.apayet.acquisition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import re.apayet.acquisition.core.builder.ContainerManager;
import re.apayet.acquisition.model.base.abstraite.Container;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

import static re.apayet.acquisition.model.base.configuration.ConfigurationValues.DEFAULT_LIVE_TIME;

public class LauncherContainerService implements Runnable {

  private static final Logger LOG = LogManager.getLogger(LauncherContainerService.class.getName()) ;

  private List<Container<? extends Data>> containers = new ArrayList<>();
  private List<Thread> process = new ArrayList<>();
  private Configuration cfg = null ;
  
  public LauncherContainerService (Configuration cfg) {
    this.cfg = cfg ;
    init();
  }

  @Override
  public void run() {
    start();
    try {
      Thread.sleep(cfg.getTime(DEFAULT_LIVE_TIME) * 1000L);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void start() {
    if (process.isEmpty()) {
      process = new ArrayList<>();
      for (Container<? extends Data> container : containers) {
        LOG.info("[ " + container.getClass().getSimpleName() + " ] - Create");
        process.add(new Thread(container));
      }
    }

    for (Thread t : process) {
      t.start();
    }
  }

  private void init() {
    try {
      GlobalScreen.registerNativeHook();
    } catch (NativeHookException ex) {
      LOG.fatal(ex.getMessage());
      System.exit(1);
    }
    containers.addAll(new ContainerManager(cfg).getContainers());
  }

  public List<Container<? extends Data>> getContainers() {
    return containers;
  }

  public List<Thread> getProcess() {
    return process;
  }

  public Configuration getConfiguration() {
    return cfg;
  }
  
}

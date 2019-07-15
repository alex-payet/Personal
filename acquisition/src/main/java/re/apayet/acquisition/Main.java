package re.apayet.acquisition;

import org.jnativehook.GlobalScreen;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.configuration.ConfigurationProperties;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);
    Configuration cfg = new ConfigurationProperties(args[0]);
    LauncherContainerService service = new LauncherContainerService(cfg);
    new Thread(service).start();
    Thread.sleep(cfg.getTime(ConfigurationValues.DEFAULT_LIVE_TIME) * 1000L);
  }
  
}

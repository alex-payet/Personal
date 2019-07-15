package re.apayet.acquisition.model.base.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static re.apayet.acquisition.model.base.configuration.ConfigurationValues.*;

public class LocalConfiguration extends Configuration {

  private static Logger LOG = LogManager.getLogger(LocalConfiguration.class.getName());
  
  private static String FLUSH_TIME = "2";
  private static String LIVE_TIME = "5";
  
  public LocalConfiguration() {
    super();
    load();
  }

  private void load() {
    LOG.info("Chargement de la configuration local.");
    setActif(KEYBOARD, true);
    setActif(MOUSE, true);
    setActif(PROCESS, true);
    setActif(SCREEN, true);

    setActif(NATIVE_CONTAINER_INSTANCE_UNIQUE, false);

    setActif(JSON_WRITER, false);

    setValue(DEFAULT_FLUSH_TIME, FLUSH_TIME);
    setValue(DEFAULT_LIVE_TIME, LIVE_TIME);
    setValue(THREAD_SLEEP, "1");
  }
  
  public void disableAllNativeReader() {
    setActif(KEYBOARD, false);
    setActif(MOUSE, false);
    setActif(PROCESS, false);
    setActif(SCREEN, false);
  }
  
  public void enable(ConfigurationValues val) {
    setActif(val, true);
  }
  
  public void disable(ConfigurationValues val) {
    setActif(val, false);
  }
}

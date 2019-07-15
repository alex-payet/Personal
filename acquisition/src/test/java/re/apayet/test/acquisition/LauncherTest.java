package re.apayet.test.acquisition;

import org.jnativehook.GlobalScreen;
import org.junit.Test;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.configuration.ConfigurationProperties;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;
import re.apayet.test.acquisition.Launcher.AcquisitionTestHelper;

import java.util.logging.Logger;

public class LauncherTest extends AcquisitionTestHelper {

  @Test
  public void simpleLaunch() throws InterruptedException {
    Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(java.util.logging.Level.OFF);
    Configuration tesCfg = new ConfigurationProperties("src/test/resources/forLittleTest.properties");
    launch(tesCfg);
    Thread.sleep(tesCfg.getTime(ConfigurationValues.DEFAULT_LIVE_TIME) * 1000L);
  }

}

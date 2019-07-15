package re.apayet.acquisition.model.base.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class ConfigurationProperties extends Configuration {

    private static Logger LOG = LogManager.getLogger(Configuration.class.getName());

    private String defaulPath = "src/main/resources/configuration.properties";

    private Properties prop = null;

    public ConfigurationProperties(String path) {
        super();
        load(path);
    }

    public void load(String path) {
        if (path == null) {
            path = defaulPath;
        }
        loadProperties(path);
        set();
    }

    private void set() {
        if (prop != null) {
            LOG.debug("Element container " + Arrays.asList(ConfigurationValues.values()));
            for (ConfigurationValues key : Arrays.asList(ConfigurationValues.values())) {
                setPropertiesValue(key);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setPropertiesValue(ConfigurationValues key) {
        setValue(key, prop.getProperty(key.value()));
    }

    private void loadProperties(String path) {
        InputStream input = null;
        try {
            prop = new Properties();
            input = new FileInputStream(path);

            // load a properties file
            prop.load(input);
        } catch (final IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

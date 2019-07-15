package re.apayet.acquisition.model.base.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class Configuration {

  private static Logger LOG = LogManager.getLogger(Configuration.class.getName());

  protected Map<ConfigurationValues, Long> mappedTimeKey = new HashMap<>();
  protected Map<ConfigurationValues, Boolean> mappedBoolKey = new HashMap<>();
  protected Map<ConfigurationValues, String> mappedKey = new HashMap<>();

  public Long getTime(ConfigurationValues value) {
    return mappedTimeKey.get(value);
  }

  public String getValue(ConfigurationValues value) {
    return mappedKey.get(value);
  }

  public Boolean isActif(ConfigurationValues value) {
    Boolean result = mappedBoolKey.get(value);
    if ( result == null ) {
      result = Boolean.FALSE;
    }
    return result;
  }

  protected void setActif(ConfigurationValues key, Boolean value) {
    String action = (value) ? "Activation" : "Inactivation" ;
    LOG.info(action + " de " + key.name() );
    mappedBoolKey.put(key, value);
  }
  
  protected void setValue(ConfigurationValues key, String value) {
    if (isNotEmpty(value)) {
      mappedKey.put(key, value);
      if (isLong(value)) {
        mappedTimeKey.put(key, Long.valueOf(value));
      } else if (isBoolean(value)) {
        mappedBoolKey.put(key, Boolean.valueOf(value));
      }
      LOG.info("[ OK ] - " + key.value());
    } else {
      LOG.warn("[ KO ] - " + key.value());
    }
  }

  protected Boolean isNotEmpty(String value) {
    return value != null && !value.isEmpty() && !"".equals(value);
  }

  protected Boolean isLong(String value) {
    Boolean result = Boolean.FALSE;
    try {
      if (value != null) {
        Long.valueOf(value);
        result = Boolean.TRUE;
      }
    } catch (NumberFormatException e) {
      result = Boolean.FALSE;
    }
    return result;
  }

  protected Boolean isBoolean(String value) {
    Boolean result = Boolean.FALSE;
    if ( "true".equals(value.toLowerCase()) || "false".equals(value.toLowerCase())) {
      result = Boolean.TRUE;
    }
    return result;
  }
}

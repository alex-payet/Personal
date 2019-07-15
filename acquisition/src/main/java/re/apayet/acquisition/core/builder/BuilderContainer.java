package re.apayet.acquisition.core.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import re.apayet.acquisition.core.NativeContainer;
import re.apayet.acquisition.model.base.abstraite.Container;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;
import re.apayet.acquisition.model.base.abstraite.NativeData;
import re.apayet.acquisition.model.data.trigger.TriggerEvent;
import re.apayet.acquisition.model.writer.JsonWriter;
import re.apayet.acquisition.model.writer.MemoryWriter;

import java.util.concurrent.atomic.AtomicReference;

import static re.apayet.acquisition.model.base.configuration.ConfigurationValues.JSON_WRITER;
import static re.apayet.acquisition.model.base.configuration.ConfigurationValues.MEMORY_WRITER;

public class BuilderContainer {

  public static final Logger LOG = LogManager.getLogger(BuilderContainer.class.getName());

  private Reader reader;
  private ConfigurationValues flushValue;
  private ConfigurationValues liveValue;
  private NativeContainer<? extends NativeData> container;

  public BuilderContainer(ConfigurationValues flushValue, ConfigurationValues liveValue, 
      Reader reader, NativeContainer<? extends NativeData> container) {
    super();
    this.flushValue = flushValue;
    this.liveValue = liveValue;
    this.reader = reader;
    this.container = container;
  }

  public NativeContainer<? extends NativeData> build(Configuration cfg) {
    String containerName = reader.getClass().getSimpleName().replace("Reader", "Container");
    LOG.info("[ " + containerName + " ] - Configuration ...");
    container.configure(cfg);
    container.addReader(reader);
    addJsonWriter(container, JSON_WRITER, cfg);
    addMemoryWriter(container, MEMORY_WRITER, cfg);
    container.addTrigger(createTriggerEvent(cfg, flushValue, liveValue));
    return container;
  }

  /* ___________________________________ STATIC METHOD __________________________________________________*/

  public static TriggerEvent createTriggerEvent(Configuration cfg, ConfigurationValues flush, ConfigurationValues live) {
     return new TriggerEvent(getFlushTime(cfg, flush), getLiveTime(cfg, live));
  }

  public static void addMemoryWriter(Container<? extends Data> container, ConfigurationValues value, Configuration cfg) {
    if (cfg.isActif(value)) {
      container.addWriter(new MemoryWriter<>());
      LOG.info("[ " + container.getClass().getSimpleName() + " ] - MEMORY_WRITER");
    }
  }
  
  public static void addJsonWriter(Container<? extends Data> container, ConfigurationValues value, Configuration cfg) {
    if (cfg.isActif(value)) {
      container.addWriter(new JsonWriter<>(cfg));
      LOG.info("[ " + container.getClass().getSimpleName() + " ] - JSON_WRITER");
    }
  }

  private static Long getFlushTime(Configuration cfg, ConfigurationValues cfgVal) {
    AtomicReference<Long> time = new AtomicReference<>(cfg.getTime(cfgVal));
    if (time.get() == null) {
      time.set(cfg.getTime(ConfigurationValues.DEFAULT_FLUSH_TIME));
      LOG.info("Default flush configuration [ " + time + "s ]");
    }
    return time.get();
  }
  
  private static Long getLiveTime(Configuration cfg, ConfigurationValues cfgVal) {
    AtomicReference<Long> time = new AtomicReference<>(cfg.getTime(cfgVal));
    if (time.get() == null) {
      time.set(cfg.getTime(ConfigurationValues.DEFAULT_LIVE_TIME));
      LOG.info("Default live configuration [ " + time + "s ]");
    }
    return time.get();
  }
}

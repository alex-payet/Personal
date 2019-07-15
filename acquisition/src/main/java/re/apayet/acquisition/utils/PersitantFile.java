package re.apayet.acquisition.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.def.Configurable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PersitantFile<T extends Data> {

  public static final Logger LOG = LogManager.getLogger(PersitantFile.class.getName());

  private String baseRep ;
  private FileWriter w;

  public PersitantFile(String baseRep) {
    this.baseRep = baseRep;
  }

  public void write(String jsonData, Class<? extends Data> clazz) {
    BuilderFileName builder = new BuilderFileName(clazz, this.baseRep);
    try {
      File file = builder.buildFile();
      LOG.debug("file != null " + (file != null));
      if (file != null) {
        w = new FileWriter(file);
        w.write(jsonData);
        printLog(LOG.getLevel(), clazz, jsonData);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (w != null) {
          w.close();
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private void printLog(Level lvl, Class<? extends Data> clazz, String jsonData) {
    if ( LOG.getLevel().equals(Level.INFO)) {
      LOG.info("Write [ " + clazz.getCanonicalName() + " ] ");
    }
    if ( LOG.getLevel().equals(Level.TRACE)) {
      LOG.trace("Write [ " + clazz.getCanonicalName() + " ] : " + jsonData);
    }
  }
}

package re.apayet.acquisition.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.data.*;
import re.apayet.acquisition.model.base.abstraite.ImageData;

import java.io.File;
import java.io.IOException;

public class BuilderFileName {
  
  public static Logger LOG = LogManager.getLogger(BuilderFileName.class.getName());
  
  private static String REP_SEP = "/";
  private static String SEP = "_";
  private static String EXT = ".json";
  private static String KEY_DIR = "keyboard";
  private static String MOUSE_DIR = "mouse";
  private static String PROCESS_DIR = "process";
  private static String SCREEN_DIR = "screen";
  private static String CAM_DIR = "cam";
  
  private DateTime d = new DateTime();
  private String year = Integer.toString(d.year().get());
  private String monthOfYear = Integer.toString(d.monthOfYear().get());
  private String dayOfMonth = Integer.toString(d.getDayOfMonth());
  private String hourOfDay = Integer.toString(d.getHourOfDay());
  private String minuteOfHour = Integer.toString(d.getMinuteOfHour());
  
  private String pathType;
  private String baseDir;
  
  private StringBuffer sb = new StringBuffer();
  
  public BuilderFileName(Class<? extends Data> clazz, String baseDir) {
    if (KeyboardData.class.equals(clazz)) {
      this.pathType = KEY_DIR;
    } else if (MouseData.class.equals(clazz)) {
      this.pathType = MOUSE_DIR;
    } else if (ProcessData.class.equals(clazz)) {
      this.pathType = PROCESS_DIR;
    } else if (ScreenData.class.equals(clazz)) {
      this.pathType = SCREEN_DIR;
    } else if (WebCamData.class.equals(clazz)) {
      this.pathType = CAM_DIR;
    }
    if (baseDir.endsWith(REP_SEP)) {
      this.baseDir = baseDir.substring(0, baseDir.length()-1);
    } else {
      this.baseDir = baseDir;  
    }
    LOG.debug("[ " +this.pathType + " ] : repertoire de destination : " + baseDir);
  }
  
  public File buildFile() throws IOException {
    File f = null;
    if ( buildRep()) {
      String filename = getFileName();
      f = new File(filename);
      f.createNewFile();
      LOG.info("[ " + this.pathType + " ] - Creation du repertoire : " + filename);
    }
    return f;
  }
  
  private Boolean buildRep() {
    return existOrCreate(sb.append(baseDir)             .append(REP_SEP).toString()) 
        && existOrCreate(sb.append(getRepDayMonth())    .append(REP_SEP).toString()) 
        && existOrCreate(sb.append(this.pathType)       .append(REP_SEP).toString());
  }
  
  private String getFileName() {
    return sb.append(getRepHourMinute()).toString();
  }
  
  private Boolean existOrCreate(String path) {
    Boolean result = true;
    File fileBaseDir = new File(path);
    if (!fileBaseDir.exists()) {
      result = fileBaseDir.mkdirs();
      LOG.debug("[ " +this.pathType + " ] - Creation du sous-repertoire : " + path + " [ " + result + " ]");
    } else {
      result = fileBaseDir.isDirectory();
      LOG.debug("[ " +this.pathType + " ] - Sous-repertoire cree : " + path + " [ " + result + " ]");
    }
    return result ;
  }
  
  private String getRepDayMonth() {
    return new StringBuffer(year).append(SEP)
        .append(monthOfYear).append(SEP)
        .append(dayOfMonth).toString();
  }
  
  private String getRepHourMinute() {
    return new StringBuffer().append(hourOfDay).append(SEP).append(minuteOfHour).append(EXT).toString();
  }
} 

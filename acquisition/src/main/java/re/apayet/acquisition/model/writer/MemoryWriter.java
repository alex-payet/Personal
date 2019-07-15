package re.apayet.acquisition.model.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.abstraite.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryWriter<T extends Data> implements Writer<T> {

  private static Logger LOG = LogManager.getLogger(MemoryWriter.class.getName());
  
  private List<T> result = null;
  
  @Override
  public void save(List<T> datas) {
    if ( datas != null && ! datas.isEmpty() ) {
      if ( result == null ) {
        LOG.info("Instantiation de la liste avec : " + datas.size());
        result = Collections.synchronizedList(new ArrayList<>(datas));
      } else {
        LOG.info("Saving : " + datas.size() + " elements");
        result.addAll(datas);
      }
    }
  }

  public List<T> getResult() {
    return result;
  }

}

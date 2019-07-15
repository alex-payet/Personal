package re.apayet.acquisition.utils;

import org.codehaus.jackson.map.ObjectMapper;
import re.apayet.acquisition.model.base.abstraite.Data;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class JsonConverter<T extends Data> {

  public String convert(Collection<T> datas) {
    ObjectMapper mapper = new ObjectMapper();

    //Convert object to JSON string
    AtomicReference<String> jsonInString = new AtomicReference<>();
    try {
      //Convert object to JSON string and pretty print
      jsonInString.set(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(datas));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return jsonInString.get();
  }
}

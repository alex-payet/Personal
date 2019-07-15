package re.apayet.acquisition.model.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import re.apayet.acquisition.model.base.abstraite.Data;
import re.apayet.acquisition.model.base.abstraite.Writer;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.configuration.ConfigurationValues;
import re.apayet.acquisition.utils.PersitantFile;
import re.apayet.acquisition.utils.JsonConverter;

import java.util.List;

public class JsonWriter<T extends Data> implements Writer<T> {

    private static final Logger LOG = LogManager.getLogger(JsonWriter.class.getName());

    private PersitantFile<T> p ;
    private JsonConverter<T> c ;

    public JsonWriter(Configuration configuration) {
        super();
        c = new JsonConverter();
        p = new PersitantFile(configuration.getValue(ConfigurationValues.JSON_WRITER_PATH));
    }


    @Override
    public void save(List<T> datas) {
        LOG.trace("datas == null " + (datas == null) + " datas.isEmpty() " + (datas.isEmpty()));
        if (datas != null && !datas.isEmpty()) {
            p.write(c.convert(datas), datas.get(0).getClass());
        }
    }

}

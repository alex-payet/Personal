package re.apayet.acquisition.model.base.abstraite;

import java.util.List;

public interface Writer<T extends Data> {

    void save(List<T> datas);

}

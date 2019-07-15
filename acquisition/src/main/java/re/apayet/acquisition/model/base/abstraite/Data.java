package re.apayet.acquisition.model.base.abstraite;

import java.util.UUID;

/**
 * Abstract class of alla data
 */
public abstract class Data {

    private UUID id = null;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}

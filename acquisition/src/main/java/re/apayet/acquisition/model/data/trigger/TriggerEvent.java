package re.apayet.acquisition.model.data.trigger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerEvent {

    private static final Logger LOG = LogManager.getLogger(TriggerEvent.class.getName());

    private Long flush = null;
    private Long live = null;

    public TriggerEvent(Long flush, Long live) {
        super();
        this.flush = flush;
        this.live = live;
    }

    public Boolean trig(Long currentCounter) {
        LOG.trace("[ " + (currentCounter >= getFlush()) + " ] - TriggerEvent : " + currentCounter + " >= " + getFlush());
        return currentCounter >= getFlush();
    }

    public Long getFlush() {
        return flush;
    }

    public void setFlush(Long flush) {
        this.flush = flush;
    }

    public Long getLive() {
        return live;
    }

    public void setLive(Long live) {
        this.live = live;
    }
}

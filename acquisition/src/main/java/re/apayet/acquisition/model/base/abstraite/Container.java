package re.apayet.acquisition.model.base.abstraite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import re.apayet.acquisition.model.base.configuration.Configuration;
import re.apayet.acquisition.model.base.def.Configurable;
import re.apayet.acquisition.model.data.trigger.TriggerEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static re.apayet.acquisition.model.base.configuration.ConfigurationValues.THREAD_SLEEP;

public abstract class Container<T extends Data> implements Runnable, Configurable {

    private static final Logger LOG = LogManager.getLogger(Container.class.getName());

    // La trame temporel
    private static final Long TIME_SEP = 1000L;

    protected List<T> datas = Collections.synchronizedList(new ArrayList<T>());

    protected Configuration configuration = null;

    protected List<Reader<T>> readers = new ArrayList<>();
    private List<Writer<T>> writers = new ArrayList<>();
    private Collection<TriggerEvent> triggers = new ArrayList<>();

    public void configure(Configuration configuration) {
        this.configuration = configuration;
    }

    protected void launch() {

        if (this.configuration == null) {
            throw new IllegalAccessError("Configuration not found.");
        }

        instantiateReader();

        AtomicReference<Long> counter = new AtomicReference<>(0L);
        AtomicReference<Long> counterFlush = new AtomicReference<>(0L);

        while (counter.get() < getLiveCounter()) {
            LOG.trace("Live time [ " + (getLiveCounter() - counter.get()) + " s ]");
            counter.getAndSet(counter.get() + 1);
            try {
                LOG.trace("Sleep [ " + this.configuration.getTime(THREAD_SLEEP) + " s ]");
                Thread.sleep(this.configuration.getTime(THREAD_SLEEP) * TIME_SEP);
                if (canFlush(counterFlush.get()) || getLiveCounter().equals(counter)) {
                    LOG.debug("Flushing ... ");
                    LOG.trace("Flush [ state ] : " + counter + " < " + getLiveCounter());
                    flush();
                    counterFlush.set(0L);
                } else {
                    counterFlush.getAndSet(counterFlush.get() + 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        isValid(readers);
        for (Reader<T> reader : readers) {
            reader.listen();
        }
    }

    public synchronized void flush() {
        if (isValid(readers)) {
            for (Reader<T> reader : readers) {
                datas.addAll(reader.flush());
            }
        }
        cleanData();
        if (isValid(writers)) {
            for (Writer<T> writer : writers) {
                if (writer == null) {
                    LOG.error("Writer is null for " + readers.get(0).getClass().getSimpleName().replace("Reader", "Container"));
                } else {
                    writer.save(datas);
                }
            }
        }
        datas = Collections.synchronizedList(new ArrayList<T>());
    }

    protected abstract void cleanData();

    public Boolean canFlush(Long currentCounter) {
        Boolean result = Boolean.TRUE;
        for (TriggerEvent trigger : triggers) {
            if (!trigger.trig(currentCounter)) {
                result = Boolean.FALSE;
                break;
            }
        }
        LOG.trace("Trigger [ " + result + " ]");
        return result;
    }

    protected Long getLiveCounter() {
        long liveCounter = 0;
        for (TriggerEvent trigger : triggers) {
            if (trigger.getLive() > liveCounter) {
                liveCounter = trigger.getLive();
            }
        }
        return liveCounter;
    }

    protected void instantiateReader() {
        for (Reader<T> reader : readers) {
            reader.listen();
        }
    }

    public List<Reader<T>> getReaders() {
        return this.readers;
    }

    public void addReader(Reader<T> reader) {
        this.readers.add(reader);
    }

    public List<Writer<T>> getWriters() {
        return this.writers;
    }

    public void addWriter(Writer<T> writer) {
        this.writers.add(writer);
    }

    public Collection<TriggerEvent> getTriggers() {
        return this.triggers;
    }

    public void addTrigger(TriggerEvent trigger) {
        this.triggers.add(trigger);
    }

    private Boolean isValid(@SuppressWarnings("rawtypes") Collection objects) {
        return objects != null && !objects.isEmpty();
    }

}

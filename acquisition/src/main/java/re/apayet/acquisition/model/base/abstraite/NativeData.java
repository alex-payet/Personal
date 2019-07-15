package re.apayet.acquisition.model.base.abstraite;

import re.apayet.acquisition.model.data.ActionKey;

import java.util.UUID;

public abstract class NativeData extends Data {

    protected long time;

    protected ActionKey action;

    private byte[] internalValue;

    public NativeData() {
        super();
        this.time = System.currentTimeMillis();
    }

    public NativeData(ActionKey action) {
        super();
        this.time = System.currentTimeMillis();
        this.action = action;
    }

    public NativeData(long time, ActionKey action) {
        super();
        this.time = time;
        this.action = action;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ActionKey getAction() {
        return action;
    }

    @Override
    public String toString() {
        return " [ " + getId() + " ] - [ " + getAction() + " ] - [ " + getTime() + " ] ";
    }

    public byte[] getInternalValue() {
        return internalValue;
    }

    protected void setInternalValue(byte[] internalValue) {
        this.internalValue = internalValue;
        setId(UUID.nameUUIDFromBytes(this.internalValue));
    }

    public abstract void createInternalValue();
}

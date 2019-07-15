package re.apayet.acquisition.model.data;

import re.apayet.acquisition.model.base.abstraite.NativeData;

public class ProcessData extends NativeData {

    private String value;

    public ProcessData(String value, ActionKey action) {
        super(action);
        this.value = value;
    }

    public ProcessData(String value, Long when, ActionKey action) {
        super(when, action);
        this.value = value;
    }

    public String getProcess() {
        return value;
    }

    public void setProcess(String value) {
        this.value = value;
    }

    @Override
    public void createInternalValue() {
        setInternalValue(value.getBytes());
    }
}

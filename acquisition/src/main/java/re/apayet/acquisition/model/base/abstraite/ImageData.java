package re.apayet.acquisition.model.base.abstraite;

import re.apayet.acquisition.model.data.ActionKey;

public abstract class ImageData extends NativeData {

    private byte[] value;
    private String pathName;

    public ImageData(byte[] value, ActionKey action) {
        super(action);
        this.value = value;
    }

    public ImageData(byte[] value, Long when, ActionKey action) {
        super(when, action);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public void createInternalValue() {
        setInternalValue(value);
    }

}

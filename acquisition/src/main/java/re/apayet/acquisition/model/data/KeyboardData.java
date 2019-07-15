package re.apayet.acquisition.model.data;

import re.apayet.acquisition.model.base.abstraite.NativeData;

import java.nio.ByteBuffer;

public class KeyboardData extends NativeData {

    private Integer value;

    public KeyboardData(Integer value, ActionKey action) {
        super(action);
        this.value = value;
    }

    public KeyboardData(Integer value, Long time, ActionKey action) {
        super(time, action);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void createInternalValue() {
        byte[] bytes = new byte[4];
        ByteBuffer.wrap(bytes).putInt(this.value);
        setInternalValue(bytes);
    }

}

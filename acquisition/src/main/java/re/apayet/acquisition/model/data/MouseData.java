package re.apayet.acquisition.model.data;

import re.apayet.acquisition.model.base.abstraite.NativeData;

import java.nio.ByteBuffer;

public class MouseData extends NativeData {

    private static Long Y_RANG = 1_0000L;

    private Integer x;
    private Integer y;

    public MouseData(Integer x, Integer y, ActionKey action) {
        super(action);
        this.x = x;
        this.y = y;
    }

    public MouseData(Integer x, Integer y, Long time, ActionKey action) {
        super(time, action);
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public void createInternalValue() {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(buildUniqueElement());
        setInternalValue(buffer.array());
    }

    //Regle de creation d'element
    private Long buildUniqueElement() {
        return (Y_RANG * y) + x;
    }
}

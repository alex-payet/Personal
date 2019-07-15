package re.apayet.acquisition.model.data;

import re.apayet.acquisition.model.base.abstraite.ImageData;
import re.apayet.acquisition.model.base.abstraite.NativeData;

import java.nio.ByteBuffer;

public class ScreenData extends ImageData {

    public ScreenData(byte[] value, Long when, ActionKey action) {
        super(value, when, action);
    }

}

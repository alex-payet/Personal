package re.apayet.acquisition.model.data;

import re.apayet.acquisition.model.base.abstraite.ImageData;
import re.apayet.acquisition.model.base.abstraite.NativeData;

import java.nio.ByteBuffer;

public class WebCamData extends ImageData {

    public WebCamData(byte[] value, Long when, ActionKey action) {
        super(value, when, action);
    }

}

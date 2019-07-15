package re.apayet.acquisition.model.base.abstraite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class Reader<T extends Data> {

    private Collection<T> datas = Collections.synchronizedList(new ArrayList<T>());

    private Boolean work = Boolean.TRUE;

    public synchronized Collection<T> flush() {
        //stop();
        //copy
        Collection<T> result = new ArrayList<>(datas);
        //reset
        datas = Collections.synchronizedList(new ArrayList<T>());
        //listen();
        return result;
    }

    public synchronized void listen() {
        this.work = Boolean.TRUE;
    }

    public synchronized void stop() {
        this.work = Boolean.FALSE;
    }

    protected synchronized Boolean isWork() {
        return work;
    }

    protected void addData(T data) {
        datas.add(data);
    }

    protected byte[] createImage(BufferedImage image) {
        ByteArrayOutputStream baos = null;
        byte[] value = null;
        try {
            baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            value = baos.toByteArray();
            baos.close();
        } catch (HeadlessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }
}

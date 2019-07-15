package re.apayet.test.acquisition.example;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WebCamExample{

    public static void main(String[] args) {
        BufferedImage image;
        try {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            image = webcam.getImage();
            ImageIO.write(image, "png", new File("webcam.png"));
        } catch (HeadlessException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

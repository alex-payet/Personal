package re.apayet.test.acquisition.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenListenerExample {

  public static void main(String[] args) {
    BufferedImage image;
    try {
      image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
      ImageIO.write(image, "png", new File("screenshot.png"));
    } catch (HeadlessException | AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

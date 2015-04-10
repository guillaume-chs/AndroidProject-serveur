package socketImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

public class Client {
    static BufferedImage bimg;

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 8888;
        try {
            Socket client = new Socket(serverName, port);
            Robot bot;
            bot = new Robot();
            bimg = bot.createScreenCapture(new Rectangle(0, 0, 200, 100));
            ImageIO.write(bimg, "JPG", client.getOutputStream());
            client.close();
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
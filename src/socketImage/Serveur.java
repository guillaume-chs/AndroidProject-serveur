package socketImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class Serveur extends Thread {
    Socket server;
    private ServerSocket serverSocket;

    public Serveur(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.setSoTimeout(180000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t = new Serveur(8888);
        t.start();
    }

    public void run() {
        while (true) {
            try {
                server = serverSocket.accept();
                BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
                JFrame frame = new JFrame();
                frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                frame.pack();
                frame.setVisible(true);
            } catch (SocketTimeoutException st) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
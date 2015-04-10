package socket.client;

import socket.serveur.Serveur;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Thread emission;
    private Thread reception;
    private Socket client;

    public Client(final String host, final int port) {
        if (host == null) {
            throw new NullPointerException("L'hôte doit être spécifié");
        }
        if (port < Serveur.PORT_MIN || port > Serveur.PORT_MAX) {
            throw new IllegalArgumentException("le port est hors de portée : " + port);
        }

        try {
            this.client = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Impossible de se connecter au serveur : " + e.getMessage());
            System.exit(1);
        }

        // On initialise les threads d'émission et de réception des messages
        try {
            emission = new Thread(new Emission(this.client.getOutputStream()));
            emission.start();
            reception = new Thread(new Reception(this.client.getInputStream()));
            reception.start();
        } catch (IOException e) {
            System.err.println("Une erreur est survenue avec le socket : \n" + e.getMessage());
            System.exit(1);
        }

//        try {
//            this.client.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(final String[] args) {
        new Client("localhost", 8888);
    }
}
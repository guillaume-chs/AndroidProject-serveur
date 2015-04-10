package socketImageAndroid;

import org.apache.commons.codec.binary.Base64;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Thread agent qui gère la connexion d'un client.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class ClientAgent implements Runnable {
    private final Socket client;

    /**
     * Créé un agent pour le socket client passé en paramètre.
     *
     * @param socketClient le socket client qui vient de se connecter
     */
    public ClientAgent(final Socket socketClient) {
        this.client = socketClient;
    }

    /**
     * Encode le tableau-donnée d'une image passé en paramètre en une chaîne de caractères en 64bit, visée à être envoyée directement par le socket.
     *
     * @param imageByteArray le tableau-donnée d'une image
     * @return une chaîne de caractères-donnée de l'image en 64bit
     */
    private static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64String(imageByteArray);
    }

    /**
     * Coeur du thread : gère le socket client.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            final File myFile = new File(Serveur.class.getResource("network.gif").getFile());
            final FileInputStream imageInFile = new FileInputStream(myFile);
            byte imageData[] = new byte[(int) myFile.length()];
            int imageRead = imageInFile.read(imageData);

            // Check size read
            System.out.println("Size : " + (int) myFile.length() + " - Read : " + imageRead);

            // Converting Image byte array into Base64 String
            final String imageDataString = ClientAgent.encodeImage(imageData);
            System.out.println("Encoded image size : " + imageDataString.length());

            // Outputs the image data
            final DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            outputStream.writeUTF(imageDataString);
            outputStream.flush();

            System.out.println("Sending...");

            // Then close the connexion
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

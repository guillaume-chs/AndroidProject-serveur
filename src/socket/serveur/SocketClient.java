package socket.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe SocketClient. Implements Runnable car elle doit être considérée comme un thread qui le serveur lance pour chaque client qui se connecte.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class SocketClient extends Thread {
    private final Socket client;
    private final Serveur serveur;
    private final int numero_client;

    private BufferedReader in = null;
    private PrintWriter out = null;

    /**
     * Initialise le gestionnaire de la connexion du client passé en paramètre.
     *
     * @param socket le socket client connecté
     * @throws NullPointerException si le socket entré est null
     * @throws IOException          si on ne peut lire ou écrire dans le socket
     */
    public SocketClient(final int numero_client, final Socket socket, final Serveur serveur) throws IOException {
        if (socket == null) {
            throw new NullPointerException("Le socket client ne peut être null");
        }

        // Une exception IOException est levée si on peut ni lire ou écrire dans le socket
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        // Sinon tout va bien et on initialise le client
        this.numero_client = numero_client;
        this.client = socket;
        this.serveur = serveur;
    }

    /**
     * Méthode run.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        // Envoi du message d'accueil
        final String message = "Bienvenue sur le serveur, vous êtes le client numéro " + this.numero_client;
        out.println(message);
        out.flush();

        while (true) {
            // Réception d'un message quelconque
            try {
                System.out.println("[Client " + this.numero_client + "] : " + in.readLine());
            } catch (IOException e) {
                System.err.println("Impossible de lire le message du client numéro " + this.numero_client + " : " + e.getMessage());
            }
        }
    }

    public void kill() {
        try {
            this.client.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture du socket : " + e.getMessage());
        }
        this.interrupt();
    }
}

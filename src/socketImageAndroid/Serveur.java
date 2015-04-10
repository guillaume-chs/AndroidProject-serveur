package socketImageAndroid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classe serveur chargée de la réception d'images.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class Serveur {
    /**
     * La taille de la file d'attente par défaut.
     */
    public static final int SIZE_WAITLIST_DEFAULT = 10;
    /**
     * Nombre de clients que peut gérer au maximum le serveur en même temps;
     */
    public static final int SIZE_THREADPOOL_MAX = 15;
    /**
     * L'adresse du serveur.
     */
    public final String HOST_SOCKET;
    /**
     * Le port du serveur.
     */
    public final int PORT_SOCKET;
    /**
     * La taille de la file d'attente.
     */
    public final int SIZE_WAITLIST;

    private final ExecutorService executorService;
    private ServerSocket serveurSocket;

    /**
     * Lance un serveur à l'écoute de l'adresse et du port donnés. La taille de la file d'attente est spécifiée par <i>SIZE_WAITLIST_DEFAULT</i>.
     *
     * @param host l'adresse du serveur
     * @param port le port d'écoute
     * @see Serveur#SIZE_WAITLIST_DEFAULT
     * @see Serveur#Serveur(String, int, int)
     */
    public Serveur(final String host, final int port) {
        this(host, port, Serveur.SIZE_WAITLIST_DEFAULT);
    }

    /**
     * Lance un serveur à l'écoute de l'adresse et du port donnés. La taille de la file d'attente est également précisée.
     *
     * @param host          l'adresse du serveur
     * @param port          le port d'écoute
     * @param size_waitlist la taille de la file d'attente
     * @throws NullPointerException     si l'adresse est nulle
     * @throws IllegalArgumentException si l'adresse est trop longue ou trop petite
     * @throws IllegalArgumentException si le port n'est pas compris en 0 et 0xFFFF
     */
    public Serveur(final String host, final int port, final int size_waitlist) {
        if (host == null)
            throw new NullPointerException("L'adresse ne peut être nulle");
        if (host.length() < 7 || host.length() > 15)
            throw new IllegalArgumentException("L'adresse est invalide. Elle doit être de la forme X.X.X.X");
        if (port < 1 || port > 0xFFFF)
            throw new IllegalArgumentException("Le port est hors de portée : " + port);

        this.PORT_SOCKET = port;
        this.HOST_SOCKET = host;
        this.SIZE_WAITLIST = size_waitlist;

        // DynamicThreadPool
        this.executorService = Executors.newFixedThreadPool(Serveur.SIZE_THREADPOOL_MAX);

        // create socket
        try {
            this.serveurSocket = new ServerSocket(this.PORT_SOCKET, this.SIZE_WAITLIST, InetAddress.getByName(this.HOST_SOCKET));
        } catch (IOException e) {
            System.err.println("Couldn't open socket");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Main. Lance le serveur.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        final Serveur serveur = new Serveur("0.0.0.0", 8888);
        serveur.run();
    }

    /**
     * Coeur du serveur. Le serveur n'est cependant pas un thread, malgrès le nom de la méthode.
     */
    public void run() {
        while (true) {
            System.out.println("Waiting...");

            try {
                // Get connected client
                final Socket client = this.serveurSocket.accept();
                System.out.println("Accepted connection : " + client);

                // Execute client threaded agent
                this.executorService.execute(new ClientAgent(client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

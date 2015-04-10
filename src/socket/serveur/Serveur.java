package socket.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Serveur (Thread). Cette classe définit le serveur du package d'exemple "socket". Basiquement, il se charge pour l'instant de gérer un pool de client, et d'intéragir simplement avec eux.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class Serveur extends Thread {
    /**
     * Le port d'écoute minimum autorisé.
     */
    public final static int PORT_MIN = 1;
    /**
     * Le port d'écoute maximum autorisé.
     */
    public final static int PORT_MAX = 0xFFFF;

    private final int nb_clients_max;
    private final List<SocketClient> sockets;
    private ServerSocket server;

    /**
     * Créé un nouveau serveur. Celui est un thread.
     *
     * @param port           le port d'écoute
     * @param nb_clients_max le nombre maximum de clients dans la file d'attente du serveur
     * @throws IllegalArgumentException si le port demandé n'est pas autorisé
     * @throws IllegalArgumentException si le nombre de clients est négatif ou égal à zéro
     */
    public Serveur(final int port, final int nb_clients_max) {
        if (port < PORT_MIN || port > PORT_MAX) {
            throw new IllegalArgumentException("le port est hors de portée : " + port);
        }
        if (nb_clients_max <= 0) {
            throw new IllegalArgumentException("le nombre de clients doit être positif");
        }

        // Initialisation du socket serveur
        try {
            this.server = new ServerSocket(port, nb_clients_max);
        } catch (IOException e) {
            System.out.println("Impossible d'écouter sur le port " + port + " : " + e.getMessage());
            System.exit(1);
        }

        // Si tout va bien
        this.nb_clients_max = nb_clients_max;
        this.sockets = new ArrayList<>(this.nb_clients_max);
        System.out.println("Le serveur est initialisé : " + this.toString());
    }

    /**
     * Méthode run du serveur. Se contente de gérer les ajouts et connexions des clients, pour lesquels un thread est lancé à chaque fois.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            // Initialisation du socket qui va se connecter
            Socket socket = null;
            SocketClient client = null;
            try {
                socket = this.server.accept();

                // Création du thread asoscié
                client = new SocketClient(this.sockets.size(), socket, this);
                client.setDaemon(true);
                if (this.addClient(client)) {
                    client.start();
                }

                // On wait 10ms afin de ne pas boucler trop vite
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Une erreur s'est produite avec le client " + socket);
                client.kill();
                this.removeClient(client);
            }
        }
    }

    /**
     * Ajoute un client à la liste de clients du serveur.
     *
     * @param client le client à ajouter
     * @return <code>true</code> si le client a été ajouté; <br>
     * <code>false</code> sinon;
     * @throws NullPointerException si le socket client passé en paramètre est null
     */
    boolean addClient(final SocketClient client) {
        if (client == null) {
            throw new NullPointerException("Le socket client ne peut être null");
        }
        if (this.sockets.size() < this.nb_clients_max) {
            this.sockets.add(client);
            return true;
        }
        return false;
    }

    /**
     * Retire un client de la liste de clients du serveur.
     *
     * @param client le client à retirer
     * @return <code>true</code> si le client a été retiré; <br>
     * <code>false</code> sinon;
     * @throws NullPointerException si le socket client passé en paramètre est null
     */
    boolean removeClient(final SocketClient client) {
        if (client == null) {
            throw new NullPointerException("Le socket client ne peut être null");
        }
        if (!(client.isInterrupted())) {
            client.interrupt();
        }
        if (this.sockets.contains(client)) {
            this.sockets.remove(client);
            return true;
        }
        return false;
    }

    /**
     * Retourne une chaîne caractéristique du serveur.
     *
     * @return une chaîne caractéristique du serveur
     */
    @Override
    public String toString() {
        return "Serveur{" +
                "address : " + server.getInetAddress() + ":" + server.getLocalPort() +
                ", nb_clients_max=" + nb_clients_max +
                '}';
    }
}

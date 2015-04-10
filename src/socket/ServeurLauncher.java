package socket;

import socket.serveur.Serveur;

/**
 * Se contente de lancer le serveur.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class ServeurLauncher {

    /**
     * Main.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        final Serveur serveur = new Serveur(8888, 12);
        serveur.setDaemon(true);
        serveur.start();

        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Fin du serveur");
            serveur.interrupt();
        }
    }
}

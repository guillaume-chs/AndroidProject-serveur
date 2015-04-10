package socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Classe thread Reception. S'occupe de la réception des messages provenant du serveur.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class Reception implements Runnable {
    private BufferedReader reader;

    public Reception(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        while (true) {
            try {
                final String message = this.reader.readLine();
                if (message != null)
                    System.out.println("Nouveau message reçu : " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

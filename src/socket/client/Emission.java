package socket.client;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Classe thread d'émission des messages.
 *
 * @author Guillaume Chanson
 * @version 1.0
 * @since 8.0
 */
public class Emission implements Runnable {
    private final Scanner scanner;
    private PrintWriter writer;

    public Emission(final OutputStream outputStream) {
        this.scanner = new Scanner(System.in);
        this.writer = new PrintWriter(outputStream);
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("Votre message : ");
            final String message = scanner.nextLine();
            this.writer.println(message);
            this.writer.flush();
        }
    }
}

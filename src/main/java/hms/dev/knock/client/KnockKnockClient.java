package hms.dev.knock.client;

import java.util.Random;
import java.util.stream.IntStream;

public class KnockKnockClient {
    public static void main(String[] args) {

        final int NUMBER_OF_CLIENTS = 5;
        final String MESSAGE_TEMPLATE = "%s|%s";

        Random random = new Random();
        String host = "localhost";
        int port = 8080;

        IntStream.range(0, NUMBER_OF_CLIENTS).forEach(clientId -> {
            int productId = random.nextInt(4) + 1;
            int quantity = random.nextInt(10) + 1;
            String message = String.format(MESSAGE_TEMPLATE, productId, quantity);

            Thread clientThread = new Thread(new ClientSession(host, port, message));
            clientThread.setName("Client: " + clientId);
            clientThread.setDaemon(false);
            clientThread.start();
        });
    }

}
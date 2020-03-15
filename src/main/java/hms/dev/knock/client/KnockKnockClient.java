package hms.dev.knock.client;

import java.util.stream.IntStream;

public class KnockKnockClient {
    public static void main(String[] args) {

        final int NUMBER_OF_CLIENTS = 5;
        final String MESSAGE_TEMPLATE = "Message from client %d";
        String host = "localhost";
        int port = 8080;

        IntStream.range(0, NUMBER_OF_CLIENTS).forEach(clientId -> {
            String message = String.format(MESSAGE_TEMPLATE, clientId);
            Thread clientThread = new Thread(new ClientSession(host, port, message));
            clientThread.setName("Client: " + clientId);
            clientThread.setDaemon(false);
            clientThread.start();
        });
    }

}
package hms.dev.knock.server;

import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {

        int portNumber = 8080;
        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread clientHandlingThread = new Thread(new ServerSession(clientSocket));
            clientHandlingThread.start();
        }
    }

}

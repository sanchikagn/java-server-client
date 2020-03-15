package hms.dev.knock;

import hms.dev.util.LogUtil;

import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {

        int portNumber = 8080;
        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread clientHandlingThread = new Thread(() -> acceptClientConnection(clientSocket));
            clientHandlingThread.start();
        }
    }

    private static void acceptClientConnection(Socket clientSocket) {
        try (
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine = in.readLine();
            String outputLine;

            if (inputLine != null) {
                LogUtil.logMessage("Client: " + inputLine);
                outputLine = inputLine.toUpperCase();
                out.println(outputLine);
                LogUtil.logMessage("Server:" + outputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

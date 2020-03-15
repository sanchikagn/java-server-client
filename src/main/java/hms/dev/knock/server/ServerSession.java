package hms.dev.knock.server;

import hms.dev.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSession implements Runnable {

    private final Socket clientSocket;

    public ServerSession(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
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
                LogUtil.logMessage("Server: " + outputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

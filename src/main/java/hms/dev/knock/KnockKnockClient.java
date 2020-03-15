package hms.dev.knock;

import hms.dev.util.LogUtil;

import java.io.*;
import java.net.*;

public class KnockKnockClient {
    public static void main(String[] args) {

        final int NUMBER_OF_CLIENTS = 5;

        for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
            final int clientId = i;
            Thread clientThread = new Thread(() -> initiateClientSession(clientId));
            clientThread.setName("Client: " + clientId);
            clientThread.setDaemon(false);
            clientThread.start();
        }
    }

    private static void initiateClientSession(int clientId) {
        String hostName = "localhost";
        int portNumber = 8080;

        LogUtil.logMessage("Initiated client session for: " + clientId);
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()))
        ) {
            String fromServer;
            String fromUser;

            fromUser = "Testing client[" + clientId + "]";
            LogUtil.logMessage(fromUser);
            out.println(fromUser);
            fromServer = in.readLine();
            LogUtil.logMessage("Server for client[" + clientId + "]: " + fromServer);

        } catch (UnknownHostException e) {
            LogUtil.logMessage("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            LogUtil.logMessage("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
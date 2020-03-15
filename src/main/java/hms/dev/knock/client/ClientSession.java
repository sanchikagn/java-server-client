package hms.dev.knock.client;

import hms.dev.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSession implements Runnable {

    private final String host;
    private final int port;
    private final String message;

    public ClientSession(String host, int port, String message) {
        this.host = host;
        this.port = port;
        this.message = message;
    }

    @Override
    public void run() {

        try (
                Socket kkSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()))
        ) {
            String fromServer;
            String fromUser;

            fromUser = message;
            LogUtil.logMessage(fromUser);
            out.println(fromUser);
            fromServer = in.readLine();
            LogUtil.logMessage("From server: " + fromServer);

        } catch (UnknownHostException e) {
            LogUtil.logMessage("Don't know about host " + host);
            System.exit(1);
        } catch (IOException e) {
            LogUtil.logMessage("Couldn't get I/O for the connection to " + host);
            System.exit(1);
        }
    }
}

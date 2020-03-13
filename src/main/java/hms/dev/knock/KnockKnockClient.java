package hms.dev.knock;

import hms.dev.util.LogUtil;

import java.io.*;
import java.net.*;

public class KnockKnockClient {
    public static void main(String[] args) {

        String hostName = "localhost";
        int portNumber = 8080;

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()))
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                LogUtil.logMessage("Server: " + fromServer);
                if (fromServer.equals("BYE."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    LogUtil.logMessage("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
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
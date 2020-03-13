package hms.dev.knock;

import hms.dev.util.LogUtil;

import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) {

        int portNumber = 8080;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))
        ) {

            String inputLine, outputLine;

            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);
            LogUtil.logMessage("A client connected");

            while ((inputLine = in.readLine()) != null) {
                LogUtil.logMessage("Client: " + inputLine);
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                LogUtil.logMessage("Server:" + outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

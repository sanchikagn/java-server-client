package hms.dev.knock.server;

import hms.dev.knock.server.entity.api.ProductResponse;
import hms.dev.knock.server.handler.ApiRequestHandler;
import hms.dev.knock.server.service.ProductDetailService;
import hms.dev.util.LogUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
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
                CloseableHttpClient httpClient = HttpClients.createDefault();
                ProductDetailService productDetailService = new ProductDetailService(
                        new ApiRequestHandler(httpClient));

                ProductResponse response = productDetailService.getDetails(inputLine);
                outputLine = String.valueOf(response);
                out.println(outputLine);
                LogUtil.logMessage("Server: " + outputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

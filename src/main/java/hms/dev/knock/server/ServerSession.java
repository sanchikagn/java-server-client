package hms.dev.knock.server;

import hms.dev.knock.server.entity.Inventory;
import hms.dev.knock.server.entity.Product;
import hms.dev.knock.server.entity.api.ProductResponse;
import hms.dev.knock.server.handler.ApiRequestHandler;
import hms.dev.knock.server.service.ProductDetailService;
import hms.dev.util.LogUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServerSession implements Runnable {

    private final Socket clientSocket;
    private final ExecutorService executorService;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private ProductDetailService productDetailService = new ProductDetailService(
            new ApiRequestHandler(httpClient));

    public ServerSession(Socket clientSocket, ExecutorService executorService) {
        this.clientSocket = clientSocket;
        this.executorService = executorService;
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
                outputLine = fetchProductInfo(inputLine);

                out.println(outputLine);
                LogUtil.logMessage("Server: " + outputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private String fetchProductInfo(String inputLine) {
        try {
            String outputLine;
            String productId;
            int orderedQty;
            int availableQty;

            String[] orderDetails = inputLine.split("\\|");
            if (orderDetails.length != 2) {
                return ProductResponse.INVALID_REQUEST.toString();
            }
            productId = orderDetails[0];
            orderedQty = Integer.parseInt(orderDetails[1]);

            ProductClient productClient = new ProductClient(productId, productDetailService);
            Future<?> productInfo = executorService.submit(productClient);
            productInfo.get();
            Product product = productClient.getProduct();
            LogUtil.logMessage("Fetched product data: "+ product);

            Inventory inventory = productDetailService.getInventoryById(productId);
            LogUtil.logMessage("Fetched inventory: " + inventory);

            availableQty = inventory.getInventoryCount();
            ProductResponse response = isOrderedQuantityAvailable(orderedQty, availableQty);
            outputLine = "Your " + product.getName() +" order is " + response.toString();
            return outputLine;
        } catch (Exception e) {
            LogUtil.logMessage("Error occurred while checking order: " + e);
            return ProductResponse.NOT_FOUND.toString();
        }
    }

    private ProductResponse isOrderedQuantityAvailable(int orderedQty, int availableQty) {
        ProductResponse response;
        if (availableQty >= orderedQty) {
            response = ProductResponse.AVAILABLE;
        } else {
            response = ProductResponse.OUT_OF_STOCK;
        }
        return response;
    }

    static class ProductClient implements Runnable {

        private final String id;
        private final ProductDetailService productDetailService;
        private Product product;

        public ProductClient(String id, ProductDetailService productStoreClient) {
            this.id = id;
            this.productDetailService = productStoreClient;
        }

        public Product getProduct() {
            return product;
        }

        @Override
        public void run() {
            try{
                product = productDetailService.getProductById(id);
            } catch (Exception e) {
                LogUtil.logMessage(e.getMessage());
            }
        }
    }
}

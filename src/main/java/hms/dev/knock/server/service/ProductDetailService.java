package hms.dev.knock.server.service;

import hms.dev.knock.server.entity.Inventory;
import hms.dev.knock.server.entity.Product;
import hms.dev.knock.server.entity.api.ProductResponse;
import hms.dev.knock.server.handler.ApiConfig;
import hms.dev.knock.server.handler.ApiRequestHandler;
import hms.dev.util.LogUtil;

public class ProductDetailService {
    private final ApiRequestHandler apiRequestHandler;

    public ProductDetailService(ApiRequestHandler apiRequestHandler) {
        this.apiRequestHandler = apiRequestHandler;
    }

    public Product getProductById(String productId) {
        String productDetails = apiRequestHandler.get(ApiConfig.getProductUrl(productId));
        String[] fields = productDetails.split(",");
        return new Product(fields[0], fields[1]);
    }

    public Inventory getInventoryById(String productId) {
        String inventoryDetails = apiRequestHandler.get(ApiConfig.getInventoryUrl(productId));
        String[] inventory = inventoryDetails.split(",");
        int availableQty = Integer.parseInt(inventory[1]);
        return new Inventory(productId, availableQty);
    }

    public ProductResponse getDetails(String details) {
        try {
            String[] orderDetails = details.split("\\|");
            if (orderDetails.length != 2) {
                return ProductResponse.INVALID_REQUEST;
            }
            String productId = orderDetails[0];
            int orderedQty = Integer.parseInt(orderDetails[1]);
            Product product = getProductById(productId);

            Inventory inventory = getInventoryById(productId);
            int availableQty = inventory.getInventoryCount();

            LogUtil.logMessage(String.format("%s | Available Qty: %d | Ordered Qty: %d",
                    product.toString(), availableQty, orderedQty));

            return isOrderedQuantityAvailable(orderedQty, availableQty);
        } catch (Exception e) {
            LogUtil.logMessage("Error occurred while checking order: " + e);
            return ProductResponse.NOT_FOUND;
        }
    }

    private ProductResponse isOrderedQuantityAvailable(int orderedQty, int availableQty) {
        if (availableQty >= orderedQty) {
            return ProductResponse.AVAILABLE;
        } else {
            return ProductResponse.OUT_OF_STOCK;
        }
    }
}

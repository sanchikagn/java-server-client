package hms.dev.knock.server.handler;

public class ApiConfig {
    private static final String PRODUCT_STORE_BASE_URL = "http://127.0.0.1:5080";
    private static final String PRODUCT_RESOURCE_PATH = "/products/id/%s";
    private static final String INVENTORY_RESOURCE_PATH = "/inventory/id/%s";

    public static String getProductUrl(String productId) {
        return String.format(PRODUCT_STORE_BASE_URL + PRODUCT_RESOURCE_PATH, productId);
    }

    public static String getInventoryUrl(String productId) {
        return String.format(PRODUCT_STORE_BASE_URL + INVENTORY_RESOURCE_PATH, productId);
    }
}

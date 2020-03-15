package hms.dev.knock.server.entity;

public class Inventory {
    private String productId;
    private int inventoryCount;

    public Inventory(String productId, int inventoryCount) {
        this.productId = productId;
        this.inventoryCount = inventoryCount;
    }

    public String getProductId() {
        return productId;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }
}

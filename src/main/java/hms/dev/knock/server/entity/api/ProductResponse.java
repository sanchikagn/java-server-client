package hms.dev.knock.server.entity.api;

public enum ProductResponse {
    AVAILABLE("Available"),
    OUT_OF_STOCK("Product out of stock"),
    NOT_FOUND("Product not found"),
    INVALID_REQUEST("Request is invalid");

    private final String message;

    ProductResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

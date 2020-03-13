package hms.dev.restaurant;

import hms.dev.util.LogUtil;

import java.util.stream.IntStream;

public class RestaurantCounter {

    public static void main(String[] args) {
        final int NUMBER_OF_ORDERS = 10;

        IntStream.range(0, NUMBER_OF_ORDERS).forEach(order -> {
            Thread orderProcessingThread = new Thread(() -> {
                Order orderFromCustomer = new Order();
                try {
                    orderFromCustomer.getOrder();
                    orderFromCustomer.fetchItems();
                    orderFromCustomer.calculateBill();
                    orderFromCustomer.charge();
                } catch (InterruptedException e) {
                    LogUtil.logMessage(e.getMessage());
                }
            });
            orderProcessingThread.setName("Customer " + order);
            orderProcessingThread.setDaemon(false);
            orderProcessingThread.start();
        });

        LogUtil.logMessage("End of main()");
    }
}

package hms.dev.restaurant;

import hms.dev.util.LogUtil;

import java.util.concurrent.TimeUnit;

public class Order {
    public Order() {
    }

    public void getOrder() throws InterruptedException {
        LogUtil.logMessage("Getting the customer order");
        TimeUnit.SECONDS.sleep(3);
    }

    public void fetchItems() throws InterruptedException {
        LogUtil.logMessage("Fetching the items for the order");
        TimeUnit.SECONDS.sleep(2);
    }

    public void calculateBill() throws InterruptedException {
        LogUtil.logMessage("Calculating the bill");
        TimeUnit.SECONDS.sleep(3);
    }

    public void charge() throws InterruptedException {
        LogUtil.logMessage("Charging the customer for the bill");
        TimeUnit.SECONDS.sleep(2);
    }
}

package hms.dev.util;

public class LogUtil {

    public static void logMessage(String message) {
        System.out.format("[%s][%s][%s][%s]\n", System.currentTimeMillis(),
                Thread.currentThread().getName(),
                Thread.currentThread().isDaemon(), message);
    }
}

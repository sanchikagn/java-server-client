package hms.dev.knock.server;

import hms.dev.knock.server.manager.TaskManager;
import hms.dev.util.LogUtil;

import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class KnockKnockServer {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10,
            r -> new Thread(r, "server"));
    private final int port;

    public KnockKnockServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {

        int port = 8080;
        TaskManager<Socket> taskList = new TaskManager<>();

//        IntStream.range(0, 3).mapToObj(processorNo -> {
//            Thread processorThread = new Thread(new RequestProcessor(taskList));
//            processorThread.setName("RequestProcessor: " + processorNo);
//            processorThread.setDaemon(false);
//            return processorThread;
//        }).forEach(Thread::start);

        KnockKnockServer server = new KnockKnockServer(port);
        server.init();
        LogUtil.logMessage("Server started on port: " + port);
    }

    private void init() {;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ServerSession(clientSocket, executorService));
            }
        } catch (IOException e) {
            LogUtil.logMessage("Exception occurred while waiting client socket");
            LogUtil.logMessage(e.getMessage());
        }
    }

    static class RequestProcessor implements Runnable {

        private final TaskManager<Socket> taskList;

        public RequestProcessor(TaskManager<Socket> taskList) {
            this.taskList = taskList;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket client = taskList.removeWaiting();
//                    new ServerSession(client).run();
                } catch (InterruptedException e) {
                    LogUtil.logMessage("Exception occurred while waiting client socket");
                    LogUtil.logMessage(e.getMessage());
                }
            }
        }
    }
}

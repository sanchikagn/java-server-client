package hms.dev.knock.server.manager;

import hms.dev.util.LogUtil;

import java.util.LinkedList;

public class TaskManager<S> {

    private final LinkedList<S> taskList = new LinkedList<S>();

    public void submitTask(S socket) {
        LogUtil.logMessage("New request: " + socket);

        synchronized (taskList) {
            taskList.add(socket);
            taskList.notifyAll();
        }
    }

    public S removeWaiting() throws InterruptedException {
        synchronized (taskList) {
            while (taskList.isEmpty()) {
                taskList.wait();
            }
            LogUtil.logMessage("Notified ...");
            taskList.notifyAll();
            return taskList.remove();
        }
    }
}

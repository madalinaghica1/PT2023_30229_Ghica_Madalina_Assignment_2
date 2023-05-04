package Model;

import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private Integer serverId;
    private List<Task> tasks;
    private Integer waitingPeriod;
    private Integer maxTasksPerQueue;

    public Server(Integer maxTasksPerQueue, Integer serverId) {
        this.maxTasksPerQueue = maxTasksPerQueue;
        this.serverId = serverId;
        this.tasks = new ArrayList<>();
        this.waitingPeriod = 0;
    }

    public Boolean taskListNotFull() {
        return tasks.size() < maxTasksPerQueue;
    }

    public Integer getServerId() {
        return serverId;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod;
    }

    public Integer getQueueSize() {
        return tasks.size();
    }

    public void addTask(Task newTask) {
        if (tasks.size() < maxTasksPerQueue) {
            tasks.add(newTask);
            waitingPeriod += newTask.getServiceTime();
        }
    }
   // În fiecare iterație a buclei, firul de execuție așteaptă 1 secundă folosind metoda Thread.sleep(1000)
   // și verifică dacă există sarcini (tasks) într-o coadă (tasks). Dacă există cel puțin o sarcină în coadă,
   // firul de execuție ia prima sarcină din coadă, o procesează timp de o unitate de timp prin scăderea cu 1
   // a timpului de serviciu al sarcinii (serviceTime) și decrementarea cu 1 a perioadei de așteptare (waitingPeriod).
   // Dacă timpul de serviciu al sarcinii devine 0, aceasta este eliminată din coadă prin apelarea metodei remove(0) pe lista de sarcini.
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tasks.size() > 0) {
                Task currentTask = tasks.get(0);
                currentTask.setServiceTime(currentTask.getServiceTime() - 1);
                waitingPeriod--;
                if (currentTask.getServiceTime() == 0) {
                    tasks.remove(0);
                }
            }
        }
    }

    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }
}

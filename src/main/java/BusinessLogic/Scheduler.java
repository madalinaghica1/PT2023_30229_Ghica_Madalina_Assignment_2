package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler {
    private List<Server> servers = new ArrayList<>();
    private ExecutorService executorService;
    private Strategy strategy;

    public Scheduler(Integer maxNoServers, Integer maxTasksPerServer, SelectionPolicy selectionPolicy) {
        executorService = Executors.newFixedThreadPool(maxNoServers);
        for (Integer i = 0; i < maxNoServers; i++) {
            servers.add(new Server(maxTasksPerServer, i));
            executorService.execute(servers.get(i));
        }
        changeStrategy(selectionPolicy);
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void add1Task(Task t) {
        strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }

}

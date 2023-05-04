package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    public void addTask (List<Server> servers, Task t)
    {
        int min = 0;
        int id = -1;
        for(int i=0;i<servers.size();i++){
            if (min==0) {
                if (servers.get(i).taskListNotFull()) {
                    min = servers.get(i).getQueueSize();
                    id = i;
                }
            } else if(min>servers.get(i).getQueueSize())
            {
                min=servers.get(i).getQueueSize();
                id=i;
            }
    }
        servers.get(id).addTask(t);
}
}
package BusinessLogic;
import Model.Server;
import Model.Task;

import java.util.List;


public class ConcreteStrategyTime implements Strategy {
    public void addTask (List<Server> servers, Task t)
    {
        int min = 0;
        int id = 0;
        for(int i=0;i<servers.size();i++) {
            if (min==0) {
                if (servers.get(i).taskListNotFull()) {
                    id = i;
                    min = servers.get(i).getWaitingPeriod();

                }
            } else if(servers.get(i).taskListNotFull()&&min>servers.get(i).getWaitingPeriod())
                {
                    id=i;
                    min=servers.get(i).getWaitingPeriod();

                }
        }
        servers.get(id).addTask(t);
    }
}

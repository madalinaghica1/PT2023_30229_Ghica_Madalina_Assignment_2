package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.*;
import java.util.*;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    Integer timeLimit;
    Integer maxProcessingTime;
    Integer minProcessingTime;
    Integer minArrivalTime;
    Integer maxArrivalTime;
    Integer noOfServers;
    Integer noOfClients;
    SelectionPolicy selectionPolicy=SelectionPolicy.SHORTEST_TIME;

    public SimulationManager(){

        this.frame=new SimulationFrame();
       setValues(frame.getInitValues());
        this.scheduler= new Scheduler(noOfServers,5,selectionPolicy);
        generatedTasks=Collections.synchronizedList(new ArrayList<>());
        generateNRandomTasks();
    }

    public void generateNRandomTasks(){
        Random random=new Random();
        int arrival;
        int service;
        for (Integer i=0;i<noOfClients;i++){

            arrival= (int) ((Math.random() * (maxProcessingTime -minProcessingTime)) +minProcessingTime);
            service= (int) ((Math.random() * (maxArrivalTime - minArrivalTime)) + minArrivalTime);
            generatedTasks.add(new Task(service,arrival));
        }
        generatedTasks.sort((Comparator.comparing(Task::getArrivalTime)));
        for (int i=0;i<noOfClients;i++)
            generatedTasks.get(i).setNumar(i);
    }
    private String printStack(){
        String stack="Waiting clients: ";
        for (Task i:generatedTasks)
        {
            stack=stack+ "("+i.getNumar()+","+i.getArrivalTime()+","+i.getServiceTime()+");";
        }
        stack=stack+"\n";
        for (Server i:scheduler.getServers()){
            stack=stack+"Queue "+(i.getServerId()+1)+": ";
            if (i.getQueueSize()==0)
                stack=stack+"closed";
            else {
                Task[] tasks=i.getTasks();
                for(int j=0;j<i.getQueueSize();j++)
                    stack=stack+"("+tasks[j].getNumar()+","+tasks[j].getArrivalTime()+","+tasks[j].getServiceTime()+");";

            }
            stack=stack+"\n";
        }
        return stack;
    }
    @Override
    public void run(){
        Integer currentTime=0;
        String display;
        String fin=" ";
        while (currentTime<timeLimit)
        {
            //Această buclă este folosită pentru a verifica dacă există procese noi care trebuie planificate,
            // adică procese care au sosit în sistem și care trebuie să fie asignate la un procesor.
            int i=0;
            while(i==0)
            {
                if (generatedTasks.size()!=0){
                    Task j=generatedTasks.get(0);
                    if (j.getArrivalTime()==currentTime)
                    {
                        scheduler.add1Task(j);
                        generatedTasks.remove(j);
                    }
                    else if (j.getArrivalTime()>currentTime)
                    {
                        i=-1;
                    }
                }

                if (generatedTasks.size()==0)
                    i=-1;
               // Dacă nu există niciun proces nou, bucla se va opri și programul va aștepta
                // până când va trece o secundă înainte de a începe următoarea iterare a buclei principale.
            }
            //Dacă există procese noi, primul proces din lista "generatedTasks" este selectat și verificat dacă trebuie planificat.
            // Dacă timpul de sosire al procesului este egal cu timpul curent, atunci procesul este asignat
            // la un procesor prin apelarea metodei "dispatchTask" a obiectului "scheduler".
            // Dacă procesul nu trebuie planificat încă, bucla se oprește și așteaptă până la următoarea iterare a buclei principale.
            display="Time "+ currentTime +"\n"+printStack();
            //După ce au fost verificate toate procesele din lista "generatedTasks", metoda "printStack()" este apelată pentru a afișa
            // starea actuală a planificatorului. Acest mesaj de afișare este adăugat la un șir de caractere "fin".
            // Apoi, mesajul este afișat la consolă și apoi trimis la un obiect "frame" pentru a fi afișat
            // într-o interfață grafică cu utilizatorul.
            fin=fin+display;
            System.out.println(display);
           frame.refreshFrame(display);
            currentTime++;
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // În cele din urmă, timpul curent este incrementat cu 1 și bucla principală
            // este pusă să aștepte o secundă înainte de a trece la următoarea iterare a buclei.
        }
        try (FileWriter fileWriter = new FileWriter("log.txt"))
        {
            fileWriter.write(fin);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

    }
    public void setValues(List<Integer> list){
        this.noOfClients=list.get(0);
        this.noOfServers=list.get(1);
        this.timeLimit=list.get(2);
        this.minArrivalTime=list.get(3);
        this.maxArrivalTime=list.get(4);
        this.minProcessingTime=list.get(5);
        this.maxProcessingTime=list.get(6);
    }
    public static void main(String[] args){
        SimulationManager gen= new SimulationManager();
        Thread t=new Thread(gen);
        t.start();
    }
}
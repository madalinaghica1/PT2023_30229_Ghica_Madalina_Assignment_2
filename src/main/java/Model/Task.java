package Model;

public class Task {
    private int arrivingTime;
    private int serviceTime;
    private int numar;
    public Task(int arrivingTime, int serviceTime) {
        this.arrivingTime = arrivingTime;
        this.serviceTime = serviceTime;

    }
    public int getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public int getArrivalTime() {
        return arrivingTime;
    }
    public int getNumar() {
        return numar;
    }
    public void setNumar(int numar)
    {
        this.numar=numar;
    }


}



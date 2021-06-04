package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    //colui che viene osservato
    private List<Observer> observerList= new ArrayList<>();

    public void addObserver(Observer observer){
        observerList.add(observer);
    }

    public void removeObserver(Observer observer){
        observerList.remove(observer);

    }

    public void notifyAllObserver(Update message){
        for(Observer observer: observerList){
            observer.update(message);
        }
    }

    public int count(){
        return  observerList.size();
    }


}

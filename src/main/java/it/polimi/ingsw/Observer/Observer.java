package it.polimi.ingsw.Observer;


import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;

public interface Observer {
    //colui che osserva

    /**
     * this methos is used to apply a comunicated State update
     * @param message information about Update
     */
    void update(Update message);
}

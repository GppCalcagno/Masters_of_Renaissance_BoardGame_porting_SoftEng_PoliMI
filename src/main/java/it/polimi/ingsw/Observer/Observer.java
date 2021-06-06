package it.polimi.ingsw.Observer;


import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;

public interface Observer {
    //colui che osserva

    /**
     * this method is used to apply a communicated state update
     * @param message information about Update
     */
    void update(Update message);
}

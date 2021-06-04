package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.View.ViewInterface;
import it.polimi.ingsw.model.exceptions.EndGameException;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = -898685728584201368L;

    private String nickname;

    public Message(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public abstract void action(GameController gameController);

}
package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.View.ViewInterface;
import it.polimi.ingsw.model.exceptions.EndGameException;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = -898685728584201368L;

    private String nickname;

    private MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }


    public  void action(GameController gameController) {}

    public  void update(PlayerBoard playerBoard, ViewInterface view){}

}
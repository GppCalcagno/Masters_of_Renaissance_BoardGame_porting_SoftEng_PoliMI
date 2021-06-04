package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serializable;

public abstract class Update implements Serializable {
    private static final long serialVersionUID = -1439919651303694140L;
    private String nickname;


    public Update(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public abstract void update(ViewInterface view);


}

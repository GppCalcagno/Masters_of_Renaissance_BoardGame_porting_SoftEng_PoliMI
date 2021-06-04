package it.polimi.ingsw.Network.Message.ServerUpdate;


import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateError extends Update {
    private static final long serialVersionUID = 84642072058199835L;

    String errorType;


    public UpdateError(String nickname, String errorType) {
        super(nickname);
        this.errorType=errorType;
    }

    @Override
    public void update(ViewInterface view){
        view.onUpdateError(errorType);
    }
}

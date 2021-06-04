package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageExtractionMarbles extends Message {
    private static final long serialVersionUID = 8042579921096981359L;

    private char colrowextract;

    private int numextract;


    public MessageExtractionMarbles(String nickname, char colrowextract, int numextract) {
        super(nickname);
        this.colrowextract = colrowextract;
        this.numextract = numextract;
    }


    @Override
    public void action(GameController gameController) {
        gameController.extractionMarble(colrowextract,numextract);
    }
}

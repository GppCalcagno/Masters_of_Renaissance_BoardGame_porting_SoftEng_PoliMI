package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageManageMarbles extends Message {
    private static final long serialVersionUID = 5253235709158532912L;

    private char structure;
    private int row;
    private String changeFromWhite;

    public MessageManageMarbles(String nickname, char structure, int row, String changeFromWhite) {
        super(nickname);
        this.structure = structure;
        this.row = row;
        this.changeFromWhite = changeFromWhite;
    }


    @Override
    public void action(GameController gameController) {
        gameController.manageMarble(structure,row, changeFromWhite);
    }
}

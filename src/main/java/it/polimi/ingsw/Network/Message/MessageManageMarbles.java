package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

public class MessageManageMarbles extends Message{
    private static final long serialVersionUID = 5253235709158532912L;

    private int structure;
    private int row;
    private String changeFromWithe;

    public MessageManageMarbles(String nickname, int structure, int row, String changeFromWithe) {
        super(nickname, MessageType.MANAGEMARBLES);
        this.structure = structure;
        this.row = row;
        this.changeFromWithe = changeFromWithe;
    }

    public int getRow() {
        return row;
    }

    public int getStructure() {
        return structure;
    }

    public String getChangeFromWithe() {
        return changeFromWithe;
    }

    @Override
    public void action(GameController gameController) {

    }
}

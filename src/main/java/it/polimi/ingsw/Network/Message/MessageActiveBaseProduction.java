package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

import java.util.Map;

public class MessageActiveBaseProduction extends Message{
    private static final long serialVersionUID = 7072915502239427238L;

    private String wanted;
    private String resources1;
    private String resources2;

    private char structure1;
    private char structure2;

    public MessageActiveBaseProduction(String nickname, String wanted, char structure1, String resources1, char structure2, String resources2) {
        super(nickname, MessageType.ACTIVESBASEPRODUCTION);
        this.wanted = wanted;
        this.resources1 = resources1;
        this.resources2 = resources2;
        this.structure1 = structure1;
        this.structure2 = structure2;
    }

    public char getStructure1() {
        return structure1;
    }

    public char getStructure2() {
        return structure2;
    }

    public String getResources1() {
        return resources1;
    }

    public String getResources2() {
        return resources2;
    }

    public String getWanted() {
        return wanted;
    }

    @Override
    public void action(GameController gameController) {

    }
}

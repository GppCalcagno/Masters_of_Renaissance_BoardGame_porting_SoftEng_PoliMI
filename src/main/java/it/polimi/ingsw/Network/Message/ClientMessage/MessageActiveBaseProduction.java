package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageActiveBaseProduction extends Message {
    private static final long serialVersionUID = 7072915502239427238L;

    private String wanted;
    private String resources1;
    private String resources2;

    private char structure1;
    private char structure2;

    public MessageActiveBaseProduction(String nickname, String wanted, char structure1, String resources1, char structure2, String resources2) {
        super(nickname);
        this.wanted = wanted;
        this.resources1 = resources1;
        this.resources2 = resources2;
        this.structure1 = structure1;
        this.structure2 = structure2;
    }

    @Override
    public void action(GameController gameController) {
        gameController.activeBaseProduction(structure1,resources1,structure2,resources2,wanted);
    }
}

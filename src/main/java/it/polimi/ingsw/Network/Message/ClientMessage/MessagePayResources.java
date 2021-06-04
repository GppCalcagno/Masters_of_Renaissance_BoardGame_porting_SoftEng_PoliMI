package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

import java.util.Map;

public class MessagePayResources extends Message {
    private static final long serialVersionUID = -4554480045749065772L;

    private Map<String,Integer> warehouse;
    private Map<String,Integer> strongBox;
    private Map<String,Integer> extraChest;

    public MessagePayResources(String nickname, Map<String, Integer> warehouse, Map<String, Integer> strongBox, Map<String, Integer> extraChest) {
        super(nickname);
        this.warehouse = warehouse;
        this.strongBox = strongBox;
        this.extraChest = extraChest;
    }

    @Override
    public void action(GameController gameController) {
        gameController.payResources(warehouse,strongBox,extraChest);
    }
}


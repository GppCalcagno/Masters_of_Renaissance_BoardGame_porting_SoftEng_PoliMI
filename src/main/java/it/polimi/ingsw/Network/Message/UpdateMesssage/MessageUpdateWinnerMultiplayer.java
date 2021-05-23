package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class MessageUpdateWinnerMultiplayer extends Message {
    private static final long serialVersionUID = 2509383771998500986L;

    private String playerWinner;

    private Map<String, Integer> playersPoints;

    public MessageUpdateWinnerMultiplayer(String playerWinner, Map<String, Integer> playersPoints) {
        super("server", MessageType.UPDATEWINNERMULTIPLAYER);
        this.playerWinner = playerWinner;
        this.playersPoints = playersPoints;
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.updateWinner(playerWinner,playersPoints);
        view.onUpdateWinnerMultiplayer();
        view.onDisconnect();
    }
}

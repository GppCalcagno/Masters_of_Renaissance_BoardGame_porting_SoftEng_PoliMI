package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.Map;

public class UpdateWinnerMultiplayer extends Update {
    private static final long serialVersionUID = 2509383771998500986L;

    private String playerWinner;

    private Map<String, Integer> playersPoints;

    public UpdateWinnerMultiplayer(String playerWinner, Map<String, Integer> playersPoints) {
        super("server");
        this.playerWinner = playerWinner;
        this.playersPoints = playersPoints;
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().updateWinner(playerWinner,playersPoints);
        view.onUpdateWinnerMultiplayer();
    }
}

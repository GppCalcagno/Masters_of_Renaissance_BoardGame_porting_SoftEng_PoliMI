package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;
import java.util.Map;

public class UpdateOtherPlayer extends Update {
    private String OtherPlayerName;
    private String[][] warehouse;
    private Map<String,Integer> extrachest;
    private Map<String,Integer> strongbox;
    private List<String> leaderCards;
    private String [][] slotDevCard;

    public UpdateOtherPlayer(String nickname, String OtherPlayerName, String[][] warehouse, Map<String, Integer> extrachest, Map<String, Integer> strongbox, List<String> leaderCards, String[][] slotDevCard) {
        super(nickname);
        this.OtherPlayerName = OtherPlayerName;
        this.warehouse = warehouse;
        this.extrachest = extrachest;
        this.strongbox = strongbox;
        this.leaderCards = leaderCards;
        this.slotDevCard = slotDevCard;
    }

    @Override
    public void update(ViewInterface view){
        view.getPlayerBoard().setOtherPlayer(OtherPlayerName,warehouse,extrachest,strongbox,leaderCards,slotDevCard);
        view.showOtherPlayer();
    }
}

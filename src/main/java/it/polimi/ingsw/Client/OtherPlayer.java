package it.polimi.ingsw.Client;

import java.util.List;
import java.util.Map;

/**
 * This class allows in the game to show the game's state of other players
 */

public class OtherPlayer {
    private String name;
    private String[][] warehouse;
    private Map<String,Integer> extrachest;
    private Map<String,Integer> strongbox;
    private List<String> leaderCards;
    private String [][] slotDevCard;

    public OtherPlayer(String name, String[][] warehouse, Map<String, Integer> extrachest, Map<String, Integer> strongbox, List<String> leaderCards, String[][] slotDevCard) {
        this.name = name;
        this.warehouse = warehouse;
        this.extrachest = extrachest;
        this.strongbox = strongbox;
        this.leaderCards = leaderCards;
        this.slotDevCard = slotDevCard;
    }

    public String getName() {
        return name;
    }

    public String[][] getWarehouse() {
        return warehouse;
    }

    public Map<String, Integer> getExtrachest() {
        return extrachest;
    }

    public Map<String, Integer> getStrongbox() {
        return strongbox;
    }

    public List<String> getLeaderCards() {
        return leaderCards;
    }

    public String[][] getSlotDevCard() {
        return slotDevCard;
    }
}

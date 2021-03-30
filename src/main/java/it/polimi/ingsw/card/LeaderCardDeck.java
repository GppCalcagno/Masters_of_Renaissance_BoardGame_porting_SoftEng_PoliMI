package it.polimi.ingsw.card;

import it.polimi.ingsw.player.Player;

public class LeaderCardDeck {
    /**
     * This attribute is the total number of Leader Cards
     */
    private final int size = 16;

    /**
     * This attribute is a vector that contains the reference of all Leader Cards
     */
    private LeaderAction leaderCardVet [];

    /**
     * This is the constructor method
     * @param leaderCardVet a vector of LeaderAction
     */
    public LeaderCardDeck(LeaderAction[] leaderCardVet) {
        this.leaderCardVet = new LeaderAction[size];
    }

    /**
     * This method returns a Leader Card from the vector indicated from the index given by the Player
     * @param i index of the card
     * @return the chosen Leader Action
     */
    public LeaderAction getLeaderCardVet(int i) {
        return leaderCardVet[i];
    }

    /**
     * This method gives the chosen Leader Card to the Player
     * @param i index of the chosen Leader Card
     * @param player to which it is given the card
     */
    public void givetoPlayer (int i, Player player) {
        // to implement
    }
}

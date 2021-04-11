package it.polimi.ingsw.player;

import it.polimi.ingsw.card.DevelopmentCard;
import it.polimi.ingsw.card.leadereffect.ExtraProduction;
import it.polimi.ingsw.producible.Producible;
import it.polimi.ingsw.producible.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlotDevCards {

    /**
     * this is the matrix where the development cards are located
     */
    private DevelopmentCard[][] boardDevCards;

    /**
     * this buffer is for avoid the possibility that resources just producted can be used as resources needed
     * for the production
     */
    private List<Producible> buffer;

    /**
     * this is a list of optional that if the player hasn't the leader card that allows to discount the price
     * of productions aren't initialized
     */
    private ExtraProduction leaderCardEffect; //da rivedere
    Optional<ExtraProduction> optionalLeaderCardEffect = Optional.of(leaderCardEffect);

    /**
     * the constructor initialize the matrix as 3*3 as written in the rules even though the player can't
     * buy more than seven cards
     */
    public SlotDevCards() {
        boardDevCards = new DevelopmentCard[3][3];
        buffer = new ArrayList<Producible>();
    }

    /**
     * this method check if the player can buy a specific level card from the development card market
     * @return the maximum level of player's cards
     */
    public int maxLevelPurchase(){
        return 0;
    }

    /**
     * when player buy a development card this method insert the card in the boardCards
     * @param column the column where player wants put the card just bought in accord with the rules
     * @param card the card just bought
     */
    public void insertCards( int column, DevelopmentCard card){

    }

    /**
     * this method check if the card selected from the player to be used is on the top of the heap
     * @param card the card that player selected
     */
    public void checkUsage(DevelopmentCard card){

    }

    /**
     * constant for each board, is the base production
     */
    public void baseProduction(){

    }

    /**
     * this method call the card and start the production
     * @param Card the card whose production player want to start
     */
    public void cardProduction(DevelopmentCard Card){

    }

    /**
     * this method empty the buffer into the strongbox
     */
    public void emptyBuffer(){

    }


    public void addleaderCardEffect(Resources resources) {
    }
}

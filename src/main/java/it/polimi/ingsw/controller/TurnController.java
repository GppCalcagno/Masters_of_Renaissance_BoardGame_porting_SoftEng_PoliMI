package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.game.Game;

import java.io.IOException;

public class TurnController {

    private DevelopmentCard c;

    private Game game;

    public TurnController() throws IOException {
        game = new Game();
    }

    /**
     * this method called by the player allow to select from the DevCardDeck the card the player wants to buy
     * @param x the row
     * @param y the column
     * @return true if there are no errors
     */
    public boolean selectDevCard(int x, int y){
        try{
            c=game.getDevelopmentCardDeck().getDevCards(x,y);
        } catch (NullPointerException e){
            e.getMessage();
        } catch (IndexOutOfBoundsException eo){
            eo.getMessage();
        }
        if(game.getCurrentPlayer().getSlotDevCards().maxLevelPurchase(c) && c.getCost().checkResources(game.getCurrentPlayer())) return true;
        else return false;
    }

    /**
     * this method called by the player allow to insert the card just bought into it's SlotDevCards
     * @param y column for SlotDevCards
     * @return true if there are no error
     */
    public boolean insertCard(int y) {
        if (y >= 0 && y <= 2) {
            if (game.getCurrentPlayer().getSlotDevCards().insertCards(y, c)) return true;
            else return false;
        }
        else return false;
    }

    public Game getGame() {
        return game;
    }
}

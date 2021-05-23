package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;

import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.singleplayer.token.DiscardDevCards;
import it.polimi.ingsw.model.singleplayer.token.MoveOneAndMix;
import it.polimi.ingsw.model.singleplayer.token.MoveTwo;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LorenzoIlMagnifico {

    /** this is the faith traker of Lorenzo
     */
    private int blackCrossToken;

    /** these are the tokens that every turn, random, are played
     */
    private List<Tokens> tokensvet;

    /**
     * this is the development card deck that is initialized in the game
     */
    private DevCardsDeck developmentDeck;

    private Tokens currentToken;

    /** this is the constructor, it initialize the blackCrossToken = 0 because Lorenzo starts at the beginning
     * of the board as the other player
     * and tokensvet are 7 in accord whit the rules and shuffle them
     */
    public LorenzoIlMagnifico(DevCardsDeck developmentDeck){
        this.developmentDeck = developmentDeck;
        blackCrossToken=0;
        tokensvet = new ArrayList<>();
        tokensvet.add(new DiscardDevCards(ColorCard.BLUE));
        tokensvet.add(new DiscardDevCards(ColorCard.GREEN));
        tokensvet.add(new DiscardDevCards(ColorCard.PURPLE));
        tokensvet.add(new DiscardDevCards(ColorCard.PURPLE));
        tokensvet.add(new DiscardDevCards(ColorCard.YELLOW));
        tokensvet.add(new MoveOneAndMix());
        tokensvet.add(new MoveTwo());
        tokensvet.add(new MoveTwo());

        Collections.shuffle(tokensvet);
        currentToken = null;
    }

    /** this method active a tokens and increase the index i to the next time call an other token
     */
    public void drawTokens() throws ActiveVaticanReportException {
        currentToken= tokensvet.get(0);
        currentToken.effectTokens(this);
    }

    /**
     * this method allows the moveOneAndMix to set the i to the first position
     * @param index the index i
     */


    /**
     * the get for the tokensvet[]
     * @return array tokensvet[]
     */
    public List<Tokens> getTokensvet() {
        return tokensvet;
    }

    /** this method return the position of the blackcrosstoken
     * @return the blackcrosstoken
     */
    public int getFaithMarker(){
        return blackCrossToken;
    }

    /** this method increase the position of the blackcrosstoken
     */
    public void increaseFaithMarker(int quantity) throws ActiveVaticanReportException{
        this.blackCrossToken += quantity;
        if (blackCrossToken >8)  throw new ActiveVaticanReportException();

    }

    /**
     * a get of the DevCardsDeck
     * @return developmentDeck.
     */
    public DevCardsDeck getDevelopmentDeck() {
        return developmentDeck;
    }

    public Tokens getCurrentToken() {
        return currentToken;
    }
}
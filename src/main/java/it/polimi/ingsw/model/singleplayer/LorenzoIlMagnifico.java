package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;

import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.singleplayer.token.DiscardDevCards;
import it.polimi.ingsw.model.singleplayer.token.MoveOneAndMix;
import it.polimi.ingsw.model.singleplayer.token.MoveTwo;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LorenzoIlMagnifico {

    /** this is the faith traker of Lorenzo
     */
    private int blackCrossToken;

    private int i;
    /** these are the tokens that every turn, random, are played
     */
    private Tokens[] tokensvet;

    /**
     * this is the development card deck that is initialized in the game
     */
    private DevCardsDeck developmentDeck;

    /** this is the constructor, it initialize the blackCrossToken = 0 because Lorenzo starts at the beginning
     * of the board as the other player
     * and tokensvet are 7 in accord whit the rules and shuffle them
     */
    public LorenzoIlMagnifico(DevCardsDeck developmentDeck){
        this.developmentDeck = developmentDeck;
        blackCrossToken=0;
        tokensvet = new Tokens[7];
        tokensvet[0] = new DiscardDevCards(ColorCard.BLUE);
        tokensvet[1] = new DiscardDevCards(ColorCard.GREEN);
        tokensvet[2] = new DiscardDevCards(ColorCard.PURPLE);
        tokensvet[3] = new DiscardDevCards(ColorCard.YELLOW);
        tokensvet[4] = new MoveTwo();
        tokensvet[5] = new MoveTwo();
        tokensvet[6] = new MoveOneAndMix();

        List<Tokens> intList = Arrays.asList(tokensvet);
        Collections.shuffle(intList);
        intList.toArray(tokensvet);

    }

    /** this method active a tokens and increase the index i to the next time call an other token
     */
    public void drawTokens() throws IOException, NegativeQuantityExceptions, ActiveVaticanReportException {
        tokensvet[i].effectTokens(this);
        if(i<tokensvet.length) i++;
    }

    /**
     * this method allows the moveOneAndMix to set the i to the first position
     * @param i the index i
     */
    public void setI(int i){
        this.i= i;
    }

    /**
     * the get for the index i
     * @return i
     */
    public int getI() {
        return i;
    }

    /**
     * the get for the tokensvet[]
     * @return array tokensvet[]
     */
    public Tokens[] getTokensvet() {
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
        if (blackCrossToken == 8 || blackCrossToken == 16 || blackCrossToken == 24)  throw new ActiveVaticanReportException();

    }

    /**
     * a get of the DevCardsDeck
     * @return developmentDeck.
     */
    public DevCardsDeck getDevelopmentDeck() {
        return developmentDeck;
    }

}
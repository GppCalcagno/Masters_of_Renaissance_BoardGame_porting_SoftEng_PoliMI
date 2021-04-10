package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.singleplayer.token.Tokens;

public class LorenzoIlMagnifico {

    /**
     * this is the faith traker of Lorenzo
     */
    private int blackCrossToken;

    /**
     * these are the tokens that every turn, random, are played
     */
    private Tokens[] tokensvet;

    /**
     * this is the constructor, it initialize the blackCrossToken = 0 because Lorenzo starts at the beginning
     * of the board as the other player
     * and tokensvet are 7 in accord whit the rules
     */
    public LorenzoIlMagnifico(){
        blackCrossToken=0;
        tokensvet = new Tokens[7];
    }

    /**
     * this method active a tokens
     * @return the tokens activated
     */
    public Tokens drawTokens(){
        return tokensvet[0];
    }

    /**
     * this method return the position of the blackcrosstoken
     * @return the blackcrosstoken
     */
    public int getFaithMarker(){
        return blackCrossToken;
    }

    /**
     * this method increase the position of the blackcrosstoken
     */
    public void increaseFaithMarker(){

    }

}

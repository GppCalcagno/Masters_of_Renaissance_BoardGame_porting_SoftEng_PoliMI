package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.producible.Servants;
import it.polimi.ingsw.model.producible.Shields;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TurnController {
    private Game game;
    private boolean[] AlreadyActivedDevCard;
    private DevelopmentCard CurrentDevCardPurchase;
    private int currentDevCardProduction;
    private int numPlayersCount;

    /**
     * this is the controller of TurnController
     * @throws IOException
     */
    public TurnController() throws IOException {
        game = new Game();
        AlreadyActivedDevCard= new boolean[3];
        numPlayersCount = 0;
    }

    /**
     * This method verifies if the player's number decided from the first player is right
     * @param numPlayers int passed by the first player
     * @return true if numPlayers is < 5
     */
    public boolean VerifyNumPlayers (int numPlayers) throws IOException {
        if (numPlayers < 1 || numPlayers > 4)
            return false;

        else if (numPlayers == 1) {
            numPlayersCount = numPlayers;
            game = new SinglePlayerGame();
            return true;
        }
        else {
            numPlayersCount = numPlayers;
            game = new Game();
            return true;
        }
    }


    /**
     * this method called by the player allow to select from the DevCardDeck the card the player wants to buy
     * @param row the row
     * @param column the column
     * @return true if there are no errors
     */
    public boolean selectDevCard(int row, int column){
        try{
            CurrentDevCardPurchase =game.getDevelopmentCardDeck().getDevCards(row,column);
        } catch (NullPointerException e){ return false;}

        //controllo che il giocare abbia spazione nello SlotDevCard e che abbia le risorse necessarie
        return (game.getCurrentPlayer().getSlotDevCards().maxLevelPurchase(CurrentDevCardPurchase) && CurrentDevCardPurchase.getCost().checkResources(game.getCurrentPlayer()));
    }

    /**
     * this method called by the player allow to insert the card just bought into it's SlotDevCards
     * @param col column for SlotDevCards
     * @return true if there are no error
     */
    public boolean insertCard(int col) {
        if (col >= 0 && col <= 2) {
            return game.getCurrentPlayer().getSlotDevCards().insertCards(col, CurrentDevCardPurchase);
        }
        return false;
    }

    /**
     * this method is used to pay DevCard Purchase
     * @param WarehouseRes resources from WarehouseRes
     * @param StrongboxRes resources from StrongboxRes
     * @param ExtrachestMap resources from ExtrachestMap
     * @return true if the resources are correct for  DevCard Purchase
     */
    public boolean chooseResourcesForPurchase(Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap){
        //controllo se le risorse siano corrette
        if(!CurrentDevCardPurchase.getCost().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap)) return false;

        //elimino risorse dal giocatore
        return deleteRes(WarehouseRes,StrongboxRes,ExtrachestMap);
    }

    /**
     * this method called by the player allow to activate a leaderActionCard
     * @param pos refer which card must to be activated, 0 for the first, 1 for the second
     * @return true if the card is activated
     */
    public boolean activeLeaderAction(int pos){
        //controllo se posizione valida
        if(pos>1)return false;
        LeaderAction card= game.getCurrentPlayer().getLeaderActionBox().get(pos);

        //carta già attivata
        if(card.getActivated())return true;

        //controllo risorse
        if(!card.getCost().checkResources(game.getCurrentPlayer()))return false;
        //attivo carta
        card.setActivated();

        //potrei fare return true ma lo uso per un "ulteriore controllo"
        return card.getActivated();
    }

    /**
     * this method called by the player discard one of his leader action card to increase his faithmarker by one
     * @param pos which leader action card must to be discarded: 0 for the first, 1 for the second
     * @return true if the card is discarded
     */
    public boolean discardLeaderAction(int pos) throws GameFinishedException {
        int size = game.getCurrentPlayer().getLeaderActionBox().size();
        //verifico se indice corretto
        if(pos<size) return false;

            game.getCurrentPlayer().discardLeaderAction(pos);

        try {
            game.getCurrentPlayer().increasefaithMarker();
        } catch (ActiveVaticanReportException e) {
            //vedere il singleplayer
            game.getFaithTrack().checkPopeSpace(game.getPlayersList(),0);
        }
        return true;
    }


    /**
     * this method is called by client to active a DevCardProduction
     * @param col is the top item of the selected column of SlotDevCard
     * @return true if the player could active DevCardProduction
     */
    public boolean activeProductionDevCard(int col){
        if (AlreadyActivedDevCard[col])return false;

        DevelopmentCard card= game.getCurrentPlayer().getSlotDevCards().getDevCards(col);

        if( card.getCostProduction().checkResources(game.getCurrentPlayer()) && game.getCurrentPlayer().getSlotDevCards().checkUsage(card)){
            currentDevCardProduction=col;
            return true;
        }
        return false;

    }


    /**
     * this method is used to pay DevCardProduction: remove resources from the player and fill SlotDevCard_buffer
     * @param WarehouseRes resources from WarehouseRes
     * @param StrongboxRes resources from StrongboxRes
     * @param ExtrachestMap resources from ExtrachestMap
     * @return true if the resources are correct for  DevCardProduction
     */
    public boolean chooseResourcesForProduction(Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap){
        DevelopmentCard card= game.getCurrentPlayer().getSlotDevCards().getDevCards(currentDevCardProduction);

        //controlla se risorse corrispondono a costo carta
        if(card.getCostProduction().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap))return false;

        //elimino le risorse
        if(!deleteRes(WarehouseRes,StrongboxRes,ExtrachestMap)) return false;

        //assegno le risorse al buffer giocatore
        game.getCurrentPlayer().getSlotDevCards().cardProduction(card);

        AlreadyActivedDevCard[currentDevCardProduction]=true;
        return true;
    }

    /**
     * remove Resources from Player
     * @param WarehouseRes  warehouse selected resources
     * @param StrongboxRes strongbox selected resources
     * @param ExtrachestMap extrachest selected resources
     * @return true if all resources are deleted
     */
    public boolean deleteRes(Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap){

        //controlla se giocatore ha risorse
        if(!(game.getCurrentPlayer().checkListResources(WarehouseRes,StrongboxRes,ExtrachestMap))) return false;

        //warehouse
        for(String res: WarehouseRes.keySet()){
            int index=0;
            //qui ho hardcodato le risorse
            Resources[]  ResList={new Coins(),new Shields(), new Servants(), new Servants()};
            while(!ResList[index].toString().equals(res))index++;

            for(int j=0;j<WarehouseRes.get(res);j++)
                game.getCurrentPlayer().getWarehouse().delete(ResList[index]);
        }

        //strongbox
        for(String res: StrongboxRes.keySet()){
            try {
                game.getCurrentPlayer().getStrongbox().updateResources(res,-StrongboxRes.get(res));
            } catch (NegativeQuantityExceptions ignored) {}//ho controllato prima che il giocatore abbia le risorse
        }

        //extrachest
        for(String res: ExtrachestMap.keySet()){
            int Resindex=0;
            //qui ho hardcodato le risorse
            Resources[]  ResList={new Coins(),new Shields(), new Servants(), new Servants()};
            while(!ResList[Resindex].toString().equals(res))Resindex++;

            int ChestIndex=0;
            while(game.getCurrentPlayer().getWarehouse().getLeaderCardEffect().get(ChestIndex).getResources().toString().equals(res))ChestIndex++;
            //so che l'indice c'è perchè faccio controllo prima

            int old= game.getCurrentPlayer().getWarehouse().getLeaderCardEffect().get(ChestIndex).getnum();
            try {
                game.getCurrentPlayer().getWarehouse().getLeaderCardEffect().get(ChestIndex).updateResources(old -ExtrachestMap.get(res));
            } catch (OverflowQuantityExcepions | NegativeQuantityExceptions ignored) {}//non uso i catch perchè controllo prima le risorse
        }
        return true;
    }

    /**
     * This method translates the TakeMarbles message
     * @param colrowextract indicates the row or the column to extract
     * @param numextract    the column or row's number
     * @return true if the extraction is right
     */
    public boolean TakeResourcesMarket(char colrowextract, int numextract) {
        try {
            return game.getMarketStructure().extractMarbles(colrowextract, numextract);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return false;
        }
    }

    /**
     * This method permits to the client to add or to discard a caught marbles
     * @param choice  1 if the client want to add the marble, 0 if he want to discard the marble
     * @param indexwarehouse row's index in which the client want to add the marble
     * @return true if the add is right
     */
    public boolean AddDiscardMarble(boolean choice, int indexwarehouse) throws GameFinishedException {
        if (choice) {
            try {
                if (game.getMarketStructure().getBuffer().get(0).addtoWarehouse(game.getCurrentPlayer(), indexwarehouse)) {
                    game.getMarketStructure().getBuffer().remove(0);
                    return true;
                }
                else return false;
            } catch (ActiveVaticanReportException activeVaticanReportException) {
                game.getMarketStructure().getBuffer().remove(0);
                game.getFaithTrack().checkPopeSpace(game.getPlayersList(), 0);
                return true;
            }
        } else {
            game.getMarketStructure().discardMarbles(game.getMarketStructure().getBuffer().get(0));
            for (Player p : game.getPlayersList()) {
                if (p.equals(game.getCurrentPlayer())) {
                    try {
                        p.increasefaithMarker();
                    } catch (ActiveVaticanReportException e) {
                        game.getFaithTrack().checkPopeSpace(game.getPlayersList(), 0);
                    }
                }
            }
            return true;
        }
    }

    /**
     * This method permits to the client to change two rows of its Warehouse depot
     * @param row1 the first row that player want change
     * @param row2 the second row that player want to change
     * @return true if the client can do the change
     */
    public boolean ExchangeWarehouse (int row1, int row2) {
        return game.getCurrentPlayer().getWarehouse().checkExchange(row1, row2);
    }

    /**
     * This method lets the current player choose the two Leader cards to mantain
     * @param i1 index of the first card
     * @param i2 index of the second card
     * @return true if the indexes are correct
     */
    public boolean ChooseLeaderCards (int i1, int i2) {
        if (i1 < 4 && i1 >= 0 && i2 < 4 && i2 >= 0 && i1 != i2) {
            LeaderAction leaderAction1 = game.getCurrentPlayer().getLeaderActionBox().get(i1);
            LeaderAction leaderAction2 = game.getCurrentPlayer().getLeaderActionBox().get(i2);
            game.getCurrentPlayer().getLeaderActionBox().clear();
            game.getCurrentPlayer().addLeaderAction(leaderAction1);
            game.getCurrentPlayer().addLeaderAction(leaderAction2);
            return true;
        }
        return false;
    }

    /**
     * This method lets the potential second, third and fourth players choose the given resource(s)
     * @param resourcesList chosen resource(s)'s list
     * @return false if there is only one player
     */
    public boolean ChooseResources (List<Resources> resourcesList){
        if (game.getPlayersList().indexOf(game.getCurrentPlayer()) == 1 || game.getPlayersList().indexOf(game.getCurrentPlayer()) == 2) {
            game.getCurrentPlayer().getWarehouse().checkInsertion(0, resourcesList.get(0));
            return true;
        }
        else if (game.getPlayersList().indexOf(game.getCurrentPlayer()) == 3){
            game.getCurrentPlayer().getWarehouse().checkInsertion(0, resourcesList.get(0));
            game.getCurrentPlayer().getWarehouse().checkInsertion(1, resourcesList.get(1));
            return true;
        }
        return false;
    }

    /**
     * this method is used to put buffer resources in Player Strongbox
     */
    public void emptyBuffer(){
        Map<String,Integer> buffer= game.getCurrentPlayer().getSlotDevCards().getBuffer();

        for (String res: buffer.keySet()){
            try {
                game.getCurrentPlayer().getStrongbox().updateResources(res,buffer.get(res));
            } catch (NegativeQuantityExceptions ignored) {} //so che sono tutte positive
        }
        buffer.clear();
    }


    /**
     * getter of game
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * getter of numPlayersCount
     * @return numPlayersCount
     */
    public int getNumPlayersCount() {
        return numPlayersCount;
    }
}

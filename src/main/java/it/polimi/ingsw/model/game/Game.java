package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;

import java.io.IOException;
import java.util.*;

public class Game {

    /**
     * This attribute is the vector that contains the players' references
     */
    private List<Player> playersList;

    /**
     * This attribute is the current player's reference
     */
    private Player currentPlayer;

    /**
     * This attribute indicates if the game is finished
     */
    private boolean finishedGame;

    /**
     * This attribute is the development cards' container. It is implemented like a matrix of pile
     */
    private DevCardsDeck developmentCardDeck;

    /**
     * This attribute is a list of the Leader card
     */
    private LeaderCardDeck leaderCardDeck;

    /**
     * This attribute is a reference to the Market Structure
     */
    private MarketStructure marketStructure;

    /**
     * This attribute is a reference to the Faith track
     */
    private FaithTrack faithTrack;

    private Player lastPlayer;

    private GameState gameState;

    private TurnPhase turnPhase;

    /**
     * 0-2 slotDevCard Production
     * 3-4 leaderCard Production
     * 5 baseProduction
     */
    private boolean[] canDoProduction;

    /**
     * This is the constructor method
     */
    public Game() throws IOException {
        this.currentPlayer = null;
        this.playersList = new ArrayList<>();
        this.leaderCardDeck = new LeaderCardDeck();
        this.developmentCardDeck = new DevCardsDeck();
        this.marketStructure = new MarketStructure();
        this.faithTrack = new FaithTrack();
        this.finishedGame = false;
        this.lastPlayer = null;
        this.turnPhase = TurnPhase.DOTURN;
        this.canDoProduction = new boolean[6];
        setCanDoProductionTrue();
    }

    public void setCanDoProductionTrue(){
        for (int i = 0; i < 6; i++) {
            canDoProduction[i] = true;
        }
    }

    /**
     * This method returns the current player
     * @return current Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method takes from playersList the current turn's player.
     */
    public void setCurrentPlayer () throws EndGameException {
        int i = this.playersList.indexOf(this.currentPlayer);

        if (finishedGame && lastPlayer == null)
            lastPlayer = getCurrentPlayer();
        do {
            i++;
            if(i >= this.playersList.size()) i=0;
            if (finishedGame && playersList.get(i) == lastPlayer)
                throw new EndGameException();

        } while(!playersList.get(i).getConnected());

        currentPlayer=playersList.get(i);
    }

    /**
     * This method returns the winner of the game in multiplayer mode.
     * @return winning a player from "playersList"
     */
    public int getWinner (){
        Player playerWinnerMax = this.playersList.get(0);
        for(Player player : this.playersList){
            if(player.getVictoryPoints() > playerWinnerMax.getVictoryPoints()){
                playerWinnerMax = player;
            }
        }
        List<Player> playersWinners = new ArrayList<>();
        for(Player p : this.playersList){
            if(playerWinnerMax.getVictoryPoints() == p.getVictoryPoints()){
                playersWinners.add(p);
            }
        }
        for(Player player : playersWinners){
            if(player.countTotalResources() > playerWinnerMax.countTotalResources())
                playerWinnerMax = player;
        }
        return this.playersList.indexOf(playerWinnerMax);
    }

    /**
     * This method checks if the game is finished
     * @return true if the game is finished
     */
    public boolean isFinishedGame() {
        for(Player player : this.playersList){
            if(player.getFaithMarker() == this.faithTrack.getFaithtracksize() || player.getSlotDevCards().countTotalNumberDevCards() >= 7) {
                finishedGame = true;
                return true;
            }
        }
        return false;
    }

    /**
     * This method initialized the game. It make draw four Leader Cards to each player, that discards two; it extracts the first player and gives to all players the initial resources and faith points
     */
    public void startgame () {
        // Dà 4 carte leader a ogni giocatore. Poi tramite il controller verranno scartate 2 carte per ogni giocatore
        for (Player player : this.playersList) {
            for (int x = 0; x < 4; x++) {
                this.leaderCardDeck.givetoPlayer(0, player);
            }
        }
        // Set the current Player
        Collections.shuffle(this.playersList);
        this.currentPlayer = this.playersList.get(0);
        // Assegno solo i punti fede agli altri giocatori. Le Risorse a scelta vengono selezionate tramite il controller
        for(int i = 2; i < this.playersList.size(); i++){
            try {
                this.playersList.get(i).increasefaithMarker();
            }
            catch (ActiveVaticanReportException ignored) {}
        }
    }


    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        for(Player p : this.playersList){
            p.addVictoryPoints(p.getSlotDevCards().countVictoryPoints() + p.countLeaderActionVictoryPoints() + (p.countTotalResources())/5 + faithTrack.getPlayerPoint(p));
        }
    }

    /**
     * This method translates the TakeMarbles message
     * @param colrowextract indicates the row or the column to extract
     * @param numextract    the column or row's number
     * @return true if the extraction is right
     */
    public boolean extractionMarble(char colrowextract, int numextract) {
        if (turnPhase.equals(TurnPhase.DOTURN)) {
            try {
                if (marketStructure.extractMarbles(colrowextract, numextract)) {
                    turnPhase = TurnPhase.EXTRACTMARBLES;
                    //update buffer marbles
                    //update riga/colonna e num
                    return true;
                }
                else return false;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return false;
            }
        }
        else return false;
    }

    /**
     * This method permits to the client to add or to discard a caught marbles
     * @param choice 0 if the client want to add the marble to Warehouse, 1 to Extra chest, 2 if he want to discard the marble
     * @param indexWarehouse row's index in which the client want to add the marble
     * @return true if the add is right
     */
    public boolean manageMarble(int choice, int indexWarehouse, String resourceWhiteMarble) {
        if (!marketStructure.getBuffer().isEmpty()) {
            currentPlayer.chooseResourceWhiteMarbleEffect(resourceWhiteMarble);
            if (choice == 0) {
                try {
                    if (marketStructure.getBuffer().get(0).addtoWarehouse(currentPlayer, indexWarehouse)) {
                        marketStructure.getBuffer().remove(0);
                        //update warehouse e buffer
                        return true;
                    }
                    else return false;
                } catch (ActiveVaticanReportException activeVaticanReportException) {
                    marketStructure.getBuffer().remove(0);
                    try {
                        faithTrack.checkPopeSpace(playersList, 0);
                    } catch (GameFinishedException e) {
                        //update faithmarker e buffer
                        return isFinishedGame();
                    }
                    //update faithmarker e buffer
                    return true;
                }
            }
            else if (choice == 1) {
                if (marketStructure.getBuffer().get(0).addToExtraChest(currentPlayer)) {
                    marketStructure.getBuffer().remove(0);
                    //update warehouse e extrachest e buffer
                    return true;
                }
                else return false;
            }
            else if (choice == 2) {
                marketStructure.discardMarbles(marketStructure.getBuffer().get(0));
                for (Player p : playersList) {
                    if (p.equals(currentPlayer)) {
                        try {
                            p.increasefaithMarker();
                            //update faithmarker
                        } catch (ActiveVaticanReportException e) {
                            try {
                                faithTrack.checkPopeSpace(playersList, 0);
                            } catch (GameFinishedException gameFinishedException) {
                                return isFinishedGame();
                            }
                        }
                    }
                }
                return true;
            }
            else return false;
        }
        else return false;
    }

    /**
     * This method permits to the client to change two rows of its Warehouse depot
     * @param row1 the first row that player want change
     * @param row2 the second row that player want to change
     * @return true if the client can do the change
     */
    public boolean exchangeWarehouse (int row1, int row2) {
        //update warehouse
        return currentPlayer.getWarehouse().checkExchange(row1, row2);
    }

    /**
     * this method called by the player allow to select from the DevCardDeck the card the player wants to buy
     * @param ID of the dev card
     * @param column of the SlotDevCard
     * @return true if there are no errors
     */
    public boolean selectDevCard(String ID, int column){
        if (turnPhase.equals(TurnPhase.DOTURN)) {
            if (column < 0 || column > 2)
                return false;
            //catch NullPointerException perché l'ID potrebbe essere errato
            try {
                //controllo che il giocatore abbia spazio nello SlotDevCard e che abbia le risorse necessarie
                if (currentPlayer.getSlotDevCards().maxLevelPurchase(developmentCardDeck.getDevCardFromID(ID)) && developmentCardDeck.getDevCardFromID(ID).getCost().checkResources(currentPlayer)) {
                    currentPlayer.setCurrentDevCardToBuy(developmentCardDeck.getDevCardFromID(ID));
                    currentPlayer.setColumnSlotBuyDev(column);
                    turnPhase = TurnPhase.BUYDEVCARD;
                    return true;
                }
                else return false;
            }
            catch (NullPointerException e) {
                return false;
            }
        }
        else return false;
    }

    public boolean payResourcesToBuyDevCard (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        if (currentPlayer.getCurrentDevCardToBuy() != null) {
            if(!currentPlayer.getCurrentDevCardToBuy().getCost().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap))
                return false;
            if (deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap)) {
                try {
                    if (currentPlayer.getSlotDevCards().insertCards(currentPlayer.getColumnSlotBuyDev(), currentPlayer.getCurrentDevCardToBuy())) {
                        currentPlayer.setCurrentDevCardToBuy(null);
                        currentPlayer.setColumnSlotBuyDev(-1);

                        turnPhase = TurnPhase.ENDTURN;
                        return true;
                    }
                    else return false;
                } catch (GameFinishedException e) {
                    currentPlayer.setCurrentDevCardToBuy(null);
                    currentPlayer.setColumnSlotBuyDev(-1);

                    turnPhase = TurnPhase.ENDTURN;
                    return isFinishedGame();
                }
            }
            else return false;
        }
        else return false;
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
        if(!(currentPlayer.checkListResources(WarehouseRes,StrongboxRes,ExtrachestMap))) return false;

        //warehouse
        for(String res: WarehouseRes.keySet()){
            int index=0;
            //qui ho hardcodato le risorse
            Resources[]  ResList={new Coins(),new Shields(), new Servants(), new Servants()};
            while(!ResList[index].toString().equals(res))index++;

            for(int j=0;j<WarehouseRes.get(res);j++)
                currentPlayer.getWarehouse().delete(ResList[index]);
        }

        //strongbox
        for(String res: StrongboxRes.keySet()){
            try {
                currentPlayer.getStrongbox().updateResources(res,-StrongboxRes.get(res));
            } catch (NegativeQuantityExceptions ignored) {}//ho controllato prima che il giocatore abbia le risorse
        }

        //extrachest
        for(String res: ExtrachestMap.keySet()){
            int Resindex=0;
            //qui ho hardcodato le risorse
            Resources[]  ResList={new Coins(),new Shields(), new Servants(), new Servants()};
            while(!ResList[Resindex].toString().equals(res))Resindex++;

            int ChestIndex=0;
            while(!currentPlayer.getWarehouse().getLeaderCardEffect().get(ChestIndex).getResources().toString().equals(res))ChestIndex++;
            //so che l'indice c'è perchè faccio controllo prima

            try {
                currentPlayer.getWarehouse().getLeaderCardEffect().get(ChestIndex).updateResources( -ExtrachestMap.get(res));
            } catch (OverflowQuantityExcepions | NegativeQuantityExceptions ignored) {}//non uso i catch perchè controllo prima le risorse
        }
        return true;
    }

    public boolean activeBaseProduction (char r1, String reqRes1, char r2, String reqRes2, String chosenResource) {
        if (turnPhase.equals(TurnPhase.DOTURN) || turnPhase.equals(TurnPhase.DOPRODUCTION)) {
            if (canDoProduction[5]) {
                Map<String,Integer> WarehouseRes = new HashMap<>();
                Map<String,Integer> StrongboxRes = new HashMap<>();
                Map<String,Integer> ExtrachestMap = new HashMap<>();
                switch (r1) {
                    case 'w' :
                        WarehouseRes.put(reqRes1, 1);
                        break;
                    case 's' :
                        StrongboxRes.put(reqRes1, 1);
                        break;
                    case 'e' :
                        ExtrachestMap.put(reqRes1, 1);
                        break;
                    default:
                        return false;
                }
                switch (r2) {
                    case 'w' :
                        WarehouseRes.put(reqRes2, 1);
                        break;
                    case 's' :
                        StrongboxRes.put(reqRes2, 1);
                        break;
                    case 'e' :
                        ExtrachestMap.put(reqRes2, 1);
                        break;
                    default:
                        return false;
                }
                if (deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap)) {
                    currentPlayer.getSlotDevCards().baseProduction(chosenResource);
                    canDoProduction[5] = false;
                    turnPhase = TurnPhase.DOPRODUCTION;
                    return true;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    public boolean activeLeaderCardProduction (String ID, char r, String resource, int indexExtraProduction) {
        if (turnPhase.equals(TurnPhase.DOTURN) || turnPhase.equals(TurnPhase.DOPRODUCTION)) {
            if (canDoProduction[3] || canDoProduction[4]) {
                Resources resourceConverted = null;
                Resources[] resourcesVet = {
                        new Coins(),
                        new Servants(),
                        new Shields(),
                        new Stones()
                };
                for (Resources res : resourcesVet) {
                    if (resource.equals(res.toString()))
                        resourceConverted = res;
                }
                if (resourceConverted == null)
                    return false;

                if (currentPlayer.getSlotDevCards().getLeaderCardEffect().isEmpty())
                    return false;
                else {
                    if (currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction) == null)
                        return false;
                    else {
                        switch (r) {
                            case 'w' :
                                if (currentPlayer.getWarehouse().delete(currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).getResources())) {
                                    try {
                                        currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).activeExtraProduction(currentPlayer, resourceConverted);
                                    } catch (ActiveVaticanReportException e) {
                                        try {
                                            faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                                        } catch (GameFinishedException gameFinishedException) {
                                            if (canDoProduction[3])
                                                canDoProduction[3] = false;
                                            else canDoProduction[4] = false;
                                            turnPhase = TurnPhase.DOPRODUCTION;
                                            return isFinishedGame();
                                        }
                                        if (canDoProduction[3])
                                            canDoProduction[3] = false;
                                        else canDoProduction[4] = false;
                                        turnPhase = TurnPhase.DOPRODUCTION;
                                        return true;
                                    }
                                    if (canDoProduction[3])
                                        canDoProduction[3] = false;
                                    else canDoProduction[4] = false;
                                    turnPhase = TurnPhase.DOPRODUCTION;
                                    return true;
                                }
                            case 's' :
                                try {
                                    currentPlayer.getStrongbox().updateResources(currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).getResources(), -1);
                                } catch (NegativeQuantityExceptions negativeQuantityExceptions) {
                                    return false;
                                }
                                try {
                                    currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).activeExtraProduction(currentPlayer, resourceConverted);
                                } catch (ActiveVaticanReportException e) {
                                    try {
                                        faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                                        if (canDoProduction[3])
                                            canDoProduction[3] = false;
                                        else canDoProduction[4] = false;
                                        turnPhase = TurnPhase.DOPRODUCTION;
                                    } catch (GameFinishedException gameFinishedException) {
                                        if (canDoProduction[3])
                                            canDoProduction[3] = false;
                                        else canDoProduction[4] = false;
                                        turnPhase = TurnPhase.DOPRODUCTION;
                                        return isFinishedGame();
                                    }
                                }
                                if (canDoProduction[3])
                                    canDoProduction[3] = false;
                                else canDoProduction[4] = false;
                                turnPhase = TurnPhase.DOPRODUCTION;
                                return true;
                            case 'e' :
                                for (ExtraChest chest : currentPlayer.getWarehouse().getLeaderCardEffect()) {
                                    if (chest.getResources().equals(currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).getResources())) {
                                        try {
                                            chest.updateResources(-1);
                                        } catch (OverflowQuantityExcepions ignored) {}
                                        catch (NegativeQuantityExceptions negativeQuantityExceptions) {
                                            return false;
                                        }
                                        try {
                                            currentPlayer.getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).activeExtraProduction(currentPlayer, resourceConverted);
                                        } catch (ActiveVaticanReportException e) {
                                            try {
                                                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                                                if (canDoProduction[3])
                                                    canDoProduction[3] = false;
                                                else canDoProduction[4] = false;
                                                turnPhase = TurnPhase.DOPRODUCTION;
                                            } catch (GameFinishedException gameFinishedException) {
                                                if (canDoProduction[3])
                                                    canDoProduction[3] = false;
                                                else canDoProduction[4] = false;
                                                turnPhase = TurnPhase.DOPRODUCTION;
                                                return isFinishedGame();
                                            }
                                        }
                                        if (canDoProduction[3])
                                            canDoProduction[3] = false;
                                        else canDoProduction[4] = false;
                                        turnPhase = TurnPhase.DOPRODUCTION;
                                        return true;
                                    }
                                }
                                return false;
                            default:
                                return false;
                        }
                    }
                }
            }
            else return false;
        }
        else return false;
    }

    public boolean activeDevCardProduction (int col) {
        if (turnPhase.equals(TurnPhase.DOTURN) || turnPhase.equals(TurnPhase.DOPRODUCTION)) {
            if (canDoProduction[0] || canDoProduction[1] || canDoProduction[2]) {
                try {
                    if (currentPlayer.getSlotDevCards().getDevCards(col) == null)
                        return false;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }
                DevelopmentCard card = currentPlayer.getSlotDevCards().getDevCards(col);
                if (card.getCostProduction().checkResources(currentPlayer) && currentPlayer.getSlotDevCards().checkUsage(card)) {
                    currentPlayer.setCurrentDevCardToProduce(currentPlayer.getSlotDevCards().getDevCards(col));
                    currentPlayer.setColumnSlotDevCardToProduce(col);
                    if (canDoProduction[0])
                        canDoProduction[0] = false;
                    else if (canDoProduction[1])
                        canDoProduction[1] = false;
                    else if (canDoProduction[2])
                        canDoProduction[2] = false;
                    turnPhase = TurnPhase.DOPRODUCTION;
                    return true;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    public boolean payResourcesForDevCardProduction (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        if (currentPlayer.getCurrentDevCardToProduce() != null) {
            DevelopmentCard card = currentPlayer.getCurrentDevCardToProduce();

            //controlla se risorse corrispondono a costo carta
            if(!card.getCostProduction().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap))
                return false;

            //elimino le risorse
            if(!deleteRes(WarehouseRes,StrongboxRes,ExtrachestMap))
                return false;

            //assegno le risorse al buffer giocatore
            currentPlayer.getSlotDevCards().cardProduction(card);

            return true;
        }
        else return false;
    }

    public boolean endProduction () {
        if (!currentPlayer.getSlotDevCards().getBuffer().isEmpty()) {
            if (emptyBuffer()) {
                //update Strongbox
                return true;
            }
            else return false;
        }
        else return false;
    }

    /**
     * this method is used to put buffer resources in Player Strongbox
     */
    public boolean emptyBuffer(){
        Map<String,Integer> buffer = currentPlayer.getSlotDevCards().getBuffer();

        for (String res: buffer.keySet()){
            try {
                if (res.equals("FaithMarker")) {
                    try {
                        currentPlayer.increasefaithMarker();
                    } catch (ActiveVaticanReportException e) {
                        try {
                            faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                        } catch (GameFinishedException gameFinishedException) {
                            return isFinishedGame();
                        }
                        return true;
                    }
                }
                else currentPlayer.getStrongbox().updateResources(res,buffer.get(res));
            } catch (NegativeQuantityExceptions ignored) {} //so che sono tutte positive
        }
        buffer.clear();
        return true;
    }

    public boolean updateLeaderCard (String ID, int choice) {
        if (!currentPlayer.getLeaderActionBox().isEmpty()) {
            if (choice == 0) {
                if (discardLeaderAction(ID)) {

                    return true;
                }
                else return false;
            }
            else if (choice == 1) {
                if (activeLeaderAction(ID)) {

                    return true;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    /**
     * this method called by the player allow to activate a leaderActionCard
     * @param ID refer which card must to be activated, 0 for the first, 1 for the second
     * @return true if the card is activated
     */
    public boolean activeLeaderAction(String ID){
        int pos = -1;
        for (LeaderAction l : currentPlayer.getLeaderActionBox()) {
            if (l.getID().equals(ID)) {
                pos = currentPlayer.getLeaderActionBox().indexOf(l);
            }
        }

        //controllo se posizione valida
        if(pos < 0 || pos>1)
            return false;

        LeaderAction card = currentPlayer.getLeaderActionBox().get(pos);

        //carta già attivata
        if(card.getActivated())
            return true;

        //controllo risorse
        if(!card.getCost().checkResources(currentPlayer))
            return false;
        //attivo carta
        card.setActivated();

        //potrei fare return true ma lo uso per un "ulteriore controllo"
        return card.getActivated();
    }

    /**
     * this method called by the player discard one of his leader action card to increase his faithmarker by one
     * @param ID which leader action card must to be discarded: 0 for the first, 1 for the second
     * @return true if the card is discarded
     */
    public boolean discardLeaderAction(String ID) {
        int pos = -1;
        for (LeaderAction l : currentPlayer.getLeaderActionBox()) {
            if (l.getID().equals(ID)) {
                pos = currentPlayer.getLeaderActionBox().indexOf(l);
            }
        }

        int size = currentPlayer.getLeaderActionBox().size();
        //verifico se indice corretto
        if (pos > size)
            return false;
        //verifico che non sia già stata attivata
        if (currentPlayer.getLeaderActionBox().get(pos).getActivated())
            return false;
        currentPlayer.getLeaderActionBox().remove(pos);
        try {
            currentPlayer.increasefaithMarker();
        } catch (ActiveVaticanReportException e) {
            try {
                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {
                return isFinishedGame();
            }
        }
        return true;
    }

    public void endTurn () throws EndGameException {
        try {
            playLorenzoTurn();
        } catch (ActiveVaticanReportException e) {
            try {
                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {

                isFinishedGame();
            }

        }
        turnPhase = TurnPhase.DOTURN;
        setCanDoProductionTrue();
        setCurrentPlayer();
    }

    /**
     * This method adds a Player to playersList
     * @param player that is to add to the players' list
     */
    public void addPlayersList(Player player){
        this.playersList.add(player);
    }

    /**
     * This method returns the marketSructure attribute
     * @return a Market Structure object
     */
    public MarketStructure getMarketStructure(){
        return this.marketStructure;
    }

    /**
     * This method returns hte playerList attribute
     * @return a Player's list
     */
    public List<Player> getPlayersList(){
        return this.playersList;
    }

    /**
     * This method returns the developmentCardDeck attribute
     * @return a DevCardsDeck object
     */
    public DevCardsDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    /**
     * This method returns the LeaderCardDeck attribute
     * @return a LeaderCardDeck object
     */
    public LeaderCardDeck getLeaderCardDeck() {
        return leaderCardDeck;
    }

    /**
     * This method returns the faithTrack attribute
     * @return a FaithTrack object
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public int getBlackCrossToken () {
        return 0;
    }

    public void playLorenzoTurn () throws ActiveVaticanReportException {
    }

    public String getCurrentToken () {
        return null;
    }

    public GameState getGameState() {
        return gameState;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    public boolean[] getCanDoProduction() {
        return canDoProduction;
    }

    public void setCanDoProduction(int i) {
        canDoProduction[i] = false;
    }
}

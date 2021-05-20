package it.polimi.ingsw.model.game;

import it.polimi.ingsw.Network.Server.UpdateCreator;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.io.IOException;
import java.util.*;

public class Game {
    private int numPlayers;

    /** This attribute is the vector that contains the players' references */
    private List<Player> playersList;

    /** This attribute is the current player's reference */
    private Player currentPlayer;

    /** This attribute indicates if the game is finished */
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

    private UpdateCreator update;

    /**
     * This is the constructor method
     */
    public Game(UpdateCreator update) throws IOException {
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
        this.update = update;
        this.numPlayers = 0;
        this.gameState = GameState.INITGAME;
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
        update.onUpdateCurrentPlayer(currentPlayer);
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
        for(int i=0;i<playersList.size();i++){
            switch (i){
                case 1:playersList.get(i).setInitialResources(1);
                case 2:playersList.get(i).setInitialResources(1);
                case 3:playersList.get(i).setInitialResources(2);
                default:
                    playersList.get(i).setInitialResources(0);
            }
        }
        currentPlayer=playersList.get(0);
        // Assegno solo i punti fede agli altri giocatori. Le Risorse a scelta vengono selezionate tramite il controller
        for(int i = 2; i < this.playersList.size(); i++){
            try {
                this.playersList.get(i).increasefaithMarker();
            }
            catch (ActiveVaticanReportException ignored) {}
        }
        update.onUpdateStartGame(developmentCardDeck.getDevelopmentCardDeck(), playersList, marketStructure.getMarketTray(), marketStructure.getRemainingMarble());
    }

    /**
     * This method counts victory points of each player at the end of the game according to the rules
     */
    public void givefinalpoints () {
        for(Player p : this.playersList){
            p.addVictoryPoints(p.getSlotDevCards().countVictoryPoints() + p.countLeaderActionVictoryPoints() + (p.countTotalResources())/5 + faithTrack.getPlayerPoint(p));
        }
    }

    public boolean chooseInitialLeaderCards (int i1, int i2) {
        if (gameState != GameState.INITGAME) {
            update.onUpdateError(currentPlayer.getNickname(),"You have already chosen your Leader cards.");
            return false;
        }

        if (currentPlayer.getLeaderActionBox().size() == 4) {
            if (i1 < 0 || i1 > 3 || i2 < 0 || i2 > 3 || i1 == i2) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong indexes.");
                return false;
            }
            LeaderAction d1 = currentPlayer.getLeaderActionBox().get(i1);
            LeaderAction d2 = currentPlayer.getLeaderActionBox().get(i2);
            currentPlayer.getLeaderActionBox().clear();
            currentPlayer.getLeaderActionBox().add(d1);
            currentPlayer.getLeaderActionBox().add(d2);
            update.onUpdateInitialLeaderCards(currentPlayer, currentPlayer.getLeaderActionBox());
            return true;
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You have already chosen your Leader cards.");
            return false;
        }
    }

    public boolean chooseInitialResources (List<String> initialResources) {
        if (gameState != GameState.INITGAME) {
            update.onUpdateError(currentPlayer.getNickname(),"You have already chosen your resources.");
            return false;
        }

        if (playersList.indexOf(currentPlayer) == 0) {
            update.onUpdateError(currentPlayer.getNickname(),"You can't choose resources");
            return false;
        }

        if (playersList.indexOf(currentPlayer) == 1 || playersList.indexOf(currentPlayer) == 2) {
            if(initialResources.size()!=1){
                update.onUpdateError(currentPlayer.getNickname(),"Wrong Number of Resources!");
                return false;
            }
            if (!isRightResource(initialResources.get(0))) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong resource");
                return false;
            }
            currentPlayer.getWarehouse().checkInsertion(0, convertStringToResource(initialResources.get(0)));
        }

        if (playersList.indexOf(currentPlayer) == 3) {
            if (!isRightResource(initialResources.get(0)) || !isRightResource(initialResources.get(1))) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong resources");
                return false;
            }
            if (initialResources.get(0).equals(initialResources.get(1))) {
                currentPlayer.getWarehouse().checkInsertion(2, convertStringToResource(initialResources.get(0)));
                currentPlayer.getWarehouse().checkInsertion(2, convertStringToResource(initialResources.get(1)));
            }
            else {
                currentPlayer.getWarehouse().checkInsertion(0, convertStringToResource(initialResources.get(0)));
                currentPlayer.getWarehouse().checkInsertion(1, convertStringToResource(initialResources.get(1)));
            }
        }
        currentPlayer.setInitialResources(0);
        update.onUpdateWarehouse(currentPlayer, false);
        gameState = GameState.INGAME;
        return true;
    }


    public Resources convertStringToResource (String stringResource) {
        Resources[] resources = {new Coins(), new Servants(), new Shields(), new Stones()};
        for (Resources r : resources) {
            if (stringResource.equals(r.toString()) || stringResource.equals(r.toString().toUpperCase()))
                return r;
        }
        return null;
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
                    update.onUpdateMarketTray(currentPlayer, colrowextract, numextract);
                    return true;
                }
                else {
                    update.onUpdateError(currentPlayer.getNickname(),"Wrong message");
                    return false;
                }
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong number of column or row");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can't do this action");
            return false;
        }
    }

    /**
     * This method permits to the client to add or to discard a caught marbles
     * @param choice 0 if the client want to add the marble to Warehouse, 1 to Extra chest, 2 if he want to discard the marble
     * @param indexWarehouse row's index in which the client want to add the marble
     * @return true if the add is right
     */
    public boolean manageMarble(int choice, int indexWarehouse, String resourceWhiteMarble) {
        if (!marketStructure.getBuffer().isEmpty()) {
            if (!currentPlayer.getLeaderCardEffectWhiteMarble().isEmpty()) {
                if (resourceWhiteMarble == null && indexWarehouse != 2) {
                    update.onUpdateError(currentPlayer.getNickname(),"You have not choose a resource.");
                    return false;
                }
                if (isRightResource(resourceWhiteMarble)) {
                    currentPlayer.chooseResourceWhiteMarbleEffect(resourceWhiteMarble);
                }
            }
            if (choice == 0) {
                if (indexWarehouse < 0 || indexWarehouse > 2) {
                    update.onUpdateError(currentPlayer.getNickname(),"You have put a wrong Warehouse's index.");
                    return false;
                }
                try {
                    int faithMarker = currentPlayer.getFaithMarker();
                    if (marketStructure.getBuffer().get(0).addtoWarehouse(currentPlayer, indexWarehouse)) {
                        marketStructure.getBuffer().remove(0);
                        if (faithMarker == currentPlayer.getFaithMarker())
                            update.onUpdateWarehouse(currentPlayer, true);
                        else update.onUpdateFaithMarker(currentPlayer, playersList, true);
                        return true;
                    }
                    else {
                        update.onUpdateError(currentPlayer.getNickname(),"You can not add a resource into the Warehouse.");
                        return false;
                    }
                } catch (ActiveVaticanReportException activeVaticanReportException) {
                    marketStructure.getBuffer().remove(0);
                    try {
                        faithTrack.checkPopeSpace(playersList, 0);
                    } catch (GameFinishedException e) {
                        if (isFinishedGame())
                            update.onUpdateGameFinished();
                    }
                    update.onUpdateFaithMarker(currentPlayer, playersList, true);
                    return true;
                }
            }
            else if (choice == 1) {
                if (marketStructure.getBuffer().get(0).addToExtraChest(currentPlayer)) {
                    marketStructure.getBuffer().remove(0);
                    update.onUpdateWarehouse(currentPlayer, true);
                    return true;
                }
                else {
                    update.onUpdateError(currentPlayer.getNickname(),"You can not add a resource into Extra Chest.");
                    return false;
                }
            }
            else if (choice == 2) {
                marketStructure.discardMarbles(marketStructure.getBuffer().get(0));
                for (Player p : playersList) {
                    if (!p.equals(currentPlayer)) {
                        try {
                            p.increasefaithMarker();
                        } catch (ActiveVaticanReportException e) {
                            try {
                                faithTrack.checkPopeSpace(playersList, 0);
                            } catch (GameFinishedException gameFinishedException) {
                                if (isFinishedGame())
                                    update.onUpdateGameFinished();
                            }
                        }
                    }
                }
                update.onUpdateFaithMarker(currentPlayer, playersList, true);
                return true;
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong choice.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can not do this action.");
            return false;
        }
    }

    /**
     * This method permits to the client to change two rows of its Warehouse depot
     * @param row1 the first row that player want change
     * @param row2 the second row that player want to change
     * @return true if the client can do the change
     */
    public boolean exchangeWarehouse (int row1, int row2) {
        if (currentPlayer.getWarehouse().checkExchange(row1, row2)) {
            update.onUpdateWarehouse(currentPlayer, false);
            return true;
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"Wrong rows.");
            return false;
        }
    }

    /**
     * this method called by the player allow to select from the DevCardDeck the card the player wants to buy
     * @param ID of the dev card
     * @param column of the SlotDevCard
     * @return true if there are no errors
     */
    public boolean selectDevCard(String ID, int column){
        if (turnPhase.equals(TurnPhase.DOTURN)) {
            if (column < 0 || column > 2) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong column.");
                return false;
            }
            //catch NullPointerException perché l'ID potrebbe essere errato
            try {
                //controllo che il giocatore abbia spazio nello SlotDevCard e che abbia le risorse necessarie
                if (currentPlayer.getSlotDevCards().maxLevelPurchase(developmentCardDeck.getDevCardFromID(ID)) && developmentCardDeck.getDevCardFromID(ID).getCost().checkResources(currentPlayer)) {
                    currentPlayer.setCurrentDevCardToBuy(developmentCardDeck.getDevCardFromID(ID));
                    currentPlayer.setColumnSlotBuyDev(column);
                    turnPhase = TurnPhase.BUYDEVCARD;
                    update.onUpdateDevCardDeck(currentPlayer, currentPlayer.getCurrentDevCardToBuy());
                    return true;
                }
                else {
                    update.onUpdateError(currentPlayer.getNickname(),"You can not buy this card.");
                    return false;
                }
            }
            catch (NullPointerException e) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong ID");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can not do this action.");
            return false;
        }
    }

    public boolean payResourcesToBuyDevCard (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
            if(!currentPlayer.getCurrentDevCardToBuy().getCost().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap)) {
                update.onUpdateError(currentPlayer.getNickname(),"Insufficient resources.");
                return false;
            }
            if (deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap)) {
                try {
                    if (currentPlayer.getSlotDevCards().insertCards(currentPlayer.getColumnSlotBuyDev(), currentPlayer.getCurrentDevCardToBuy())) {
                        currentPlayer.setCurrentDevCardToBuy(null);
                        currentPlayer.setColumnSlotBuyDev(-1);
                        update.onUpdateResources(currentPlayer);
                        turnPhase = TurnPhase.ENDTURN;
                        return true;
                    }
                    else {
                        update.onUpdateError(currentPlayer.getNickname(),"You can't insert the Development card.");
                        return false;
                    }
                } catch (GameFinishedException e) {
                    currentPlayer.setCurrentDevCardToBuy(null);
                    currentPlayer.setColumnSlotBuyDev(-1);
                    update.onUpdateResources(currentPlayer);
                    turnPhase = TurnPhase.ENDTURN;
                    if (isFinishedGame()) {
                        update.onUpdateGameFinished();
                        return true;
                    }
                    else {
                        update.onUpdateError(currentPlayer.getNickname(),"Comm sfaccimm è possibil.");
                        return false;
                    }
                }
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"You can not delete these resources.");
                return false;
            }
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
        if (!isRightResource(reqRes1) || !isRightResource(reqRes2) || !isRightResource(chosenResource)) {
            update.onUpdateError(currentPlayer.getNickname(),"Wrong resources.");
            return false;
        }
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
                    update.onUpdateResources(currentPlayer);
                    return true;
                }
                else {
                    update.onUpdateError(currentPlayer.getNickname(),"You can not delete these resources to do this action.");
                    return false;
                }
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"You have already do this action.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can not do this action.");
            return false;
        }
    }

    public boolean isRightResource (String resource) {
        String[] allResources = {"Coins", "Servants", "Shields", "Stones"};
        for (String r : allResources){
            if (resource.equals(r) || resource.equals(r.toUpperCase()))
                return true;
        }
        return false;
    }

    public boolean activeLeaderCardProduction (String ID, char r, String resource) {
        if (turnPhase.equals(TurnPhase.DOTURN) || turnPhase.equals(TurnPhase.DOPRODUCTION)) {
            if (!isRightResource(resource)) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong resource.");
                return false;
            }
            LeaderAction l = null;
            int posLeaderBox = -1;
            for (LeaderAction l1 : currentPlayer.getLeaderActionBox()) {
                if (l1.getID().equals(ID)) {
                    l = l1;
                    posLeaderBox = currentPlayer.getLeaderActionBox().indexOf(l1);
                }
            }
            if (l == null) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong ID.");
                return false;
            }
            if (!l.getActivated()) {
                update.onUpdateError(currentPlayer.getNickname(),"This card is not activated.");
                return false;
            }
            if (posLeaderBox < 0 || posLeaderBox > 1) {
                update.onUpdateError(currentPlayer.getNickname(),"Error.");
                return false;
            }
            if (!canDoProduction[posLeaderBox + 3]) {
                update.onUpdateError(currentPlayer.getNickname(),"You have already do this production.");
                return false;
            }
            String reqResource = l.getResources().toString();
            Map<String, Integer> w = new HashMap<>();
            Map<String, Integer> s = new HashMap<>();
            Map<String, Integer> e = new HashMap<>();
            switch (r) {
                case 'w' :
                    w.put(reqResource, 1);
                    break;
                case 's' :
                    s.put(reqResource, 1);
                    break;
                case 'e' :
                    e.put(reqResource, 1);
                    break;
                default:
                    return false;
            }
            if (deleteRes(w, s, e)) {
                try {
                    currentPlayer.increasefaithMarker();
                } catch (ActiveVaticanReportException activeVaticanReportException) {
                    try {
                        faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                    } catch (GameFinishedException gameFinishedException) {
                        if (isFinishedGame())
                            update.onUpdateGameFinished();
                    }
                    update.onUpdateFaithMarker(currentPlayer, playersList, false);
                }
                if (currentPlayer.getSlotDevCards().getBuffer().containsKey(resource)) {
                    currentPlayer.getSlotDevCards().getBuffer().put(resource, currentPlayer.getSlotDevCards().getBuffer().get(resource) + 1);
                }
                else {
                    currentPlayer.getSlotDevCards().getBuffer().put(resource, 1);
                }
                canDoProduction[posLeaderBox + 3] = false;
                turnPhase = TurnPhase.DOPRODUCTION;
                return true;
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"You can not delete these resources to do this action.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can do this action.");
            return false;
        }
    }

    public boolean activeDevCardProduction (int col) {
        if (turnPhase.equals(TurnPhase.DOTURN) || turnPhase.equals(TurnPhase.DOPRODUCTION)) {
            if (canDoProduction[col]) {
                try {
                    if (currentPlayer.getSlotDevCards().getDevCards(col) == null) {
                        update.onUpdateError(currentPlayer.getNickname(),"This card does not exist.");
                        return false;
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    update.onUpdateError(currentPlayer.getNickname(),"Wrong column.");
                    return false;
                }
                DevelopmentCard card = currentPlayer.getSlotDevCards().getDevCards(col);
                if (card.getCostProduction().checkResources(currentPlayer) && currentPlayer.getSlotDevCards().checkUsage(card)) {
                    currentPlayer.setCurrentDevCardToProduce(currentPlayer.getSlotDevCards().getDevCards(col));
                    currentPlayer.setColumnSlotDevCardToProduce(col);
                    canDoProduction[col] = false;
                    turnPhase = TurnPhase.DOPRODUCTION;
                    update.onUpdateActivatedDevCardProduction(currentPlayer, card.getID());
                    return true;
                }
                else {
                    update.onUpdateError(currentPlayer.getNickname(),"You have not the resources to activate this production power.");
                    return false;
                }
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"You have already do this production.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can do this action.");
            return false;
        }
    }

    public boolean payResourcesForDevCardProduction (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
            DevelopmentCard card = currentPlayer.getCurrentDevCardToProduce();

            //controlla se risorse corrispondono a costo carta
            if(!card.getCostProduction().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap)) {
                update.onUpdateError(currentPlayer.getNickname(),"Insufficient resources.");
                return false;
            }

            //elimino le risorse
            if(!deleteRes(WarehouseRes,StrongboxRes,ExtrachestMap)) {
                update.onUpdateError(currentPlayer.getNickname(),"You can't delete these resources because you don't have them.");
                return false;
            }

            //assegno le risorse al buffer giocatore
            currentPlayer.getSlotDevCards().cardProduction(card);
            update.onUpdateResources(currentPlayer);
            return true;
    }

    public boolean payResources(Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap){
        if(currentPlayer.getCurrentDevCardToProduce() != null)
            return payResourcesForDevCardProduction(WarehouseRes, StrongboxRes, ExtrachestMap);
        else if(currentPlayer.getCurrentDevCardToBuy() != null) {
            return payResourcesToBuyDevCard(WarehouseRes, StrongboxRes, ExtrachestMap);
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You can't do this.");
            return false;
        }
    }

    public boolean endProduction () {
        if (!currentPlayer.getSlotDevCards().getBuffer().isEmpty()) {
            if (emptyBuffer()) {
                update.onUpdateStrongBox(currentPlayer);
                return true;
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"Difficile che entri qui.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"Empty buffer.");
            return false;
        }
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
                    update.onUpdateLeaderCard(currentPlayer, ID, false);
                    return true;
                }
                else return false;
            }
            else if (choice == 1) {
                if (activeLeaderAction(ID)) {
                    update.onUpdateLeaderCard(currentPlayer, ID, true);
                    return true;
                }
                else return false;
            }
            else {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong choice.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You don't have Leader cards.");
            return false;
        }
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
        if(pos < 0 || pos>1) {
            update.onUpdateError(currentPlayer.getNickname(),"Wrong ID.");
            return false;
        }

        LeaderAction card = currentPlayer.getLeaderActionBox().get(pos);

        //carta già attivata
        if(card.getActivated()) {
            update.onUpdateError(currentPlayer.getNickname(),"Already activated.");
            return false;
        }

        //controllo risorse
        if(!card.getCost().checkResources(currentPlayer)) {
            update.onUpdateError(currentPlayer.getNickname(),"You don't have the requirements to activate this card.");
            return false;
        }
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

        //verifico se indice corretto
        if (pos < 0 || pos > 1) {
            update.onUpdateError(currentPlayer.getNickname(),"Wrong ID.");
            return false;
        }
        //verifico che non sia già stata attivata
        if (currentPlayer.getLeaderActionBox().get(pos).getActivated()) {
            update.onUpdateError(currentPlayer.getNickname(),"Already activated.");
            return false;
        }
        currentPlayer.getLeaderActionBox().remove(pos);
        try {
            currentPlayer.increasefaithMarker();
        } catch (ActiveVaticanReportException e) {
            try {
                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {
                if (isFinishedGame())
                    update.onUpdateFaithMarker(currentPlayer, playersList, false);
            }
        }
        update.onUpdateFaithMarker(currentPlayer, playersList, false);
        return true;
    }

    public void endTurn () {
        if (currentPlayer.getLeaderActionBox().size() == 2 && currentPlayer.getInitialResources() == 0) {
            turnPhase = TurnPhase.DOTURN;
            setCanDoProductionTrue();
            try {
                setCurrentPlayer();
            } catch (EndGameException e) {
                Player winner = playersList.get(getWinner());
                givefinalpoints();
                update.onUpdateWinnerMultiplayer(winner, playersList);
            }
            update.onUpdateCurrentPlayer(currentPlayer);
        }
        else
            update.onUpdateError(currentPlayer.getNickname(),"You can't end your turn.");
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

    public Tokens getCurrentToken () {
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

    public boolean getFinishedGame() {
        return finishedGame;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public UpdateCreator getUpdate() {
        return update;
    }


}

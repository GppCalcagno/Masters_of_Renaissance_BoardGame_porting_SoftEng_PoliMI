package it.polimi.ingsw.model.game;

import it.polimi.ingsw.Network.Server.UpdateCreator.UpdateCreator;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.io.IOException;
import java.util.*;

public class Game {

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

    /**
     * this attribute is the first player, useful for the start and the end of the game
     */
    private Player firstPlayer;

    /**
     * this attribute is a reference to the UpdateCreator
     */
    private UpdateCreator update;

    /**
     * this attribute is true when game is alredy starts and not yet end
     */
    private boolean isDuringGame;

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
        this.firstPlayer = null;
        this.update = update;
        this.isDuringGame=false;
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
        boolean isSomeoneOnline=false;

        for(Player p: playersList){
            if (p.getConnected()) {
                isSomeoneOnline = true;
                break;
            }
        }
        if(isSomeoneOnline) {
            int i = this.playersList.indexOf(this.currentPlayer);

            do {
                i++;
                if (i >= this.playersList.size()) i = 0;
                if (finishedGame && playersList.get(i).equals(firstPlayer))
                    throw new EndGameException();
            } while (!playersList.get(i).getConnected());

            currentPlayer = playersList.get(i);
        }
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
        isDuringGame=true;
        // gives 4 leaderActions to each player
        for (Player player : this.playersList) {
            for (int x = 0; x < 4; x++) {
                this.leaderCardDeck.givetoPlayer(0, player);
            }
        }
        // Set the current Player
        Collections.shuffle(this.playersList);
        currentPlayer=playersList.get(0);
        firstPlayer=currentPlayer;

        //set Initial Resources
        for(int i=0;i<playersList.size();i++){
            switch (i){
                case 1:playersList.get(i).setInitialResources(1); break;
                case 2:playersList.get(i).setInitialResources(1); break;
                case 3:playersList.get(i).setInitialResources(2); break;
                default: playersList.get(i).setInitialResources(0); break;
            }
        }

        // Set Initial FaithPoints
        for(int i = 2; i < this.playersList.size(); i++){
            try { this.playersList.get(i).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored) {} //Max 2 points -> No Vatican Report
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


    /**
     * this method is used to make the player choose the starting LeaderActions
     * @param i1 is the index of the first LeaderAction
     * @param i2 is the index of the first LeaderAction
     * @return true if the command was successful
     */
    public boolean chooseInitialLeaderCards (int i1, int i2) {
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

            if (currentPlayer.getInitialResources() == 0)  {
                currentPlayer.setTurnPhase(TurnPhase.ENDTURN);
            }

            update.onUpdateInitialLeaderCards(currentPlayer,currentPlayer.getLeaderActionBox());
            return true;
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You have already chosen your Leader cards.");
            return false;
        }
    }

    /**
     * this method is used to make the player choose the starting resources
     * @param initialResources resources chosen by the player
     * @return true if the command was successful
     */
    public boolean chooseInitialResources (List<String> initialResources) {
        if (currentPlayer.getGameState() != GameState.INITGAME || playersList.indexOf(currentPlayer) == 0) {
            update.onUpdateError(currentPlayer.getNickname(),"You can not choose the initial resources.");
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

        if (currentPlayer.getLeaderActionBox().size() == 2)  {
            currentPlayer.setTurnPhase(TurnPhase.ENDTURN);
        }

        update.onUpdateWarehouse(currentPlayer, false);
        return true;
    }


    /**
     * This method translates the TakeMarbles message
     * @param colrowextract indicates the row or the column to extract
     * @param numextract    the column or row's number
     * @return true if the extraction is right
     */
    public boolean extractionMarble(char colrowextract, int numextract) {
        if (currentPlayer.getGameState().equals(GameState.INGAME) && currentPlayer.getTurnPhase().equals(TurnPhase.DOTURN)) {
            try {
                if (marketStructure.extractMarbles(currentPlayer,colrowextract, numextract)) {
                    currentPlayer.setTurnPhase(TurnPhase.EXTRACTMARBLES);
                    update.onUpdateMarketTray(currentPlayer, colrowextract, numextract);
                    return true;
                }
                update.onUpdateError(currentPlayer.getNickname(), "Wrong message");
                return false;
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                update.onUpdateError(currentPlayer.getNickname(), "Wrong number of column or row");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this action at the moment.");
            return false;
        }
    }

    /**
     * This method permits to the client to add or to discard a caught marbles
     * @param choice 0 if the client want to add the marble to Warehouse, 1 to Extra chest, 2 if he want to discard the marble
     * @param indexWarehouse row's index in which the client want to add the marble
     * @return true if the add is right
     */
    public boolean manageMarble(char choice, int indexWarehouse, String resourceWhiteMarble) {
        if (!currentPlayer.getWarehouse().getBuffer().isEmpty()) {
            if (!currentPlayer.getLeaderCardEffectWhiteMarble().isEmpty()) {
                if (currentPlayer.getWarehouse().getBuffer().get(0).toString().equals("") && resourceWhiteMarble == null && choice != 'D') {
                    update.onUpdateError(currentPlayer.getNickname(),"You have not choose a resource.");
                    return false;
                }
                if (isRightResource(resourceWhiteMarble))
                    currentPlayer.chooseResourceWhiteMarbleEffect(resourceWhiteMarble);
            }
            switch (choice) {
                case 'W' :
                    if (indexWarehouse < 0 || indexWarehouse > 2) {
                        update.onUpdateError(currentPlayer.getNickname(),"You have put a wrong Warehouse's index.");
                    }
                    else {
                        try {
                            int faithMarker = currentPlayer.getFaithMarker();
                            if (currentPlayer.getWarehouse().getBuffer().get(0).addtoWarehouse(currentPlayer, indexWarehouse)) {
                                currentPlayer.getWarehouse().removeFirstfromBuffer();
                                if (faithMarker == currentPlayer.getFaithMarker())
                                    update.onUpdateWarehouse(currentPlayer, true);
                                else update.onUpdateFaithMarker(currentPlayer, playersList, true, getBlackCrossToken());
                            } else {
                                update.onUpdateError(currentPlayer.getNickname(), "You can not add a resource into the Warehouse.");
                            }
                        } catch (ActiveVaticanReportException activeVaticanReportException) {
                            currentPlayer.getWarehouse().removeFirstfromBuffer();
                            try {
                                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                            } catch (GameFinishedException e) {
                                if (!finishedGame && isFinishedGame())
                                    update.onUpdateGameFinished();
                            }
                            update.onUpdateFaithMarker(currentPlayer, playersList, true,getBlackCrossToken());
                        }
                    }
                    break;
                case 'E' :
                    if (currentPlayer.getWarehouse().getBuffer().get(0).addToExtraChest(currentPlayer)) {
                        currentPlayer.getWarehouse().removeFirstfromBuffer();
                        update.onUpdateWarehouse(currentPlayer, true);
                    }
                    else
                        update.onUpdateError(currentPlayer.getNickname(),"You can not add a resource into Extra Chest.");
                    break;
                case 'D' :
                    currentPlayer.getWarehouse().removeFirstfromBuffer();
                    try {
                        for (Player p : playersList) {
                            if (!p.equals(currentPlayer))
                                p.increasefaithMarker();
                        }
                        this.increaseLorenzoFaithtrack();
                    } catch (ActiveVaticanReportException e) {
                        try {
                            faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                        } catch (GameFinishedException gameFinishedException) {
                            if (!finishedGame && isFinishedGame())
                                update.onUpdateGameFinished();
                        }
                    }
                    update.onUpdateFaithMarker(currentPlayer, playersList, true,getBlackCrossToken());
                    break;
                default:
                    update.onUpdateError(currentPlayer.getNickname(),"Wrong choice.");
                    break;
            }
            if (currentPlayer.getWarehouse().getBuffer().isEmpty()) {
                currentPlayer.setTurnPhase(TurnPhase.ENDTURN);
            }
            return true;
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
        if (row1 < 0 || row1 > 2 || row2 < 0 || row2 > 2 || row1 == row2){
            update.onUpdateError(currentPlayer.getNickname(),"Wrong rows.");
            return false;
        }
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
        if (currentPlayer.getGameState().equals(GameState.INGAME) && currentPlayer.getTurnPhase().equals(TurnPhase.DOTURN )){
                if (column < 0 || column > 2) {
                    update.onUpdateError(currentPlayer.getNickname(), "Wrong column.");
                    return false;
                }
                //catch NullPointerException because the ID could be wrong
                try {
                    //check player has enough space in his slotDevCard and he has right resources
                    if (currentPlayer.getSlotDevCards().canBuyDevCard(developmentCardDeck.getDevCardFromID(ID),column) && developmentCardDeck.getDevCardFromID(ID).getCost().checkBuy(currentPlayer)) {
                        currentPlayer.setCurrentDevCardToBuy(developmentCardDeck.getDevCardFromID(ID));
                        currentPlayer.setColumnSlotBuyDev(column);
                        currentPlayer.setTurnPhase(TurnPhase.BUYDEVCARD);
                        update.onUpdateDevCardDeck(currentPlayer, currentPlayer.getCurrentDevCardToBuy());
                        developmentCardDeck.removeDevCards(currentPlayer.getCurrentDevCardToBuy().getID());

                        return true;
                    } else {
                        update.onUpdateError(currentPlayer.getNickname(), "You can not buy this card.");
                        return false;
                    }
                } catch (NullPointerException e) {
                    update.onUpdateError(currentPlayer.getNickname(), "Wrong ID");
                    return false;
                }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this action at the moment.");
            return false;
        }
    }

    /**
     * this method allow the players to choose the resources to pay the DevCard he selected
     * @param WarehouseRes a map of the resources from the warehouse
     * @param StrongboxRes a map of the resources from the strongbox
     * @param ExtrachestMap a map of the resources from the extrachest
     * @return true if the resources are correct
     */
    public boolean payResourcesToBuyDevCard (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        if (currentPlayer.getTurnPhase().equals(TurnPhase.BUYDEVCARD)) {
            if (!currentPlayer.getCurrentDevCardToBuy().getCost().checkResources(currentPlayer,WarehouseRes, StrongboxRes, ExtrachestMap,true)) {
                update.onUpdateError(currentPlayer.getNickname(), "Insufficient resources.");
                return false;
            }
            if (deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap)) {
                try {
                    if (currentPlayer.getSlotDevCards().insertCards(currentPlayer.getColumnSlotBuyDev(), currentPlayer.getCurrentDevCardToBuy())) {
                        update.onUpdateSlotDevCard(currentPlayer, currentPlayer.getCurrentDevCardToBuy(), currentPlayer.getColumnSlotBuyDev());
                        currentPlayer.setCurrentDevCardToBuy(null);
                        currentPlayer.setColumnSlotBuyDev(-1);
                        update.onUpdateResources(currentPlayer);
                        currentPlayer.setTurnPhase(TurnPhase.ENDTURN);
                        return true;
                    } else {
                        update.onUpdateError(currentPlayer.getNickname(), "You can't insert the Development card.");
                        return false;
                    }
                } catch (GameFinishedException e) {
                    update.onUpdateSlotDevCard(currentPlayer, currentPlayer.getCurrentDevCardToBuy(), currentPlayer.getColumnSlotBuyDev());
                    currentPlayer.setCurrentDevCardToBuy(null);
                    currentPlayer.setColumnSlotBuyDev(-1);
                    update.onUpdateResources(currentPlayer);
                    currentPlayer.setTurnPhase(TurnPhase.ENDTURN);
                    if (!finishedGame &&  isFinishedGame()) {
                        update.onUpdateGameFinished();
                        return true;
                    }
                }
            }
            update.onUpdateError(currentPlayer.getNickname(), "You can not delete these resources.");
            return false;
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this action at the moment.");
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
            int index;
            index=0;
            //qui ho hardcodato le risorse
            Resources[]  ResList={new Coins(),new Shields(), new Servants(), new Stones()};

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
            Resources[]  ResList={new Coins(),new Shields(), new Servants(), new Stones()};
            while(Resindex<ResList.length && !ResList[Resindex].toString().equals(res))Resindex++;

            int ChestIndex=0;
            while(!currentPlayer.getWarehouse().getLeaderCardEffect().get(ChestIndex).getResources().toString().equals(res))ChestIndex++;
            //so che l'indice c'è perchè faccio controllo prima

            try {
                currentPlayer.getWarehouse().getLeaderCardEffect().get(ChestIndex).updateResources( -ExtrachestMap.get(res));
            } catch (OverflowQuantityExcepions | NegativeQuantityExceptions ignored) {}//non uso i catch perchè controllo prima le risorse
        }
        return true;
    }

    /**
     * this method allow to activate the base production
     * @param r1 structure where first resource came from
     * @param reqRes1 first resource to pay
     * @param r2 structure where second resource came from
     * @param reqRes2 second resource to pay
     * @param chosenResource the resource wanted
     * @return true if the resource to be payed are correct
     */
    public boolean activeBaseProduction (char r1, String reqRes1, char r2, String reqRes2, String chosenResource) {
        if (currentPlayer.getGameState().equals(GameState.INGAME)) {
            if (!isRightResource(reqRes1) || !isRightResource(reqRes2) || !isRightResource(chosenResource)) {
                update.onUpdateError(currentPlayer.getNickname(),"Wrong resources.");
                return false;
            }
            if (currentPlayer.getTurnPhase().equals(TurnPhase.DOTURN) || currentPlayer.getTurnPhase().equals(TurnPhase.DOPRODUCTION)) {
                if (currentPlayer.getCanDoProduction(5)) {
                    Map<String, Integer> WarehouseRes = new HashMap<>();
                    Map<String, Integer> StrongboxRes = new HashMap<>();
                    Map<String, Integer> ExtrachestMap = new HashMap<>();
                    switch (r1) {
                        case 'W':
                            WarehouseRes.put(reqRes1, 1);
                            break;
                        case 'S':
                            StrongboxRes.put(reqRes1, 1);
                            break;
                        case 'E':
                            ExtrachestMap.put(reqRes1, 1);
                            break;
                        default:
                            update.onUpdateError(currentPlayer.getNickname(), "You can only choose between W (Warehouse), S (Strongbox), E (ExtraChest).");
                            return false;
                    }
                    int old = 0;
                    switch (r2) {
                        case 'W':
                            if (WarehouseRes.containsKey(reqRes2))
                                old = 1;
                            WarehouseRes.put(reqRes2, old + 1);
                            break;
                        case 'S':
                            if (StrongboxRes.containsKey(reqRes2))
                                old = 1;
                            StrongboxRes.put(reqRes2, old + 1);
                            break;
                        case 'E':
                            if (ExtrachestMap.containsKey(reqRes2))
                                old = 1;
                            ExtrachestMap.put(reqRes2, old + 1);
                            break;
                        default:
                            update.onUpdateError(currentPlayer.getNickname(), "You can only choose between W (Warehouse), S (Strongbox), E (ExtraChest).");
                            return false;
                    }
                    if (deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap)) {
                        currentPlayer.getSlotDevCards().baseProduction(chosenResource);
                        currentPlayer.setCanDoProduction(5,false);
                        currentPlayer.setTurnPhase(TurnPhase.DOPRODUCTION);
                        update.onUpdateResources(currentPlayer);
                        return true;
                    } else {
                        update.onUpdateError(currentPlayer.getNickname(), "You can not delete these resources to do this action.");
                        return false;
                    }
                } else {
                    update.onUpdateError(currentPlayer.getNickname(), "You have already do this action.");
                    return false;
                }
            } else {
                update.onUpdateError(currentPlayer.getNickname(), "You can not do this action.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this action at the moment.");
            return false;
        }
    }

    /**
     * this method is to avoid the case sensitive
     * @param resource the name of the resource
     * @return true if the letters are equals
     */
    public boolean isRightResource (String resource) {
        String[] allResources = {"Coins", "Servants", "Shields", "Stones"};
        for (String r : allResources){
            if (resource == null)
                return true;
            if (resource.equals(r) || resource.equals(r.toUpperCase()))
                return true;
        }
        return false;
    }

    /**
     * this method active the leadercard production
     * @param ID the card's ID
     * @param r where the resource that have to be payed came from
     * @param resource the resource that have to be payed
     * @return true if the payment is correct
     */
    public boolean activeLeaderCardProduction (String ID, char r, String resource) {
        if (currentPlayer.getGameState().equals(GameState.INGAME)) {
            if (currentPlayer.getTurnPhase().equals(TurnPhase.DOTURN) || currentPlayer.getTurnPhase().equals(TurnPhase.DOPRODUCTION)) {
                if (!isRightResource(resource)) {
                    update.onUpdateError(currentPlayer.getNickname(), "Wrong resource.");
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
                    update.onUpdateError(currentPlayer.getNickname(), "Wrong ID.");
                    return false;
                }
                if (!l.getActivated()) {
                    update.onUpdateError(currentPlayer.getNickname(), "This card is not activated.");
                    return false;
                }
                if (posLeaderBox < 0 || posLeaderBox > 1) {
                    update.onUpdateError(currentPlayer.getNickname(), "Error.");
                    return false;
                }
                if (!currentPlayer.getCanDoProduction(posLeaderBox + 3)) {
                    update.onUpdateError(currentPlayer.getNickname(), "You have already do this production.");
                    return false;
                }
                String reqResource = l.getResources().toString();
                Map<String, Integer> w = new HashMap<>();
                Map<String, Integer> s = new HashMap<>();
                Map<String, Integer> e = new HashMap<>();
                switch (r) {
                    case 'W':
                        w.put(reqResource, 1);
                        break;
                    case 'S':
                        s.put(reqResource, 1);
                        break;
                    case 'E':
                        e.put(reqResource, 1);
                        break;
                    default:
                        update.onUpdateError(currentPlayer.getNickname(), "You can only choose between W (Warehouse), S (Strongbox), E (ExtraChest).");
                        return false;
                }
                if (deleteRes(w, s, e)) {
                    try {
                        currentPlayer.increasefaithMarker();
                    } catch (ActiveVaticanReportException activeVaticanReportException) {
                        try {
                            faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                        } catch (GameFinishedException gameFinishedException) {
                            if (!finishedGame && isFinishedGame())
                                update.onUpdateGameFinished();
                        }
                    }
                    if (currentPlayer.getSlotDevCards().getBuffer().containsKey(resource)) {
                        currentPlayer.getSlotDevCards().getBuffer().put(resource, currentPlayer.getSlotDevCards().getBuffer().get(resource) + 1);
                    } else {
                        currentPlayer.getSlotDevCards().getBuffer().put(resource, 1);
                    }


                    update.onUpdateFaithMarker(currentPlayer, playersList, false, getBlackCrossToken());
                    update.onUpdateResources(currentPlayer);
                    currentPlayer.setCanDoProduction(posLeaderBox + 3,false);
                    currentPlayer.setTurnPhase(TurnPhase.DOPRODUCTION);
                    return true;
                } else {
                    update.onUpdateError(currentPlayer.getNickname(), "You can not delete these resources to do this action.");
                    return false;
                }
            } else {
                update.onUpdateError(currentPlayer.getNickname(), "You can do this action.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this action at the moment.");
            return false;
        }
    }

    /**
     * this method active a devcard production
     * @param col the column where the card is stored, in according with the rules the card is always that one on the top
     * @return true if the column is correct and the player has the resource to activate the card
     */
    public boolean activeDevCardProduction (int col) {
        if (currentPlayer.getGameState().equals(GameState.INGAME)) {
            if (currentPlayer.getTurnPhase().equals(TurnPhase.DOTURN) || currentPlayer.getTurnPhase().equals(TurnPhase.DOPRODUCTION)) {
                try {
                    if (currentPlayer.getCanDoProduction(col)) {
                        if (currentPlayer.getCurrentDevCardToProduce() != null) {
                            update.onUpdateError(currentPlayer.getNickname(), "You have to pay the card already activated.");
                            return false;
                        }
                        try {
                            if (currentPlayer.getSlotDevCards().getDevCards(col) == null) {
                                update.onUpdateError(currentPlayer.getNickname(), "This card does not exist.");
                                return false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            update.onUpdateError(currentPlayer.getNickname(), "Wrong column.");
                            return false;
                        }
                        DevelopmentCard card = currentPlayer.getSlotDevCards().getDevCards(col);
                        if (card.getCostProduction().checkResources(currentPlayer) && currentPlayer.getSlotDevCards().checkUsage(card)) {
                            currentPlayer.setCurrentDevCardToProduce(currentPlayer.getSlotDevCards().getDevCards(col));
                            currentPlayer.setCanDoProduction(col,false);
                            currentPlayer.setTurnPhase(TurnPhase.DOPRODUCTION);
                            update.onUpdateActivatedDevCardProduction(currentPlayer, card.getID());
                            return true;
                        } else {
                            update.onUpdateError(currentPlayer.getNickname(), "You have not the resources to activate this production power.");
                            return false;
                        }
                    } else {
                        update.onUpdateError(currentPlayer.getNickname(), "You have already do this production.");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    update.onUpdateError(currentPlayer.getNickname(), "Wrong column.");
                    return false;
                }
            } else {
                update.onUpdateError(currentPlayer.getNickname(), "You can do this action.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this action at the moment.");
            return false;
        }
    }

    /**
     * this method is called when a player wants to activate a devcard production and has to pay for it
     * @param WarehouseRes a map of the resources use for the payment that came from the warehouse
     * @param StrongboxRes a map of the resources use for the payment that came from the strongbox
     * @param ExtrachestMap a map of the resources use for the payment that came from the extrachest
     * @return true if the resources are correct
     */
    public boolean payResourcesForDevCardProduction (Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
            DevelopmentCard card = currentPlayer.getCurrentDevCardToProduce();

            //controlla se risorse corrispondono a costo carta
            if(!card.getCostProduction().checkResources(currentPlayer, WarehouseRes,StrongboxRes,ExtrachestMap, false)) {
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
            currentPlayer.setCurrentDevCardToProduce(null);
            update.onUpdateResources(currentPlayer);
            return true;
    }

    /**
     * this method allow the payment when players buy a devcard
     * @param WarehouseRes a map of the resources use for the payment that came from the warehouse
     * @param StrongboxRes a map of the resources use for the payment that came from the strongbox
     * @param ExtrachestMap a map of the resources use for the payment that came from the extrachest
     * @return
     */
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

    /**
     * this method is called from the player to empty his buffer and doesn't allow any other production
     * @return true is buffer is empty
     */
    public boolean endProduction () {
        if (!currentPlayer.getSlotDevCards().getBuffer().isEmpty()) {
           int currentPlayerFaithMarker = currentPlayer.getFaithMarker();
           emptyBuffer();
           if (currentPlayer.getFaithMarker() != currentPlayerFaithMarker)
               update.onUpdateFaithMarker(currentPlayer, playersList, false, getBlackCrossToken());
            currentPlayer.setTurnPhase(TurnPhase.ENDTURN);
           update.onUpdateStrongBox(currentPlayer);
           return true;
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(),"You have not activated any production.");
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
                    for (int i = 0; i < buffer.get(res); i++) {
                        try {
                            currentPlayer.increasefaithMarker();
                        } catch (ActiveVaticanReportException e) {
                            try {
                                faithTrack.checkPopeSpace(playersList, getBlackCrossToken());
                            } catch (GameFinishedException gameFinishedException) {
                                if (!finishedGame && isFinishedGame()) update.onUpdateGameFinished();
                                    update.onUpdateFaithMarker(currentPlayer, playersList, false, getBlackCrossToken());
                            }
                        }
                    }
                }
                else currentPlayer.getStrongbox().updateResources(res,buffer.get(res));
            } catch (NegativeQuantityExceptions ignored) {} //so che sono tutte positive
        }
        buffer.clear();
        return true;
    }

    /**
     * this method allow to change the status of leadercard
     * @param ID leadercard's ID
     * @param choice 0 to discard 1 to activate
     * @return true is the status change correctly
     */
    public boolean updateLeaderCard (String ID, int choice) {
        if (!currentPlayer.getLeaderActionBox().isEmpty()) {
            if (currentPlayer.getGameState().equals(GameState.INGAME)) {
                if (choice == 0) {
                    if (discardLeaderAction(ID)) {
                        update.onUpdateLeaderCard(currentPlayer, ID, false);
                        return true;
                    } else return false;
                } else if (choice == 1) {
                    if (activeLeaderAction(ID)) {
                        update.onUpdateLeaderCard(currentPlayer, ID, true);
                        if (ID.equals("LCCL1") || ID.equals("LCCL2") || ID.equals("LCCL3") || ID.equals("LCCL4"))
                            update.onUpdateWarehouse(currentPlayer, false);
                        return true;
                    } else return false;
                } else {
                    update.onUpdateError(currentPlayer.getNickname(), "Wrong choice.");
                    return false;
                }
            } else {
                update.onUpdateError(currentPlayer.getNickname(), "You can not do this action at the moment.");
                return false;
            }
        }
        else {
            update.onUpdateError(currentPlayer.getNickname(), "You can't do this.");
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
        card.doSpecialAbility(currentPlayer);

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
                if (!finishedGame &&  isFinishedGame()) update.onUpdateGameFinished();
                    update.onUpdateFaithMarker(currentPlayer, playersList, false,getBlackCrossToken());
            }
        }
        update.onUpdateFaithMarker(currentPlayer, playersList, false,getBlackCrossToken());
        return true;
    }

    /**
     * this is the last action a player can does, the turn ends and start the turn of another player if the
     * game is online or Lorenzo's turn if the game is in singleplayer
     * @param onDisconnect if player disconnects
     */
    public void endTurn(boolean onDisconnect) {

        boolean canEndTurn = onDisconnect;
        if(!onDisconnect) {
            if (currentPlayer.getGameState().equals(GameState.INITGAME) && currentPlayer.getLeaderActionBox().size() <= 2 && currentPlayer.getInitialResources() == 0) {
                currentPlayer.setGameState(GameState.INGAME);
                currentPlayer.setTurnPhase(TurnPhase.DOTURN);
                canEndTurn = true;
            } else {
                if (currentPlayer.getTurnPhase().equals(TurnPhase.ENDTURN)) {
                    canEndTurn = true;
                    currentPlayer.setTurnPhase(TurnPhase.DOTURN);
                    currentPlayer.setCanDoProductionTrue();
                }
            }
        }
        
        if(canEndTurn){
            try {
                setCurrentPlayer();
            } catch (EndGameException e) {
                givefinalpoints();
                Player winner = playersList.get(getWinner());
                update.onUpdateWinnerMultiplayer(winner, playersList);
                isDuringGame=false;
                return;  //finisci tutto

            }
            update.onUpdateCurrentPlayer(currentPlayer);
        }
        else{
            update.onUpdateError(currentPlayer.getNickname(),"Take your turn before you finish it");
        }
    }

    /**
     * this method converts a string into an object resource
     * @param stringResource the string to convert
     * @return the resource object
     */
    public Resources convertStringToResource (String stringResource) {
        Resources[] resources = {new Coins(), new Servants(), new Shields(), new Stones()};
        for (Resources r : resources) {
            if (stringResource.equals(r.toString()) || stringResource.equals(r.toString().toUpperCase()))
                return r;
        }
        return null;
    }

    /**
     * this method show the board of another player
     * @param name otherplayer's name
     */
    public void onReqOtherPlayer(String name){
        int i=0;

        while (i<playersList.size() && !playersList.get(i).getNickname().toUpperCase().equals(name.toUpperCase())) i++;

        if(i<playersList.size() && playersList.get(i).getNickname().toUpperCase().equals(name.toUpperCase()))
            update.onReqOtherPlayer(currentPlayer,playersList.get(i));
        else
            update.onUpdateError(currentPlayer.getNickname(),"There is no player with that name!");
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

    public void increaseLorenzoFaithtrack() throws ActiveVaticanReportException {}

    public void playLorenzoTurn () throws ActiveVaticanReportException {
    }

    public Tokens getCurrentToken () {
        return null;
    }

    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public UpdateCreator getUpdate() {
        return update;
    }

    public boolean isDuringGame() {
        return isDuringGame;
    }

    public void setDuringGame(boolean duringGame) {
        isDuringGame = duringGame;
    }

    public void cheat() {
        try {
            currentPlayer.getStrongbox().updateResources("Coins", 100);
            currentPlayer.getStrongbox().updateResources("Stones", 100);
            currentPlayer.getStrongbox().updateResources("Shields", 100);
            currentPlayer.getStrongbox().updateResources("Servants", 100);
        } catch (NegativeQuantityExceptions ignored) {}

        update.onUpdateStrongBox(currentPlayer);
    }

}

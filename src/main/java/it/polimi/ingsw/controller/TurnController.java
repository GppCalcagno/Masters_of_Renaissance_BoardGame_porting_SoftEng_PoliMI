package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnController {
    private Game game;
    private boolean[] AlreadyActivedDevCard;
    private boolean[] ProductionPowers;
    private DevelopmentCard CurrentDevCardPurchase;
    private int currentDevCardProduction;
    private int numPlayersCount;

    /**
     * this is the controller of TurnController
     * @throws IOException
     */
    public TurnController() throws IOException {
        AlreadyActivedDevCard = new boolean[3];
        ProductionPowers = new boolean[3];
        resetAlreadyActivedDevCard();
        resetProductionPowers();
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
     * This method adds a new player to the Game if there is not already the same nickname
     * @param name nickname of the new player
     * @return true
     */
    public boolean addNewPlayer (String name) {
        for (Player player : game.getPlayersList()) {
            if (name.equals(player.getNickname()))
                return false;
        }
        game.addPlayersList(new Player(name));
        return true;
    }

    /**
     * this method called by the player allow to select from the DevCardDeck the card the player wants to buy
     * @param ID of the dev card
     * @return true if there are no errors
     */
    public boolean selectDevCard(String ID){
        try{
            CurrentDevCardPurchase =game.getDevelopmentCardDeck().getDevCardFromID(ID);
        } catch (NullPointerException e){ return false;}

        //controllo che il giocatore abbia spazio nello SlotDevCard e che abbia le risorse necessarie
        return (game.getCurrentPlayer().getSlotDevCards().maxLevelPurchase(CurrentDevCardPurchase) && CurrentDevCardPurchase.getCost().checkResources(game.getCurrentPlayer()));
    }

    /**
     * this method called by the player allow to insert the card just bought into it's SlotDevCards
     * @param col column for SlotDevCards
     * @return true if there are no error
     */
    public boolean insertCard(int col) {
        if (col >= 0 && col <= 2) {
            try {
                return game.getCurrentPlayer().getSlotDevCards().insertCards(col, CurrentDevCardPurchase);
            } catch (GameFinishedException e) {
                return game.isFinishedGame();
            }
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
     * This method returns the current player's SlotDevCards with String objects instead of Development card objects
     * @return a matrix of String objects
     */
    public String[][] updateSlotDevCards () {
        String[][] slotDevCards = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slotDevCards[i][j] = game.getCurrentPlayer().getSlotDevCards().getBoardDevCards()[i][j].getID();
            }
        }

        return slotDevCards;
    }

    /**
     * this method called by the player allow to activate a leaderActionCard
     * @param ID refer which card must to be activated, 0 for the first, 1 for the second
     * @return true if the card is activated
     */
    public boolean activeLeaderAction(String ID){
        int pos = -1;
        for (LeaderAction l : game.getCurrentPlayer().getLeaderActionBox()) {
            if (l.getID().equals(ID)) {
                pos = game.getCurrentPlayer().getLeaderActionBox().indexOf(l);
            }
        }

        //controllo se posizione valida
        if(pos < 0 || pos>1)
            return false;

        LeaderAction card= game.getCurrentPlayer().getLeaderActionBox().get(pos);

        //carta già attivata
        if(card.getActivated())
            return true;

        //controllo risorse
        if(!card.getCost().checkResources(game.getCurrentPlayer()))
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
        for (LeaderAction l : game.getCurrentPlayer().getLeaderActionBox()) {
            if (l.getID().equals(ID)) {
                pos = game.getCurrentPlayer().getLeaderActionBox().indexOf(l);
            }
        }

        int size = game.getCurrentPlayer().getLeaderActionBox().size();
        //verifico se indice corretto
        if (pos > size)
            return false;
        //verifico che non sia già stata attivata
        if (game.getCurrentPlayer().getLeaderActionBox().get(pos).getActivated())
            return false;
        try {
            game.getCurrentPlayer().increasefaithMarker();
        } catch (ActiveVaticanReportException e) {
            try {
                game.getFaithTrack().checkPopeSpace(game.getPlayersList(),game.getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {
                return game.isFinishedGame();
            }
        }
        return true;
    }

    public boolean chooseResourcesBaseProduction(Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap) {
        if (!ProductionPowers[1]) {
            int countResources = 0;
            for (int i : WarehouseRes.values()) {
                countResources += i;
            }
            for (int i : StrongboxRes.values()) {
                countResources += i;
            }
            for (int i : ExtrachestMap.values()) {
                countResources += i;
            }
            if (countResources == 2) {
                return deleteRes(WarehouseRes, StrongboxRes, ExtrachestMap);
            }
            return false;
        }
        return false;
    }

    public boolean activeBaseProduction (String resource) {
        if (!ProductionPowers[1]) {
            game.getCurrentPlayer().getSlotDevCards().baseProduction(resource);
            ProductionPowers[1] = true;
            return true;
        }
        return false;
    }

    /**
     * this method is called by client to active a DevCardProduction
     * @param col is the top item of the selected column of SlotDevCard
     * @return true if the player could active DevCardProduction
     */
    public boolean activeProductionDevCard(int col){
        if (!ProductionPowers[0]) {
            if (AlreadyActivedDevCard[col])
                return false;

            DevelopmentCard card = game.getCurrentPlayer().getSlotDevCards().getDevCards(col);

            if (card.getCostProduction().checkResources(game.getCurrentPlayer()) && game.getCurrentPlayer().getSlotDevCards().checkUsage(card)) {
                currentDevCardProduction=col;
                return true;
            }
            return false;
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
    public boolean chooseResourcesDevCardProduction(Map<String,Integer> WarehouseRes, Map<String,Integer> StrongboxRes, Map<String,Integer> ExtrachestMap){
        DevelopmentCard card= game.getCurrentPlayer().getSlotDevCards().getDevCards(currentDevCardProduction);

        //controlla se risorse corrispondono a costo carta
        if(card.getCostProduction().checkResources(WarehouseRes,StrongboxRes,ExtrachestMap))return false;

        //elimino le risorse
        if(!deleteRes(WarehouseRes,StrongboxRes,ExtrachestMap)) return false;

        //assegno le risorse al buffer giocatore
        game.getCurrentPlayer().getSlotDevCards().cardProduction(card);

        AlreadyActivedDevCard[currentDevCardProduction]=true;

        if (ProductionDevCardisAlreadyActivated())
            ProductionPowers[0] = true;

        return true;
    }

    public boolean ProductionDevCardisAlreadyActivated() {
        for (boolean b : AlreadyActivedDevCard) {
            if (!b)
                return false;
        }
        return true;
    }

    public boolean ActiveLeaderCardProduction (int indexExtraProduction, int WareStrongChest, String resource) {
        Resources resourceConverted = null;
        Resources[] resourcesVet = {
                new Coins(),
                new Servants(),
                new Shields(),
                new Stones()
        };
        for (Resources r : resourcesVet) {
            if (resource.equals(r.toString()))
                resourceConverted = r;
        }
        if (resourceConverted == null)
            return false;

        if (!ProductionPowers[2]) {
            if (game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().isEmpty())
                return false;
            else {
                if (game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction) == null)
                    return false;
                else {
                    switch (WareStrongChest) {
                        case 0 :
                            if (game.getCurrentPlayer().getWarehouse().delete(game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).getResources())) {
                                try {
                                    game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).activeExtraProduction(game.getCurrentPlayer(), resourceConverted);
                                } catch (ActiveVaticanReportException e) {
                                    try {
                                        game.getFaithTrack().checkPopeSpace(game.getPlayersList(), game.getBlackCrossToken());
                                    } catch (GameFinishedException gameFinishedException) {
                                        return game.isFinishedGame();
                                    }
                                    return true;
                                }
                                return true;
                            }
                        case 1 :
                            try {
                                game.getCurrentPlayer().getStrongbox().updateResources(game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).getResources(), -1);
                            } catch (NegativeQuantityExceptions negativeQuantityExceptions) {
                                return false;
                            }
                            try {
                                game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).activeExtraProduction(game.getCurrentPlayer(), resourceConverted);
                            } catch (ActiveVaticanReportException e) {
                                try {
                                    game.getFaithTrack().checkPopeSpace(game.getPlayersList(), game.getBlackCrossToken());
                                } catch (GameFinishedException gameFinishedException) {
                                    return game.isFinishedGame();
                                }
                            }
                            return true;
                        case 2 :
                            for (ExtraChest chest : game.getCurrentPlayer().getWarehouse().getLeaderCardEffect()) {
                                if (chest.getResources().equals(game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).getResources())) {
                                    try {
                                        chest.updateResources(-1);
                                    } catch (OverflowQuantityExcepions ignored) {}
                                    catch (NegativeQuantityExceptions negativeQuantityExceptions) {
                                        return false;
                                    }
                                    try {
                                        game.getCurrentPlayer().getSlotDevCards().getLeaderCardEffect().get(indexExtraProduction).activeExtraProduction(game.getCurrentPlayer(), resourceConverted);
                                    } catch (ActiveVaticanReportException e) {
                                        try {
                                            game.getFaithTrack().checkPopeSpace(game.getPlayersList(), game.getBlackCrossToken());
                                        } catch (GameFinishedException gameFinishedException) {
                                            return game.isFinishedGame();
                                        }
                                    }
                                }
                            }
                        default:
                            return false;
                    }
                }
            }
        }
        return false;
    }

    public int updateFaithMarker () {
        return game.getCurrentPlayer().getFaithMarker();
    }

    public Map<String, boolean[]> updatePlayersPopFavoriteTiles() {
        Map<String, boolean[]> playersPopFavoriteTiles = new HashMap<>();

        for (Player p : game.getPlayersList()) {
            playersPopFavoriteTiles.put(p.getNickname(), p.getPopsfavortiles());
        }

        return playersPopFavoriteTiles;
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
            while(!game.getCurrentPlayer().getWarehouse().getLeaderCardEffect().get(ChestIndex).getResources().toString().equals(res))ChestIndex++;
            //so che l'indice c'è perchè faccio controllo prima

            try {
                game.getCurrentPlayer().getWarehouse().getLeaderCardEffect().get(ChestIndex).updateResources( -ExtrachestMap.get(res));
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

    public List<String> updateExtractedMarbles () {
        List<String> bufferStringList = new ArrayList<>();
        for (Marbles m : game.getMarketStructure().getBuffer()) {
            bufferStringList.add(m.toString());
        }
        return bufferStringList;
    }

    /**
     * This method permits to the client to add or to discard a caught marbles
     * @param choice  1 if the client want to add the marble, 0 if he want to discard the marble
     * @param indexwarehouse row's index in which the client want to add the marble
     * @return true if the add is right
     */
    public boolean AddDiscardMarble(boolean choice, int indexwarehouse) {
        if (choice) {
            try {
                if (game.getMarketStructure().getBuffer().get(0).addtoWarehouse(game.getCurrentPlayer(), indexwarehouse)) {
                    game.getMarketStructure().getBuffer().remove(0);
                    return true;
                }
                else return false;
            } catch (ActiveVaticanReportException activeVaticanReportException) {
                game.getMarketStructure().getBuffer().remove(0);
                try {
                    game.getFaithTrack().checkPopeSpace(game.getPlayersList(), 0);
                } catch (GameFinishedException e) {
                    return game.isFinishedGame();
                }
                return true;
            }
        } else {
            game.getMarketStructure().discardMarbles(game.getMarketStructure().getBuffer().get(0));
            for (Player p : game.getPlayersList()) {
                if (p.equals(game.getCurrentPlayer())) {
                    try {
                        p.increasefaithMarker();
                    } catch (ActiveVaticanReportException e) {
                        try {
                            game.getFaithTrack().checkPopeSpace(game.getPlayersList(), 0);
                        } catch (GameFinishedException gameFinishedException) {
                            return game.isFinishedGame();
                        }
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
     * This method set as first Resource the one chosen from the current player to convert a White marble with this
     * @param i is the resource's index in the leaderCardEffectWhiteMarble list in Player
     * @return true if the action is allowed
     */
    public boolean selectTransformationWhiteMarble(int i) {
        if (game.getCurrentPlayer().getLeaderCardEffectWhiteMarble().isEmpty())
            return true;

        if (game.getCurrentPlayer().getLeaderCardEffectWhiteMarble().get(i) == null)
            return false;

        if (i == 1) {
            Resources r = game.getCurrentPlayer().getLeaderCardEffectWhiteMarble().get(0);
            game.getCurrentPlayer().getLeaderCardEffectWhiteMarble().add(0, game.getCurrentPlayer().getLeaderCardEffectWhiteMarble().get(1));
            game.getCurrentPlayer().getLeaderCardEffectWhiteMarble().add(1, r);
            return true;
        }
        return true;
    }

    public List<String> updateLeaderCardEffectWhiteMarble(){
        List<String> list = new ArrayList<>();
        for (Resources r : game.getCurrentPlayer().getLeaderCardEffectWhiteMarble()) {
            list.add(r.toString());
        }
        return list;
    }

    /**
     * This method returns a copy of the current player's warehouse with Resources coverted to String
     * @return a matrix of String
     */
    public String[][] updateWarehouse () {
        String[][] warehouse = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getCurrentPlayer().getWarehouse().getWarehouse()[i][j] == null)
                    warehouse[i][j] = null;
                else warehouse[i][j] = game.getCurrentPlayer().getWarehouse().getWarehouse()[i][j].toString();
            }
        }

        return warehouse;
    }

    public Map<String, Integer> updateExtraChest () {
        Map<String, Integer> extraChest = new HashMap<>();

        for (ExtraChest chest : game.getCurrentPlayer().getWarehouse().getLeaderCardEffect()) {
            extraChest.put(chest.getResources().toString(), chest.getnum());
        }

        return extraChest;
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

    //lista id carte leader di ogni giocatore
    public List<List<String>> leaderCardsToChoose() {
        List<List<String>> Cardslist = new ArrayList<>();
        for (Player p : game.getPlayersList()) {
            List<String> cardsPlayerList = new ArrayList<>();
            for (Card c : p.getLeaderActionBox()) {
                cardsPlayerList.add(c.getID());
            }
            Cardslist.add(cardsPlayerList);
        }
        return Cardslist;
    }

    //lista nomi giocatori in ordine
    public List<String> playersNameList() {
        List<String> playersNameList = new ArrayList<>();
        for (Player p : game.getPlayersList()) {
            playersNameList.add(p.getNickname());
        }
        return playersNameList;
    }

    //SuperMatrice con gli id delle carte sviluppo
    public String[][][] devCardDeckMethod () {
        String[][][] devCardDeckString = new String[3][4][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    devCardDeckString[i][j][k] = game.getDevelopmentCardDeck().getDevelopmentCardDeck()[i][j][k].getID();
                }
            }
        }
        return devCardDeckString;
    }

    //vista delle biglie
    public String[][] marketTrayMethod() {
        String [][] marketTrayView = new String[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marketTrayView[i][j] = game.getMarketStructure().getMarketTray()[i][j].toString();
            }
        }
        return marketTrayView;
    }

    //marbles rimanente
    public String remainingMarbleMethod() {
        return game.getMarketStructure().getRemainingMarble().toString();
    }

    /**
     * This method lets the potential second, third and fourth players choose the given resource(s)
     * @param resourcesList chosen resource(s)'s list
     * @return false if there is only one player
     */
    public boolean ChooseResourcesFirstTurn (List<String> resourcesList){
        Resources[] resourcesVet = {
                new Coins(),
                new Servants(),
                new Shields(),
                new Stones()
        };
        List<Resources> resourcesListStub = new ArrayList<>();

        for (String resourceString : resourcesList) {
            for (Resources resourcesInVet : resourcesVet) {
                if (resourceString.equals(resourcesInVet.toString())) {
                    resourcesListStub.add(resourcesInVet);
                }
            }
        }

        if (game.getPlayersList().indexOf(game.getCurrentPlayer()) == 1 || game.getPlayersList().indexOf(game.getCurrentPlayer()) == 2) {
            game.getCurrentPlayer().getWarehouse().checkInsertion(0, resourcesListStub.get(0));
            //chiamato al primo turno = warehouse vuoto = inserimento consentito
            return true;
        }
        else if (game.getPlayersList().indexOf(game.getCurrentPlayer()) == 3){
            if (resourcesListStub.get(0).toString().equals(resourcesListStub.get(1).toString())) {
                game.getCurrentPlayer().getWarehouse().checkInsertion(2, resourcesListStub.get(0));
                game.getCurrentPlayer().getWarehouse().checkInsertion(2, resourcesListStub.get(1));
            }
            else {
                game.getCurrentPlayer().getWarehouse().checkInsertion(0, resourcesListStub.get(0));
                game.getCurrentPlayer().getWarehouse().checkInsertion(1, resourcesListStub.get(1));
            }
            //chiamato al primo turno = warehouse vuoto = inserimento consentito
            return true;
        }
        return false;
    }

    public Map<String,Integer> updateStrogbox () {
        return game.getCurrentPlayer().getStrongbox().getChest();
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

    public void resetAlreadyActivedDevCard() {
        for (boolean b : AlreadyActivedDevCard) {
            b = false;
        }
    }

    public void resetProductionPowers() {
        for (boolean b : ProductionPowers) {
            b = false;
        }
    }

    /**
     * This method empties the StrongBox buffer and sets the new current player.
     */
    public void endTurn () throws EndGameException {
        try {
            game.playLorenzoTurn();
        } catch (ActiveVaticanReportException e) {
            try {
                game.getFaithTrack().checkPopeSpace(game.getPlayersList(),game.getBlackCrossToken());
            } catch (GameFinishedException gameFinishedException) {
                game.isFinishedGame();
            }
        }
        game.setCurrentPlayer();
    }

    public String getCurrentToken() {
        return game.getCurrentToken();
    }

    public Map<String, Integer> updateFinalPoints () {
        Map<String, Integer> playersFinalPoints = new HashMap<>();

        for (Player p : game.getPlayersList()) {
            playersFinalPoints.put(p.getNickname(), p.getVictoryPoints());
        }

        return playersFinalPoints;
    }

    /**
     * disconnect player from the game
     * @param name is the name of the disconnected player
     */
    public void disconnectPlayer(String name){
        for(Player p: game.getPlayersList()){
            if(p.getNickname().equals(name)){
                p.setConnected(false);
            }
        }
    }

    /**
     * This method calls the Game's startgame method.
     */
    public void startGame (){
        game.startgame();
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

    public DevelopmentCard getCurrentDevCardPurchase() {
        return CurrentDevCardPurchase;
    }
}

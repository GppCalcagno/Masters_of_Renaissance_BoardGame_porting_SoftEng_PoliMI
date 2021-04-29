package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGame;

import java.io.IOException;
import java.util.List;

public class GiovanniController {
    private Game game;
    private int numPlayersCount;

    public GiovanniController() throws IOException {
        numPlayersCount = 0;
    }

    /**
     * This method translates the TakeMarbles message
     *
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
     *
     * @param choice         1 if the client want to add the marble, 0 if he want to discard the marble
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
     * This method verifies if the player's number decided from the first player is right
     * @param numPlayers int passed by the first player
     * @return true if numPlayers is < 5
     */
    public boolean VerifyNumPlayers (int numPlayers) throws IOException {
        switch(numPlayers) {
            case 1 :
                numPlayersCount = 1;
                game = new SinglePlayerGame();
                return true;
            case 2 :
                numPlayersCount = 2;
                game = new Game();
                return true;
            case 3 :
                numPlayersCount = 3;
                game = new Game();
                return true;
            case 4 :
                numPlayersCount = 4;
                game = new Game();
                return true;
            default:
                return false;
        }
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

    public Game getGame() {
        return game;
    }

    public int getNumPlayersCount() {
        return numPlayersCount;
    }
}
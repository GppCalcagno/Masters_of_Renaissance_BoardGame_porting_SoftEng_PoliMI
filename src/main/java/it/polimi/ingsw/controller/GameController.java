package it.polimi.ingsw.controller;

import it.polimi.ingsw.Network.message.*;
import it.polimi.ingsw.model.exceptions.GameFinishedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameState gameState;

    private TurnController turnController;

    private InputController inputController;

    private List<MessageType> nextState;

    public GameController () throws IOException {
        this.turnController = new TurnController();
        gameState = GameState.LOGIN;
        this.inputController = new InputController(turnController);
        this.nextState = new ArrayList<>();
        nextState.add(MessageType.LOGIN);
    }

    public void actionGame (Message receivedMessage) throws IOException {
        for (MessageType type : nextState) {
            if (type.equals(receivedMessage.getMessageType())) {
                switch (gameState) {
                    case LOGIN:
                        loginPhase(receivedMessage);
                        break;
                    case INITGAME:
                        initGamePhase(receivedMessage);
                        break;
                    case INGAME:
                        inGame(receivedMessage);
                        break;
                    default:
                        //stato non valido
                        break;
                }
            }
        }
    }

    public void loginPhase (Message message) throws IOException {
        switch (message.getMessageType()) {
            case LOGIN:
                loginMethod((MessageLogin) message);
            case NUMPLAYERS:
                numPlayersMethod((MessageNumPlayers) message);
            default:
                break;
        }
    }

    public void initGamePhase (Message message) {
        switch (message.getMessageType()) {
            case CHOOSELEADERCARDS:
                if (verifyCurrentPlayer(message.getNickname())) {
                    chooseLeaderCardsMethod((MessageChooseLeaderCards) message);
                }
            case CHOOSERESOURCESFIRSTTURN:
                if (verifyCurrentPlayer(message.getNickname())) {
                    chooseResourcesFirstTurnMethod((MessageChooseResourcesFirstTurn) message);
                }
            case ENDTURN:
                if (verifyCurrentPlayer(message.getNickname())) {

                }
            default:
                break;
        }
    }

    public void inGame (Message message) {
        switch (message.getMessageType()) {
            case EXTRACTIONMARBLES:
                if (verifyCurrentPlayer(message.getNickname())) {
                    extractionMarblesMethod((MessageExtractionMarbles) message);
                }
            case ADDDISCARDMARBLES:
                if (verifyCurrentPlayer(message.getNickname())) {
                    addDiscardMarblesMethod((MessageAddDiscardMarble) message);
                }
            case EXCHANGEWAREHOUSE:
                if (verifyCurrentPlayer(message.getNickname())) {
                    exchangeWarehouseMethod((MessageExchangeWarehouse) message);
                }
            case SELECTDEVCARD:
                if (verifyCurrentPlayer(message.getNickname())) {
                    selectDevCardMethod((MessageSelectDevCard) message);
                }
            case CHOOSERESOURCESPURCHASEDEVCARD:
                if (verifyCurrentPlayer(message.getNickname())) {
                    chooseResourcesForPurchase((MessageChooseResourcesPurchaseDevCard) message);
                }
            case INSERTCARD:
                if (verifyCurrentPlayer(message.getNickname())) {
                    insertCardMethod((MessageInsertCard) message);
                }
            case CHOOSERESOURCESBASEPRODUCTION:
                if (verifyCurrentPlayer(message.getNickname())) {
                    chooseResourcesBaseProductionMethod((MessageChooseResourcesBaseProduction) message);
                }
            case CHOSENRESOURCEBASEPRODUCTION:
                if (verifyCurrentPlayer(message.getNickname())) {
                    chosenResourceBaseProductionMethod((MessageChosenResourceBaseProduction) message);
                }
            case ACTIVEPRODUCTIONDEVCARD:
                if (verifyCurrentPlayer(message.getNickname())) {
                    activeProductionDevCard((MessageActiveProductionDevCard) message);
                }
            case CHOOSERESOURCESDEVCARDPRODUCTION:
                if (verifyCurrentPlayer(message.getNickname())) {
                    chooseResourcesDevCardProductionMethod((MessageChooseResourcesDevCardProduction) message);
                }
            case ACTIVELEADERCARDPRODUCTION:
                if (verifyCurrentPlayer(message.getNickname())) {
                    activeLeaderCardProductionMethod((MessageActiveLeaderCardProduction) message);
                }
            case UPDATESTATELEADERACTION:
            case ENDTURN:
            default:
                break;
        }
    }

    public void loginMethod(MessageLogin messageLogin) {
        if (turnController.getGame().getPlayersList().isEmpty()) {
            turnController.addNewPlayer(messageLogin.getNickname());
            nextState.clear();
            nextState.add(MessageType.NUMPLAYERS);
        }
        else {
            if (turnController.getGame().getPlayersList().size() < turnController.getNumPlayersCount()) {
                if (turnController.addNewPlayer(messageLogin.getNickname())) {
                    if (turnController.getGame().getPlayersList().size() == turnController.getNumPlayersCount()) {
                        nextState.clear();
                        nextState.add(MessageType.CHOOSELEADERCARDS);
                        gameState = GameState.INITGAME;
                        turnController.startGame();
                    }
                }
            }
        }
    }

    public void numPlayersMethod (MessageNumPlayers messageNumPlayers) {
        try {
            if (turnController.VerifyNumPlayers(messageNumPlayers.getNumPlayers())) {
                nextState.clear();
                if (turnController.getNumPlayersCount() == 1) {
                    nextState.add(MessageType.CHOOSELEADERCARDS);
                    gameState = GameState.INITGAME;
                    turnController.startGame();
                }
                else
                    nextState.add(MessageType.LOGIN);
            }
        } catch (IOException e) {
            //se capita questo errore non ci sono le carte
        }
    }

    public void chooseLeaderCardsMethod (MessageChooseLeaderCards messageChooseLeaderCards) {
        if (turnController.ChooseLeaderCards(messageChooseLeaderCards.getI1(), messageChooseLeaderCards.getI2())) {
            nextState.clear();
            nextState.add(MessageType.CHOOSERESOURCESFIRSTTURN);
        }
    }

    public void chooseResourcesFirstTurnMethod (MessageChooseResourcesFirstTurn messageChooseResourcesFirstTurn) {
        if (turnController.ChooseResourcesFirstTurn(messageChooseResourcesFirstTurn.getResourcesList())) {
            nextState.clear();
            nextState.add(MessageType.ENDTURN);
        }
    }

    public void extractionMarblesMethod (MessageExtractionMarbles messageExtractionMarbles) {
        if (turnController.TakeResourcesMarket(messageExtractionMarbles.getColrowextract(), messageExtractionMarbles.getNumextract())) {
            nextState.clear();
            nextState.add(MessageType.ADDDISCARDMARBLES);
            nextState.add(MessageType.EXCHANGEWAREHOUSE);
        }
    }

    public void addDiscardMarblesMethod (MessageAddDiscardMarble messageAddDiscardMarble) {
        if (!turnController.getGame().getMarketStructure().getBuffer().isEmpty()) {
            if (turnController.AddDiscardMarble(messageAddDiscardMarble.isChoice(), messageAddDiscardMarble.getIndexwarehouse())) {
                if (turnController.getGame().getMarketStructure().getBuffer().isEmpty()) {
                    nextState.clear();
                    nextState.add(MessageType.UPDATESTATELEADERACTION);
                }
            }
        }
    }

    public void exchangeWarehouseMethod (MessageExchangeWarehouse messageExchangeWarehouse) {
        if (!turnController.getGame().getMarketStructure().getBuffer().isEmpty())
            turnController.ExchangeWarehouse(messageExchangeWarehouse.getRow1(), messageExchangeWarehouse.getRow2());
    }

    public void selectDevCardMethod (MessageSelectDevCard messageSelectDevCard) {
        if (turnController.selectDevCard(messageSelectDevCard.getRowDevCardDeck(), messageSelectDevCard.getColumnDevCardDeck())) {
            nextState.clear();
            nextState.add(MessageType.CHOOSERESOURCESPURCHASEDEVCARD);
        }
    }

    public void chooseResourcesForPurchase (MessageChooseResourcesPurchaseDevCard messageChooseResourcesPurchaseDevCard) {
        if (turnController.chooseResourcesForPurchase(messageChooseResourcesPurchaseDevCard.getWarehouseRes(), messageChooseResourcesPurchaseDevCard.getStrongboxRes(), messageChooseResourcesPurchaseDevCard.getExtrachestMap())) {
            nextState.clear();
            nextState.add(MessageType.INSERTCARD);
        }
    }

    public void insertCardMethod (MessageInsertCard messageInsertCard) {
        if (turnController.insertCard(messageInsertCard.getColumnSlotDev())) {
            nextState.clear();
            nextState.add(MessageType.ENDTURN);
        }
    }

    public void chooseResourcesBaseProductionMethod (MessageChooseResourcesBaseProduction message) {
        if (turnController.chooseResourcesBaseProduction(message.getWarehouseRes(), message.getStrongboxRes(), message.getExtrachestMap())) {
            nextState.clear();
            nextState.add(MessageType.CHOSENRESOURCEBASEPRODUCTION);
        }
    }

    public void chosenResourceBaseProductionMethod (MessageChosenResourceBaseProduction message) {
        if (turnController.activeBaseProduction(message.getResources())) {
            nextState.clear();
            nextState.add(MessageType.ACTIVEPRODUCTIONDEVCARD);
            nextState.add(MessageType.ACTIVELEADERCARDPRODUCTION);
            nextState.add(MessageType.ENDTURN);
        }
    }

    public void activeProductionDevCard (MessageActiveProductionDevCard message) {
        if (turnController.activeProductionDevCard(message.getColumn())) {
            nextState.clear();
            nextState.add(MessageType.CHOOSERESOURCESDEVCARDPRODUCTION);
        }
    }

    public void chooseResourcesDevCardProductionMethod (MessageChooseResourcesDevCardProduction message) {
        if (turnController.chooseResourcesDevCardProduction(message.getWarehouseRes(), message.getStrongboxRes(), message.getExtrachestMap())) {
            nextState.clear();
            nextState.add(MessageType.CHOOSERESOURCESBASEPRODUCTION);
            nextState.add(MessageType.ACTIVELEADERCARDPRODUCTION);
            nextState.add(MessageType.ENDTURN);
        }
    }

    public void activeLeaderCardProductionMethod (MessageActiveLeaderCardProduction message) {
        if (turnController.ActiveLeaderCardProduction(message.getIndexExtraProduction(), message.getWareStrongChest(), message.getResource())) {
            nextState.clear();
            nextState.add(MessageType.ENDTURN);
            nextState.add(MessageType.CHOOSERESOURCESBASEPRODUCTION);
            nextState.add(MessageType.ACTIVEPRODUCTIONDEVCARD);
        }
    }

    public boolean isFirstPlayer (String nickname) {
        //verifico prima se il currentplayer sia il primo della lista di giocatori
        if (turnController.getGame().getCurrentPlayer().equals(turnController.getGame().getPlayersList().get(0))) {
            //verifico se il nickname del client sia uguale a quello del current player
            return turnController.getGame().getCurrentPlayer().getNickname().equals(nickname);
        }
        return false;
    }

    public boolean verifyCurrentPlayer (String nickname) {
        return turnController.getGame().getCurrentPlayer().getNickname().equals(nickname);
    }

    public TurnController getTurnController() {
        return turnController;
    }
}

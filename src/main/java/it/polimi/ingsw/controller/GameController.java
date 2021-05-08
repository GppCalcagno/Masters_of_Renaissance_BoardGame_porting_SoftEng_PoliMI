package it.polimi.ingsw.controller;

import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.message.*;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameState gameState;

    private TurnController turnController;

    private List<MessageType> nextState;

    private Server server;

    private List<String> playersNames;

    public GameController (Server server) throws IOException {
        this.turnController = new TurnController();
        gameState = GameState.LOGIN;
        this.nextState = new ArrayList<>();
        nextState.add(MessageType.LOGIN);
        this.server = server;
        this.playersNames = new ArrayList<>();
    }

    public void actionGame (Message receivedMessage) {
        if (nextState.contains(receivedMessage.getMessageType())) {
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

    public void loginPhase (Message message) {
        switch (message.getMessageType()) {
            case LOGIN:
                loginMethod((MessageLogin) message);
                break;
            case NUMPLAYERS:
                numPlayersMethod((MessageNumPlayers) message);
                break;
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
                    endTurnMethod();
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
                if (verifyCurrentPlayer(message.getNickname())) {
                    updateStateLeaderActionMethod((MessageAddDiscardLeaderCard) message);
                }
            case ENDTURN:
                if (verifyCurrentPlayer(message.getNickname())) {
                    endTurnMethod();
                }
            default:
                break;
        }
    }

    public void loginMethod(MessageLogin messageLogin) {
        if (turnController.getNumPlayersCount() == 0) {
            playersNames.add(messageLogin.getNickname());
            nextState.clear();
            nextState.add(MessageType.NUMPLAYERS);
            server.sendtoPlayer(messageLogin.getNickname(), new MessageRequestNumPlayers(messageLogin.getNickname()));
        } else {
            if (playersNames.size() < turnController.getNumPlayersCount() - 1) {
                if (!playersNames.contains(messageLogin.getNickname())) {
                    playersNames.add(messageLogin.getNickname());
                    nextState.clear();
                    nextState.add(MessageType.LOGIN);
                    server.sendtoPlayer(messageLogin.getNickname(), new MessageGeneric(messageLogin.getNickname(), MessageType.WAITINGOTHERPLAYERS));
                }
                else server.sendtoPlayer(messageLogin.getNickname(), new MessageChechOk(messageLogin.getNickname(), false));
            } else {
                if (!playersNames.contains(messageLogin.getNickname())) {
                    playersNames.add(messageLogin.getNickname());
                    for (String name : playersNames) {
                        turnController.getGame().addPlayersList(new Player(name));
                    }
                    nextState.clear();
                    nextState.add(MessageType.CHOOSELEADERCARDS);
                    gameState = GameState.INITGAME;
                    turnController.startGame();
                    server.sendtoPlayer(messageLogin.getNickname(), new MessageChechOk(messageLogin.getNickname(), true));
                }
                else server.sendtoPlayer(messageLogin.getNickname(), new MessageChechOk(messageLogin.getNickname(), false));
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
                    server.sendtoPlayer(messageNumPlayers.getNickname(), new MessageChechOk(messageNumPlayers.getNickname(), true));
                }
                else {
                    nextState.add(MessageType.LOGIN);
                    server.sendtoPlayer(messageNumPlayers.getNickname(), new MessageGeneric(messageNumPlayers.getNickname(), MessageType.WAITINGOTHERPLAYERS));
                }
            }
            else server.sendtoPlayer(messageNumPlayers.getNickname(), new MessageChechOk(messageNumPlayers.getNickname(), false));
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
        turnController.ChooseResourcesFirstTurn(messageChooseResourcesFirstTurn.getResourcesList());
        nextState.clear();
        nextState.add(MessageType.ENDTURN);
    }

    public void extractionMarblesMethod (MessageExtractionMarbles messageExtractionMarbles) {
        if (turnController.TakeResourcesMarket(messageExtractionMarbles.getColrowextract(), messageExtractionMarbles.getNumextract())) {
            nextState.clear();
            nextState.add(MessageType.ADDDISCARDMARBLES);
            nextState.add(MessageType.EXCHANGEWAREHOUSE);
            server.sendBroadcastMessage(new MessageExtractedMarbles("server", turnController.getGame().getMarketStructure().getBuffer()));
        }
    }

    public void addDiscardMarblesMethod (MessageAddDiscardMarble messageAddDiscardMarble) {
        if (!turnController.getGame().getMarketStructure().getBuffer().isEmpty()) {
            if (turnController.AddDiscardMarble(messageAddDiscardMarble.isChoice(), messageAddDiscardMarble.getIndexwarehouse())) {
                if (turnController.getGame().getMarketStructure().getBuffer().isEmpty()) {
                    nextState.clear();
                    nextState.add(MessageType.UPDATESTATELEADERACTION);
                    nextState.add(MessageType.ENDTURN);
                }
                else {
                    nextState.clear();
                    nextState.add(MessageType.ADDDISCARDMARBLES);
                    nextState.add(MessageType.EXCHANGEWAREHOUSE);
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

    public void updateStateLeaderActionMethod (MessageAddDiscardLeaderCard message) {
        if (message.isAddOrDiscard()) {
            if (turnController.activeLeaderAction(message.getPos())) {
                nextState.clear();
                nextState.add(MessageType.ENDTURN);
                nextState.add(MessageType.EXTRACTIONMARBLES);
                nextState.add(MessageType.SELECTDEVCARD);
                nextState.add(MessageType.CHOOSERESOURCESBASEPRODUCTION);
                nextState.add(MessageType.ACTIVEPRODUCTIONDEVCARD);
                nextState.add(MessageType.ACTIVELEADERCARDPRODUCTION);
            }
        }
        else {
            if (turnController.discardLeaderAction(message.getPos())) {
                nextState.clear();
                nextState.add(MessageType.ENDTURN);
                nextState.add(MessageType.EXTRACTIONMARBLES);
                nextState.add(MessageType.SELECTDEVCARD);
                nextState.add(MessageType.CHOOSERESOURCESBASEPRODUCTION);
                nextState.add(MessageType.ACTIVEPRODUCTIONDEVCARD);
                nextState.add(MessageType.ACTIVELEADERCARDPRODUCTION);
            }
        }
    }

    public void endTurnMethod () {
        if (gameState == GameState.INITGAME) {
            if (turnController.getGame().getPlayersList().indexOf(turnController.getGame().getCurrentPlayer()) == turnController.getNumPlayersCount() - 1) {
                gameState = GameState.INGAME;
                nextState.clear();
                nextState.add(MessageType.EXTRACTIONMARBLES);
                nextState.add(MessageType.SELECTDEVCARD);
                nextState.add(MessageType.CHOOSERESOURCESBASEPRODUCTION);
                nextState.add(MessageType.ACTIVEPRODUCTIONDEVCARD);
                nextState.add(MessageType.ACTIVELEADERCARDPRODUCTION);
                nextState.add(MessageType.UPDATESTATELEADERACTION);
                try {
                    turnController.endTurn();
                } catch (EndGameException e) {
                    turnController.getGame().givefinalpoints();
                    //notifica il vincitore
                    turnController.getGame().getWinner();
                }
            }
            else {
                nextState.clear();
                nextState.add(MessageType.CHOOSELEADERCARDS);
                nextState.add(MessageType.CHOOSERESOURCESFIRSTTURN);
                try {
                    turnController.endTurn();
                } catch (EndGameException e) {
                    turnController.getGame().givefinalpoints();
                    //notifica il vincitore
                    turnController.getGame().getWinner();
                }
            }
        }
        else if (gameState == GameState.INGAME) {
            nextState.clear();
            nextState.add(MessageType.EXTRACTIONMARBLES);
            nextState.add(MessageType.SELECTDEVCARD);
            nextState.add(MessageType.CHOOSERESOURCESBASEPRODUCTION);
            nextState.add(MessageType.ACTIVEPRODUCTIONDEVCARD);
            nextState.add(MessageType.ACTIVELEADERCARDPRODUCTION);
            nextState.add(MessageType.UPDATESTATELEADERACTION);
            try {
                turnController.endTurn();
            } catch (EndGameException e) {
                turnController.getGame().givefinalpoints();
                //notifica il vincitore
                turnController.getGame().getWinner();
            }
        }
    }

    public boolean verifyCurrentPlayer (String nickname) {
        return turnController.getGame().getCurrentPlayer().getNickname().equals(nickname);
    }

    public TurnController getTurnController() {
        return turnController;
    }
}

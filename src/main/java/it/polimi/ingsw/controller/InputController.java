package it.polimi.ingsw.controller;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class InputController {
    private List<MessageType> messageTypeList;

    private TurnController turnController;

    private int countLoginTurn;

    public InputController(TurnController turnController) {
        this.messageTypeList = new ArrayList<>();
        messageTypeList.add(MessageType.LOGIN);
        messageTypeList.add(MessageType.NUMPLAYERS);
        this.turnController = turnController;
    }

    public void verifyTurn(Message message) {
        switch (message.getMessageType()) {
            case LOGIN:
                if (messageTypeList.get(0) == MessageType.LOGIN) {
                    if (messageTypeList.get(1) == MessageType.NUMPLAYERS) {
                        messageTypeList.remove(0);
                    }
                    else {
                        if (countLoginTurn == 0) {
                            messageTypeList.remove(0);
                            messageTypeList.add(MessageType.CHOOSELEADERCARDS);
                            countLoginTurn = turnController.getNumPlayersCount();
                        }
                        else countLoginTurn--;
                    }
                }
            case NUMPLAYERS:
                if (messageTypeList.get(0) == MessageType.NUMPLAYERS) {
                    messageTypeList.remove(0);
                    if (turnController.getNumPlayersCount() == 1) {
                        messageTypeList.add(MessageType.CHOOSELEADERCARDS);
                        countLoginTurn = turnController.getNumPlayersCount();
                    }
                    else {
                        countLoginTurn = turnController.getNumPlayersCount() - 2;
                        messageTypeList.add(MessageType.LOGIN);
                    }
                }
            case CHOOSELEADERCARDS:
                if (messageTypeList.get(0) == MessageType.CHOOSELEADERCARDS) {
                    if (countLoginTurn == 1) {
                        messageTypeList.remove(0);
                        messageTypeList.add(MessageType.CHOOSERESOURCESFIRSTTURN);
                        countLoginTurn = turnController.getNumPlayersCount();
                    }
                    else countLoginTurn--;
                }
            case CHOOSERESOURCESFIRSTTURN:
                if (messageTypeList.get(0) == MessageType.CHOOSERESOURCESFIRSTTURN) {
                    if (countLoginTurn == 1) {
                        messageTypeList.remove(0);
                        messageTypeList.add(MessageType.EXTRACTIONMARBLES);
                        messageTypeList.add(MessageType.EXTRACTIONMARBLES);
                        countLoginTurn = turnController.getNumPlayersCount();
                    }
                    else countLoginTurn--;
                }
        }
    }

    public void setMessageTypeList (MessageType messageType) {
        this.messageTypeList.add(messageType);
    }

    public void removeMessageTypeList (MessageType messageType) {
        this.messageTypeList.remove(messageType);
    }

    public List<MessageType> getMessageTypeList() {
        return messageTypeList;
    }
}

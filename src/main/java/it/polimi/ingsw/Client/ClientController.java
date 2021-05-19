package it.polimi.ingsw.Client;

import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.io.IOException;


public class ClientController implements Observer {
    ClientSocket clientSocket;
    PlayerBoard board;
    ViewInterface view;

    public ClientController(ViewInterface view) {
        try {
            this.board= new PlayerBoard();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @Override
    public void update(Message message) {
        message.update(board,view);
    }

    public void connect(String address, int port){
        clientSocket= new ClientSocket(address,port);
    }

    public void sendMessage(Message message) {
        clientSocket.sendMessage(message);
    }

}

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

    public ClientController() {
        board=new PlayerBoard();
    }

    public void setView(ViewInterface view) {
        this.view = view;
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

    public PlayerBoard getBoard() {
        return board;
    }
}

package it.polimi.ingsw.Client;

import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;


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
        this.clientSocket= new ClientSocket(address,port,this);
        clientSocket.readMessage();
    }

    public void disconnect(){
        this.clientSocket.disconnect();
    }



    public void sendMessage(Message message) {
        clientSocket.sendMessage(message);
    }

    public PlayerBoard getBoard() {
        return board;
    }
}

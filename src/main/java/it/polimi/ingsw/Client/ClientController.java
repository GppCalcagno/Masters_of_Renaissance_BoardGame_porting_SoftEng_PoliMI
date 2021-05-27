package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Client.ClientSender.ClientSender;
import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.Client.ClientSender.SendtoController;
import it.polimi.ingsw.Network.Client.ClientSender.SendtoServer;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Server.UpdateSender.LocalUpdate;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.Gui;
import it.polimi.ingsw.View.ViewInterface;


public class ClientController implements Observer {
    ClientSocket clientSocket;
    ViewInterface view;
    ClientSender sender;
    boolean isOnline;

    public ClientController(boolean isCli, boolean isOnline) {
        this.isOnline=isOnline;

        if(isCli)
            view= new Cli(this);
        else
            view= new Gui(this);

        if(!isOnline){
            sender= new SendtoController(new GameController(new LocalUpdate(this),false));
            view.askLogin();
        }
        else{
            view.askServerInfo();
        }
    }

    @Override
    public void update(Message message) {
        message.update(view);
    }

    public void connect(String address, int port){
        this.clientSocket= new ClientSocket(address,port,this);
        sender= new SendtoServer(clientSocket);
        clientSocket.readMessage();
    }

    public void disconnect(){
        this.clientSocket.disconnect();
    }

    public void sendMessage(Message message) {
        sender.sendMessage(message);
    }

    public void setSender(ClientSender sender) {
        this.sender = sender;
    }

}

package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Client.ClientSender.ClientSender;
import it.polimi.ingsw.Network.Client.ClientSocket;
import it.polimi.ingsw.Network.Client.ClientSender.SendtoController;
import it.polimi.ingsw.Network.Client.ClientSender.SendtoServer;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.ServerUpdate.*;
import it.polimi.ingsw.Network.Server.UpdateSender.LocalUpdate;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.Gui;
import it.polimi.ingsw.View.ViewInterface;


public class ClientController implements Observer {
    private ClientSocket clientSocket;
    private ViewInterface view;
    private ClientSender sender;
    private boolean isOnline;


    /**
     * this method launch the client in the right way, cli or gui, online or offline. The offline game is only in single player
     * @param isCli boolean that specifies if the first parameter is cli or gui
     * @param isOnline boolean that specifies if the second parameters is online or offline
     */
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

    /**
     * @param message information about Update
     */
    @Override
    public void update(Update message) {
        /*  we have inserted this check to filter messages before the board is initialized */
        if (view.getPlayerBoard().getCurrentPlayer()!=null || message instanceof UpdateRequestLogin || message instanceof UpdateReconnect || message instanceof UpdateRequestNumPlayers || message instanceof UpdateWaitingForOtherPlayer || message instanceof UpdateStartGame)
            message.update(view);
    }

    /**
     * create a connection
     * @param address server's address
     * @param port server's port
     */
    public void connect(String address, int port){
        this.clientSocket= new ClientSocket(address,port,this);
        sender= new SendtoServer(clientSocket);
        clientSocket.readMessage();
    }

    /**
     * disconnect this client from the server if is online
     */
    public void disconnect(){
        if(isOnline)
            this.clientSocket.disconnect();
        else
            System.exit(0);
    }

    /**
     * client send message through the sender, the message could be passed to the server if is an online game or
     * directly to the controller if the game is offline
     * @param message a serialized java class
     */
    public void sendMessage(Message message) {
        sender.sendMessage(message);
    }

    public void setSender(ClientSender sender) {
        this.sender = sender;
    }

}

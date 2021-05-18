package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server extends Observable {
    private final static int SOTIMEOUT=20;
    private static Logger LOGGER;

    private Map<String, ServerClientHandler> clientHandlerMap;
    private GameController gameController;
    private ServerSocket serverSocket;
    private int connected;
    private int port;


    /**
     * this is the costructor of the Server
     * @param port is the port of the Server
     */
    public Server(int port){
        try {
            this.gameController= new GameController(this);
        } catch (IOException e) {
            LOGGER.severe("ERROR: CAN'T LOAD GAME CARD");
        }
        clientHandlerMap = new HashMap<>();
        this.port=port;
        LOGGER= Logger.getLogger(Server.class.getName());
        connected=0;
    }


    /**
     * this method is used to Start Game Server
     */
    public void startServer(){
        //creo ServerSocket
        try {
            serverSocket= new ServerSocket(port);
            LOGGER.info("Server Start");
        } catch (IOException e) {
            LOGGER.severe("SERVER STARTING ERROR");
            System.exit(-1);
        }

        while(true){

            try {
                //eseguo connessioni con client
                Socket clientSocket= serverSocket.accept();
                    //todo to fix
                    connected++;
                    //timeout set
                    clientSocket.setSoTimeout(SOTIMEOUT*1000);
                    LOGGER.info("Connection with Client successful");

                    //avvio thread
                    Thread thread= new Thread(new ServerClientHandler(clientSocket,this));
                    thread.start();

            } catch (IOException e) {
                LOGGER.severe("CLIENT CONNECTION ERROR");
            }

        }
    }

    /**
     * this method add a client (player) on clientHandlerMap and observer list
     * @param name name of the player
     * @param ClientHandler socket of the player
     */
    public void addPlayer(String name, ServerClientHandler ClientHandler){
            clientHandlerMap.put(name, ClientHandler);
            addObserver(ClientHandler);
            LOGGER.info("Player " + name +" Added to ServerList");
    }

    /**
     * This method remove a client (player) on clientHandlerMap and observer list
     * @param name name of the player
     */
    public void removePlayer(String name){
        removeObserver(clientHandlerMap.get(name));
        clientHandlerMap.remove(name);
        LOGGER.info("Player " + name +" removed from ServerList");
        connected--;

    }

    /**
     * this method is a linker between GameController and Server
     * @param message is the message given to GameController
     */
    public void recivedMessage(Message message){
        //gameController.actionGame(message);
    }

    /**
     * This method notify all the client logged
     * @param message is the message to notify
     */
    public void sendBroadcastMessage(Message message){
        notifyAllObserver(message);
        LOGGER.info("Server sent Broadcast Message: "+ message.getMessageType());
    }


    /**
     * This method notify a single Player
     * @param player is the name of the player you want to notify
     * @param message is the message you want to send
     */
    public void sendtoPlayer(String player, Message message){
        clientHandlerMap.get(player).update(message);
        LOGGER.info("Server sent Private Message to " + player+": "+ message.getMessageType());
    }

}

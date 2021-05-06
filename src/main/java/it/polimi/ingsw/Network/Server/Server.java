package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server extends Observable {
    private final static int SOTIMEOUT=20;
    private Map<String, ServerClientHandler> clientHandlerMap;
    private GameController gameController;
    private ServerSocket serverSocket;
    private int port;

    private static Logger LOGGER;

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
     * @param serverClientHandler socket of the player
     */
    public void addPlayer(String name, ServerClientHandler serverClientHandler){
        if(gameController.getTurnController().getNumPlayersCount()==0){
            clientHandlerMap.put(name, serverClientHandler);
            addObserver(serverClientHandler);

            print();

        }

        if(!clientHandlerMap.containsKey(name) && clientHandlerMap.size()<gameController.getTurnController().getNumPlayersCount()){
            clientHandlerMap.put(name, serverClientHandler);
            addObserver(serverClientHandler);
        }
    }

    /**
     * This method remove a client (player) on clientHandlerMap and observer list
     * @param name name of the player
     */
    public void removePlayer(String name){
        removeObserver(clientHandlerMap.get(name));
        clientHandlerMap.remove(name);

    }

    /**
     * this method is a linker between GameController and Server
     * @param message is the message given to GameController
     */
    public void recivedMessage(Message message){
        //manda il messaggio al controller che lo smista
        gameController.actionGame(message);
    }

    /**
     * This method notify all the client logged
     * @param message is the message to notify
     */
    public void sendBroadcastMessage(Message message){
        notifyAllObserver(message);
    }

    public void print(){
        System.out.println("DIM:"+clientHandlerMap.size() +" -- "+ count()+ " Name: "+ clientHandlerMap.keySet());

    }


}

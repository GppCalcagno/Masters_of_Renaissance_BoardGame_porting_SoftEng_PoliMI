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
    private final static int SOTIMEOUT=500;
    private static Logger LOGGER;

    private Map<Integer, ServerClientHandler> iDClientMap;
    private Map<Integer, String> iDNameMap;
    private GameController gameController;
    private ServerSocket serverSocket;
    private int clientConnected;
    private int port;


    /**
     * this is the costructor of the Server
     * @param port is the port of the Server
     */
    public Server(int port){
        this.gameController= new GameController(this);
        iDClientMap = new HashMap<>();
        iDNameMap = new HashMap<>();

        this.port=port;
        LOGGER= Logger.getLogger(Server.class.getName());
        clientConnected =0;
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
                    clientConnected++;
                    //timeout set
                    clientSocket.setSoTimeout(SOTIMEOUT*1000);
                    LOGGER.info("Connection with Client successful");

                    //Strart thread
                    ServerClientHandler clientHandler=new ServerClientHandler(clientSocket,this,clientConnected);

                    Thread thread= new Thread(clientHandler);
                    addObserver(clientHandler);
                    addIDClient(clientConnected,clientHandler);
                    thread.start();

            } catch (IOException e) {
                LOGGER.severe("CLIENT CONNECTION ERROR");
            }

        }
    }


    /**
     * this method add a Client to iDClientMap
     * @param ID of the client
     * @param clientHandler socket manager of the client
     */
    public void addIDClient(int ID, ServerClientHandler clientHandler){
        iDClientMap.put(ID,clientHandler);
    }

    public void addIDname(int ID, String name){
        iDNameMap.put(ID,name);
    }

    public Integer getIDfromName(String name){
        for(int identifier: iDNameMap.keySet()){
            if(iDNameMap.get(identifier).equals(name))
                return  identifier;
        }
        return null;
    }

    /**
     * this method is a linker between GameController and Server
     * @param message is the message given to GameController
     */
    public void recivedMessage(Message message){
        gameController.onRecivedMessage(message);
    }

    /**
     * This method notify all the client logged
     * @param message is the message to notify
     */
    public void sendBroadcastMessage(Message message){
        notifyAllObserver(message);
        LOGGER.info("Server send Broadcast Message: "+ message.getMessageType());
    }


    /**
     * This method notify a single Player
     * @param player is the name of the player you want to notify
     * @param message is the message you want to send
     */
    public void sendtoPlayer(String player, Message message){
        Integer id=getIDfromName(player);
        if(id!=null){

            iDClientMap.get(id).update(message);
            LOGGER.info("Server send to " +player+" a message: "+ message.getMessageType());
        }

    }

    public void disconnect(int ID){
        LOGGER.info("Player "+iDNameMap.get(ID)+" disconnected");

        if(iDNameMap.containsKey(ID))
            gameController.disconnect(iDNameMap.get(ID));

        if(iDClientMap.containsKey(ID))
            iDClientMap.get(ID).clocsesocket();

        iDClientMap.remove(ID);
        iDNameMap.remove(ID);

        clientConnected--;
    }

}

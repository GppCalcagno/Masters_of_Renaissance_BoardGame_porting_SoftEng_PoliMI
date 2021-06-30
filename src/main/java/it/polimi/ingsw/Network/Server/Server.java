package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.Network.Message.ServerUpdate.UpdateError;
import it.polimi.ingsw.Network.Message.ServerUpdate.UpdateRequestLogin;
import it.polimi.ingsw.Network.Server.UpdateSender.ServerUpdate;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server extends Observable {
    private final static int SOTIMEOUT=10;
    private static Logger LOGGER;

    private Map<Integer, ServerClientHandler> iDClientMap;
    private Map<Integer, String> iDNameMap;
    private GameController gameController;
    private ServerSocket serverSocket;
    private int clientConnected;
    private int port;
    private Object serverLocker= new Object();

    /**
     * this is the costructor of the Server
     * @param port is the port of the Server
     */
    public Server(int port){
        this.gameController= new GameController(new ServerUpdate(this),true);
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

    public void addIDname(int ID, String name, Message message){
        synchronized (serverLocker) {
            if (iDNameMap.containsValue(name)) {
                iDClientMap.get(ID).update(new UpdateError("server", "Nickname already Used"));
                iDClientMap.get(ID).update(new UpdateRequestLogin());
            } else {
                iDNameMap.put(ID, name);
                recivedMessage(message);
            }
        }

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
    public void sendBroadcastMessage(Update message){
        notifyAllObserver(message);
        LOGGER.info("Server send Broadcast Message: "+ message.getClass().getName());
    }


    /**
     * This method notify a single Player
     * @param player is the name of the player you want to notify
     * @param message is the message you want to send
     */
    public void sendtoPlayer(String player, Update message){
        Integer id=getIDfromName(player);
        if(id!=null){

            iDClientMap.get(id).update(message);
            LOGGER.info("Server send to " +player+" a message: "+ message.getClass().getName());
        }

    }

    /**
     * this method disconnect a player
     * @param ID position in the list of players
     */
    public void disconnect(int ID){

        synchronized (serverLocker) {
            LOGGER.info("Player " + iDNameMap.get(ID) + " disconnected");
            //The observer must be removed first otherwise server tries broadcast message and throws again exception (loop)
            removeObserver(iDClientMap.get(ID));

            if (iDNameMap.containsKey(ID))
                gameController.disconnect(iDNameMap.get(ID));

            if (iDClientMap.containsKey(ID)){
                iDClientMap.get(ID).clocsesocket();
            }
            iDClientMap.remove(ID);
            iDNameMap.remove(ID);
        }
    }
}

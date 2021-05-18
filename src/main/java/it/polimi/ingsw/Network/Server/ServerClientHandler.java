package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageGeneric;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerClientHandler implements Runnable, Observer {
    /** this attribute is the Logger Of the server, is used to sent check or warning message */
    private final Logger SERVERLOGGER;

    /** this is the name of the player who is using the socket */
    private String clientName;

    /** this is the socket of the player */
    private Socket clientSocket;

    /**
     * this is the connection state of the socket
     * true  -> connected
     * false -> disconnected
     */
    private boolean connected;

    /** this attribute is used to send information to the Model and controll Player Socket */
    private Server server;


    /** this attribute is used to send message */
    private ObjectOutputStream output;
    /** this attribute is used to recive message */
    private ObjectInputStream input;

    /** this is a locker to manage the Concurrency     */
    private  final Object SenderLock;

    /**
     * this is the costructor of the class
     * @param clientSocket is the connected socket of the player
     * @param server is the current Game Server
     */
    public ServerClientHandler(Socket clientSocket, Server server) {
        SERVERLOGGER= Logger.getLogger(Server.class.getName());
        this.clientSocket = clientSocket;
        this.server=server;
        connected=true;
        SenderLock= new Object();

        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            SERVERLOGGER.severe("ERROR: THREAD INITIALIZATION ");
            disconnect();
        }

    }

    /** Thread run method  */
    @Override
    public void run() {
        ReciveMessage();
    }

    /** this method is used to listen for new messages  */
    private void ReciveMessage() {
        try {

            while(connected){

                //possibile lock (non necessario per ora)
                SERVERLOGGER.info("Server in attesa di messaggio");
                Message message= (Message) input.readObject();


                if(message!=null){
                    SERVERLOGGER.info("Messagge recived" + "(from" + message.getNickname()+")"+ ":" + message.getMessageType());
                    if(message.getMessageType().equals(MessageType.PING)){
                        sendMessage(new MessageGeneric("server",MessageType.PING));
                    }
                    else{
                        if(message.getMessageType().equals(MessageType.LOGIN)){
                            server.addPlayer(message.getNickname(),this);
                            clientName=message.getNickname();
                        }

                        if(message.getMessageType().equals(MessageType.DISCONNECT)) break;
                            server.recivedMessage(message);
                    }
                }
            }//finewhile

            SERVERLOGGER.info("Ended ClientHander while");
            disconnect();


        }catch (IOException | ClassNotFoundException e) {
                SERVERLOGGER.severe("ERROR: CLIENT MESSAGE RECEPTION ");
                disconnect();
            }
    }

    /**
     * this method is used to send update to Client
     * @param message information about Update
     */
    @Override
    public void update(Message message) {
        sendMessage(message);
    }


    /**
     * this method is used to send message,
     * @param message is the message to send to the player
     */
    private void sendMessage(Message message){
            try {
                output.writeObject(message);
                output.reset();

            } catch (IOException e) {
                SERVERLOGGER.severe("ERROR: CAN'T SENT MESSAGE TO:"+ clientName);
                disconnect();
            }
        }

    /** this method is used to disconnect a plauer from the game  */
    public void disconnect(){
        server.removePlayer(clientName);
    }
}

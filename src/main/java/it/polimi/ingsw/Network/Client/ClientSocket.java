package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Network.Message.Message;

import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.logging.Logger;

public class ClientSocket extends Observable {

    private final static int SOTIMEOUT=500;
    /** this is the Client Socket */
    private Socket clientSocket;

    /** This Attribute is used to send message to the Server */
    private ObjectOutputStream sendMessage;
    /** This Attribute is used to recive message from the Server */
    private ObjectInputStream reciveMessage;

    /** This Attribute is the log of the client */
    private Logger CLOGGER= Logger.getLogger(ClientSocket.class.getName());

    /** This attribute is a Timer Schedule for the ping Message */
    private Timer pinger;

    /**
     * it is used to manage concurrent delivery of messages to the server
     * is used for Ping and Sending from the controller
     */
    private final Object LockSending;

    /**
     * this is the costuctor of the class
     * @param address is the address of the server
     * @param port is the server connection port
     */
    public ClientSocket(String address, int port, ClientController client) {
        //connection to the server
        clientSocket = new Socket();
        LockSending=new Object();
        addObserver(client);

        try {
            clientSocket.connect(new InetSocketAddress(address,port));
            clientSocket.setSoTimeout(SOTIMEOUT*1000);
        }
        catch (IOException e) {
            CLOGGER.severe("ERROR: CAN'T CONNECT TO THE SERVER");
            System.exit(-1);
        }

        //INOUT class
        try {
            sendMessage= new ObjectOutputStream(clientSocket.getOutputStream());
            reciveMessage= new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException e) {
            CLOGGER.severe("ERROR: CAN'T OPEN I/O STREAM");
            System.exit(-1);
        }

        //pinger
        pinger=new Timer();
        EnablePing(true);

    }

    /** this method is a Listener of Message from the server */
    public void readMessage(){
        while(true) {
            try {
                Message message = (Message) reciveMessage.readObject();
                if(message!=null && !message.getMessageType().equals(MessageType.PING)){
                    notifyAllObserver(message);
                }
            }
            catch (IOException | ClassNotFoundException e) {
                CLOGGER.warning("ERROR: CAN'T READ MESSAGE");
                System.exit(0);
            }
        }
    }

    /**
     * This method is used to send message to the server
     * @param message is the message to send
     */
    public void sendMessage(Message message) {
        synchronized (LockSending) {
            try {
                sendMessage.writeObject(message);
            } catch (IOException e) {
                CLOGGER.severe("EROOR: CAN'T SEND MESSAGE TO THE CONTROLLER");
                e.printStackTrace();
            }
        }
    }

    /**
     * this Method is used to switch Pinging to the server
     * @param state true to active a Pinging Thread, false to cancel it
     */
    public void EnablePing(boolean state){
        if(state){
            pinger.schedule(new PingerTimerTask(this),0,300*1000);
        }
        else
            pinger.cancel();
    }

    public ObjectOutputStream getSendMessage() {
        return sendMessage;
    }

    public Object getLockSending() {
        return LockSending;
    }
}



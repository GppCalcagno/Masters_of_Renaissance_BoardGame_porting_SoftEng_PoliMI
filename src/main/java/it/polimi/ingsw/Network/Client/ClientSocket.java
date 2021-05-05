package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.message.Message;

import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.logging.Logger;

public class ClientSocket extends Observable {
    //todo CLient Controller

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
     * this is the costructor of the Claa
     * @param address is the address of the server
     * @param port is the server connection port
     */
    public ClientSocket(String address, int port) {
        //connection to the server
        clientSocket = new Socket();

        try { clientSocket.connect(new InetSocketAddress(address,port)); }
            catch (IOException e) {
                CLOGGER.severe("ERROR: CAN'T CONNECT TO THE SERVER");
                System.exit(-1);
            }

        //INOUT class
        try {
            sendMessage= new ObjectOutputStream(clientSocket.getOutputStream());
            reciveMessage= new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            CLOGGER.severe("ERROR: CAN'T OPEN I/O STREAM");
            System.exit(-1);
        }

        //pinger
        pinger=new Timer();

        EnablePing(true);
    }

    /**
     * this method is a Listener of Message from the server
     */
    public void readMessage(){

        while(true) {
            try {
                Message message = (Message) reciveMessage.readObject();
                CLOGGER.info("recived Message");
                notifyAllObserver(message);

            } catch (IOException | ClassNotFoundException e) {
                CLOGGER.warning("ERROR: CAN'T READ MESSAGE");
            }
        }
    }

    /**
     * This method is used to send message to the server
     * @param message is the message to send
     */
    public void SendMessage(Message message){
        try {
            sendMessage.writeObject(message);
        } catch (IOException e) {
            CLOGGER.severe("EROOR: CAN'T SEND MESSAGE TO THE CONTROLLER");

        }
    }

    /**
     * this Method is used to switch Pinging to the server
     * @param state true to active a Pinging Thread, false to cancel it
     */
    public void EnablePing(boolean state){
        if(state){
            pinger.schedule(new PingerTimerTask(sendMessage),0,5000);
        }
        else
            pinger.cancel();
    }
}



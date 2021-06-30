package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Network.Client.ClientSender.SendtoServer;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.View.Cli.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;

public class ClientSocket extends Observable {

    private final static int SOTIMEOUT=10;
    /** this is the Client Socket */
    private Socket clientSocket;

    /** This Attribute is used to send message to the Server */
    private ObjectOutputStream sendMessage;
    /** This Attribute is used to recive message from the Server */
    private ObjectInputStream reciveMessage;

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

        client.setSender(new SendtoServer(this));

        try {
            clientSocket.connect(new InetSocketAddress(address,port));
            clientSocket.setSoTimeout(SOTIMEOUT*1000);
        }
        catch (IOException e) {
            System.out.println(Color.ANSI_RED.escape()+"Can't connect to the server"+ Color.ANSI_BRIGHTWHITE.escape());
            System.exit(0);
        }

        //INOUT class
        try {
            sendMessage= new ObjectOutputStream(clientSocket.getOutputStream());
            reciveMessage= new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException e) {
            System.out.println(Color.ANSI_RED.escape()+"Can't Open I/O Stream"+ Color.ANSI_BRIGHTWHITE.escape());
            disconnect();
        }


        //pinger
        pinger=new Timer();
        EnablePing(true);

    }

    /** this method is a Listener of Message from the server */
    public void readMessage(){
        while(true) {
            try {
                Update message = (Update) reciveMessage.readObject();
                if(message!=null){
                    notifyAllObserver(message);
                }
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println(Color.ANSI_RED.escape()+"Can't reach the server anymore"+ Color.ANSI_BRIGHTWHITE.escape());
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
                System.out.println(Color.ANSI_RED.escape()+"Can't send Message to the  server anymore"+ Color.ANSI_BRIGHTWHITE.escape());
                disconnect();
            }
        }
    }

    public void disconnect(){
        try {
            clientSocket.close();
        } catch (IOException ioException) {
            System.out.println(Color.ANSI_RED.escape()+"Can't close the socket"+ Color.ANSI_BRIGHTWHITE.escape());
        }
        System.exit(0);
    }


    /**
     * this Method is used to switch Pinging to the server
     * @param state true to active a Pinging Thread, false to cancel it
     */
    public void EnablePing(boolean state){
        if(state){
            pinger.schedule(new PingerTimerTask(this),0,SOTIMEOUT*1000/2);
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



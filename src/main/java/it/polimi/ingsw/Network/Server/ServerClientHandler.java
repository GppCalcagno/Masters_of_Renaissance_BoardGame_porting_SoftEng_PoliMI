package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessagePing;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Network.Message.UpdateMesssage.MessageRequestLogin;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ServerClientHandler implements Runnable, Observer {
    /** this attribute is the Logger Of the server, is used to sent check or warning message */
    private final Logger SERVERLOGGER;

    /** this is the identifier of the player who is using the socket */
    private final int ID;

    /** this is the socket of the player */
    private final Socket clientSocket;

    /** this attribute is used to send information to the Model and controll Player Socket */
    private final Server server;


    /** this attribute is used to send message */
    private ObjectOutputStream output;
    /** this attribute is used to recive message */
    private ObjectInputStream input;

    /** this is a locker to manage the Concurrency */
    private  final Object SenderLock;
    /**
     * this is the costructor of the class
     * @param clientSocket is the connected socket of the player
     * @param server is the current Game Server
     */
    public ServerClientHandler(Socket clientSocket, Server server, int ID) throws IOException {
        SERVERLOGGER= Logger.getLogger(Server.class.getName());
        this.clientSocket = clientSocket;
        this.server=server;
        SenderLock= new Object();
        this.ID=ID;

        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            SERVERLOGGER.severe("ERROR: THREAD INITIALIZATION ");
        }
        output.writeObject(new MessageRequestLogin());

    }

    /** Thread run method  */
    @Override
    public void run() {
        ReciveMessage();
    }

    /** this method is used to listen for new messages  */
    private void ReciveMessage() {
        try {

            while(true){
                //possibile lock (non necessario per ora)
                SERVERLOGGER.info("Server wait a Message");
                Message message= (Message) input.readObject();

                if(message!=null){
                    SERVERLOGGER.info("Messagge recived" + "(from " + message.getNickname()+")"+ ":" + message.getMessageType());
                    switch (message.getMessageType()){
                        case PING: sendMessage(new MessagePing()); break;
                        case LOGIN: server.addIDname(ID,message.getNickname(), message); break;
                        default: server.recivedMessage(message);
                    }
                }
            }//finewhile
        }
        catch (IOException | ClassNotFoundException | NullPointerException  e) {
            e.printStackTrace();
            SERVERLOGGER.severe("ERROR: CLIENT MESSAGE RECEPTION ");
            server.disconnect(ID);
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
            SERVERLOGGER.severe("ERROR: CAN'T SENT MESSAGE: DISCONNECT PLAYER");
            server.disconnect(ID);
        }
    }


    public void clocsesocket(){
        try { clientSocket.close();}
            catch (IOException ignore) {}
    }
}

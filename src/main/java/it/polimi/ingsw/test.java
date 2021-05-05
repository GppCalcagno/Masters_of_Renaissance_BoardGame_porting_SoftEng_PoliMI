package it.polimi.ingsw;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageLogin;
import it.polimi.ingsw.Network.message.MessageNumPlayers;
import it.polimi.ingsw.Network.message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SignatureSpi;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        Socket socket;
        socket=new Socket();
        Scanner in= new Scanner(System.in);

        try {
            socket.connect(new InetSocketAddress("127.0.0.1",1234));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectOutputStream outputStm = null;
        try {
            outputStm = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStm = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("manda 1o messaggio");
        in.next();

        Message message= new MessageNumPlayers("cosa", MessageType.NUMPLAYERS,2);

        try {
            outputStm.writeObject(message);
            outputStm.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("manda 2o messaggio");
        in.next();

        Message message1= new MessageLogin("cosa", MessageType.LOGIN);
        try {
            outputStm.writeObject(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("manda 3o messaggio");
        in.next();


        Message message2= new MessageLogin("cosa", MessageType.LOGIN);
        try {
            outputStm.writeObject(message2);
        } catch (IOException e) {
            System.out.println("GIUSTO");
        }

        System.out.println("termina");
        in.next();

        try {
            outputStm.writeObject(message2);
        } catch (IOException e) {
            System.out.println("GIUSTO");
        }


    }
}

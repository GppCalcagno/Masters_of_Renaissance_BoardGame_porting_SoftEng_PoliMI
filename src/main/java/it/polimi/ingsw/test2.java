package it.polimi.ingsw;

import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageChooseLeaderCards;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageDisconnect;
import it.polimi.ingsw.Network.Message.ClientMessage.MessageLogin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class test2 {

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



        System.out.println("manda 1o messaggio: LOGIN");
        in.next();

        Message message1= new MessageLogin("cosa");
        try {
            outputStm.writeObject(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("manda 3o messaggio: comando a caso");
        in.next();

        Message message3= new MessageChooseLeaderCards("cosa",2,3);

        try {
            outputStm.writeObject(message3);
            outputStm.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("termina");
        in.next();

        try {
            outputStm.writeObject(new MessageDisconnect("cosa"));
        } catch (IOException e) {
            System.out.println("ERRORE");
        }


    }
}

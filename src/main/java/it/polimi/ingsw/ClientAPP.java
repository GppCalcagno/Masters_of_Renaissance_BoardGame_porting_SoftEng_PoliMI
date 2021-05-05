package it.polimi.ingsw;

import it.polimi.ingsw.Network.Client.ClientSocket;

public class ClientAPP {

    public static void main(String[] args) {
        ClientSocket Client1= new ClientSocket("127.0.0.1",1234);
    }
}

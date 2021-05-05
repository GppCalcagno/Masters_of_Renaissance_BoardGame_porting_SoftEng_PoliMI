package it.polimi.ingsw;

import it.polimi.ingsw.Network.Server.Server;

public class ServerAPP {

    public static void main(String[] args) {
        int ServerPort=1234; //default

        if(args.length>1){
            int i=0;
            while(!args[i].equals("-port") && i<args.length)i++;

            if((i+1)<args.length){
                ServerPort= Integer.parseInt(args[i+1]);
            }
        }
        Server server= new Server(ServerPort);
        server.startServer();
    }
}

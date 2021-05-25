package it.polimi.ingsw;

import it.polimi.ingsw.Network.Server.Server;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class ServerAPP {

    public static void main(String[] args) {
        int ServerPort=1234; //default
        Logger LOGGER= Logger.getLogger(Server.class.getName());

        if(args.length>1) {
            int i=0;
            while(i<args.length-1 && !args[i].equals("--port"))i++;

                try {
                    ServerPort = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    LOGGER.severe("ERROR: WRONG PORT PARAMETER");
                    System.exit(2);
                }
        }

        Server server= new Server(ServerPort);
        server.startServer();
    }
}

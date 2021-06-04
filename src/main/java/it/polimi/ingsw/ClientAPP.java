package it.polimi.ingsw;

import it.polimi.ingsw.Client.ClientController;

public class ClientAPP {

    public static void main(String[] args) {
        boolean isCli;
        boolean isOnline;

        if(args.length==2) {

            isCli= !args[0].equals("--gui") && !args[0].equals("-gui");
            isOnline= !args[1].equals("--offline") && !args[1].equals("-offline");

            new ClientController(isCli,isOnline);
        }
        else {
            System.out.println("WRONG PARAMETERS. You must use --cli/gui --offline/online ");
        }

    }
}

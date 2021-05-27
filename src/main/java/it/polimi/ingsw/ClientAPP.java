package it.polimi.ingsw;

import it.polimi.ingsw.Client.ClientController;
import javafx.application.Application;

public class ClientAPP {

    public static void main(String[] args) {
        boolean isCli;
        boolean isOnline;

        if(args.length==2) {
            if(args[0].equals("--gui")||args[0].equals("-gui")){
                isCli=false;
            }
            else{
                isCli=true;
            }
            if(args[1].equals("--offline")||args[1].equals("-offline")) {
                isOnline=false;
            }
            else{
                isOnline=true;
            }

            new ClientController(isCli,isOnline);

        }
        else {
            System.out.println("WRONG PARAMETERS. You must use --cli/gui --offline/online ");
        }

    }
}

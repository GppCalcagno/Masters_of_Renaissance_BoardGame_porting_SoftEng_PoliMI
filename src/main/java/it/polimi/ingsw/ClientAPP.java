package it.polimi.ingsw;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.Gui;
import it.polimi.ingsw.View.ViewInterface;

public class ClientAPP {

    public static void main(String[] args) {
        ClientController clientController= new ClientController();
        ViewInterface view = null;

        if(args.length==1) {
            if(args[0].equals("--cli")||args[0].equals("-cli")){
                view= new Cli(clientController.getBoard(),clientController);
            }
            else{
                if(args[0].equals("--gui")||args[0].equals("-gui")){
                    Gui.main(args);
                }
            }
        }
        clientController.setView(view);
        view.start();

    }
}

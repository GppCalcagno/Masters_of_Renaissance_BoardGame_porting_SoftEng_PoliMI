package it.polimi.ingsw;

import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.GuiJavaFX;
import javafx.application.Application;

public class ClientAPP {

    public static void main(String[] args) {

        if(args.length==1) {
            if(args[0].equals("--cli")||args[0].equals("-cli")){
                new Cli();
            }
            else{
                if(args[0].equals("--gui")||args[0].equals("-gui")){
                    Application.launch(GuiJavaFX.class);
                }
            }
        }
    }
}

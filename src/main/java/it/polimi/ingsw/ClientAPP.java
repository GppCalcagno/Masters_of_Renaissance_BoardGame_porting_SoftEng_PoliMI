package it.polimi.ingsw;

import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.Gui;
import it.polimi.ingsw.View.Gui.GuiJavaFX;
import it.polimi.ingsw.View.ViewInterface;
import javafx.application.Application;

public class ClientAPP {

    public static void main(String[] args) {
        ViewInterface view = null;

        if(args.length==1) {
            if(args[0].equals("--cli")||args[0].equals("-cli")){
                view= new Cli();
            }
            else{
                if(args[0].equals("--gui")||args[0].equals("-gui")){
                    Application.launch(GuiJavaFX.class);
                   // view = new Gui();
                }
            }
        }
        view.init();

    }
}

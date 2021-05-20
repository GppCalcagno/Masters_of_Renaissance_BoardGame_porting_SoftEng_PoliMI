package it.polimi.ingsw.View.Cli;

import java.util.Scanner;

public class InputReader implements Runnable{

    private ActionParser parser;
    private Scanner input;

    public InputReader(ActionParser parser) {
        this.input = new Scanner(System.in);
        this.parser = parser;
    }

    /**
     * this method take the input form player
     */
    @Override
    public void run() {
        while (true){
            String line = input.nextLine();
            parser.commandParser(line);
        }

    }



}

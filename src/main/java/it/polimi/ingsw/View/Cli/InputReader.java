package it.polimi.ingsw.View.Cli;

import java.util.Scanner;

public class InputReader implements Runnable{

    ActionParser parser;
    Scanner input;


    public InputReader(ActionParser parser) {
        Scanner input = new Scanner(System.in);
        this.parser = parser;
    }

    /**
     * this method take the input form player
     */
    @Override
    public void run() {
        input.reset();
        String line = input.nextLine();
        parser.commandParcer(line);
    }



}

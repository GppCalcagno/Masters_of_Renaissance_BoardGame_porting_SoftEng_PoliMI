package it.polimi.ingsw.View.Cli;

import java.util.Scanner;

public class InputReader implements Runnable{

    private String line;

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        line = input.nextLine();
    }

    public String getLine() {
        return line;
    }
}

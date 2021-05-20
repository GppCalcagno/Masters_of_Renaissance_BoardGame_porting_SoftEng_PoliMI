package it.polimi.ingsw.View.Cli;

public enum Color {
    ANSI_BRIGHTBLUE("\u001B[94m"),
    ANSI_BRIGHTWHITE("\u001B[97m"),
    ANSI_BRIGHTPURPLE("\u001B[95m"),
    ANSI_BRIGHTRED("\u001B[91m"),
    ANSI_BRIGHDARK("\u001B[90m"),
    ANSI_BRIGHTYELLOW("\u001B[93m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_RED("\u001B[31m");

    static final String RESET = "\u001B[0m";

    private String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}

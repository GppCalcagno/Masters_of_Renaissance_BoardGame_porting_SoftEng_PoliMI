package it.polimi.ingsw.Network.message;

import java.util.List;

public class MessageInizialization extends Message{
    private static final long serialVersionUID = -2092724752083413983L;

    private List<List<String>> leaderCardsToChoose;

    private List<String> playersNameList;

    private String[][][] devCardDeckMethod;

    private String[][] marketTray;

    private String remainingMarble;

    public MessageInizialization(String nickname, List<List<String>> leaderCardsToChoose, List<String> playersNameList, String[][][] devCardDeckMethod, String[][] marketTray, String remainingMarble) {
        super(nickname, MessageType.INITIALSITUATIONGAME);
        this.leaderCardsToChoose = leaderCardsToChoose;
        this.playersNameList = playersNameList;
        this.devCardDeckMethod = devCardDeckMethod;
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
    }

    public List<List<String>> getLeaderCardsToChoose() {
        return leaderCardsToChoose;
    }

    public List<String> getPlayersNameList() {
        return playersNameList;
    }

    public String[][][] getDevCardDeckMethod() {
        return devCardDeckMethod;
    }

    public String[][] getMarketTray() {
        return marketTray;
    }

    public String getRemainingMarble() {
        return remainingMarble;
    }
}

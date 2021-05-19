package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;
import java.util.Map;

public class MessageStartGame extends Message {
    private static final long serialVersionUID = -2092724752083413983L;

    private Map<String,List<String>> leaderCardsToChoose;

    private List<String> playersNameList;

    private String[][][] devCardDeckMethod;

    private String[][] marketTray;

    private String remainingMarble;

    public MessageStartGame(Map<String,List<String>>leaderCardsToChoose, List<String> playersNameList, String[][][] devCardDeckMethod, String[][] marketTray, String remainingMarble) {
        super("server", MessageType.STARTGAME);
        this.leaderCardsToChoose = leaderCardsToChoose;
        this.playersNameList = playersNameList;
        this.devCardDeckMethod = devCardDeckMethod;
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.onGameStart(playersNameList,leaderCardsToChoose,devCardDeckMethod,marketTray,remainingMarble);

    }
}

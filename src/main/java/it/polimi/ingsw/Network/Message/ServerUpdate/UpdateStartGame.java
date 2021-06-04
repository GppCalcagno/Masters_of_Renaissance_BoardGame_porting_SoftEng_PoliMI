package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;
import java.util.Map;

public class UpdateStartGame extends Update {
    private static final long serialVersionUID = -2092724752083413983L;

    private Map<String,List<String>> leaderCardsToChoose;

    private List<String> playersNameList;

    private String[][][] devCardDeckMethod;

    private String[][] marketTray;

    private String remainingMarble;

    public UpdateStartGame(Map<String,List<String>>leaderCardsToChoose, List<String> playersNameList, String[][][] devCardDeckMethod, String[][] marketTray, String remainingMarble) {
        super("server");
        this.leaderCardsToChoose = leaderCardsToChoose;
        this.playersNameList = playersNameList;
        this.devCardDeckMethod = devCardDeckMethod;
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().onGameStart(playersNameList,leaderCardsToChoose,devCardDeckMethod,marketTray,remainingMarble);
        view.onUpdateStartGame();
    }
}

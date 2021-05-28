package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;
import java.util.Map;

public class MessageReconnect extends Message {


    private List<String> playersNameList;
    private String currentPlayer;
    private String[][][] devCardDeck;
    private String[][] marketTray;
    private String remainingMarble;
    private List<String> marbleBuffer;

    private String[][] warehouse;
    private Map<String, Integer> extraChest;
    private Map<String,Integer> strongbox;

    private Map<String, Integer> playersPosition;
    private Map<String, boolean[]> playersPopFavoriteTile;
    private int blackcrosstoken;

    private List<String> leaderCards;
    private String[][] slotDevCards;

    private String activeDevCardToBuy;
    private String activeDevCardProd;

    private String lastTokerUsed;
    private String lastTokenUsedColor;

    public MessageReconnect(
            String nickname, List<String> playersNameList,
            String currentPlayer, String[][][] devCardDeck, String[][] marketTray,
            String remainingMarble,List<String> marbleBuffer, String[][] warehouse, Map<String, Integer> extraChest,
            Map<String, Integer> strongbox, Map<String, Integer> playersPosition,
            Map<String, boolean[]> playersPopFavoriteTile, int blackcrosstoken,
            List<String> leaderCards, String[][] slotDevCards, String activeDevCardToBuy,
            String activeDevCardProd) {



        super(nickname, MessageType.RECONNECT);
        this.playersNameList = playersNameList;
        this.currentPlayer = currentPlayer;
        this.devCardDeck = devCardDeck;
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
        this.marbleBuffer=marbleBuffer;
        this.warehouse = warehouse;
        this.extraChest = extraChest;
        this.strongbox = strongbox;
        this.playersPosition = playersPosition;
        this.playersPopFavoriteTile = playersPopFavoriteTile;
        this.blackcrosstoken = blackcrosstoken;
        this.leaderCards = leaderCards;
        this.slotDevCards = slotDevCards;
        this.activeDevCardToBuy = activeDevCardToBuy;
        this.activeDevCardProd = activeDevCardProd;
    }

    @Override
    public void update(ViewInterface view){


        if(view.getPlayerBoard().getNickname().equals(getNickname())){
            view.getPlayerBoard().resume( playersNameList, currentPlayer, devCardDeck,  marketTray,
                    remainingMarble, marbleBuffer, warehouse,  extraChest, strongbox,  playersPosition,
                    playersPopFavoriteTile, blackcrosstoken, leaderCards, slotDevCards,  activeDevCardToBuy, activeDevCardProd);
        }
        view.onResume(getNickname());


    }






}

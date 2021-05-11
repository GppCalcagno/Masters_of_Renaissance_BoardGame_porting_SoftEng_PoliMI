package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.View.Cli.Structure.*;
import it.polimi.ingsw.View.view;

public class Cli implements view {

    @Override
    public void askServerInfo() {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askNumPlayer() {

    }

    @Override
    public void askChooseLeaderCards() {

    }

    @Override
    public void askChooseResourcesFirstTurn() {

    }

    @Override
    public void askChooseAction() {

    }

    @Override
    public void askActiveLeaderAction() {

    }

    @Override
    public void askTakeMarble() {

    }

    @Override
    public void askAfterTakeMarble() {

    }

    @Override
    public void askExchange() {

    }

    @Override
    public void askAddDiscardMarble() {

    }

    @Override
    public void askSelectDevCard() {

    }

    @Override
    public void askActiveProducition() {

    }

    @Override
    public void askChooseResources() {

    }

    @Override
    public void askInsertCard() {

    }

    @Override
    public void askEndProduction() {

    }

    //show
    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLeaderActionBox() {
        ViewLeaderActionBox viewLeaderActionBox = new ViewLeaderActionBox();
    }

    @Override
    public void showSlotDevCard() {
        ViewSlotDevCard viewSlotDevCard = new ViewSlotDevCard();
        viewSlotDevCard.plot();
    }

    @Override
    public void showWarehouse() {
        ViewWarehouse viewWarehouse = new ViewWarehouse();
        viewWarehouse.plot();
    }

    @Override
    public void showStrongbox() {
        ViewStrongbox viewStrongbox = new ViewStrongbox();
        viewStrongbox.plot();
    }

    @Override
    public void showFaithTrack() {
        ViewFaithTrack viewFaithTrack = new ViewFaithTrack();
        viewFaithTrack.plot();
    }

    @Override
    public void showMarketTray() {
        ViewMarketTray viewMarketTray = new ViewMarketTray();
        viewMarketTray.plot();
    }

    @Override
    public void showDevCardDeck() {
        ViewDevCardDeck viewDevCardDeck = new ViewDevCardDeck();
        viewDevCardDeck.plot();
    }

    @Override
    public void showExtraChest(){
        ViewExtraChest viewExtraChest = new ViewExtraChest();
        viewExtraChest.plot();
    }

    @Override
    public void showDevCard(String ID) {

    }

    @Override
    public void showLeaderAction(String ID) {

    }

    @Override
    public void showPlayerState(String name) {

    }

    @Override
    public void showWinner() {

    }

    @Override
    public void showVictoryPoint() {

    }
}

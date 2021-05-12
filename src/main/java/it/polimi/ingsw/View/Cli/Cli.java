package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Structure.*;
import it.polimi.ingsw.View.view;

public class Cli implements view {



    @Override
    public void askServerInfo() {

    }

    @Override
    public void endturn() {

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
    public void askChooseResourcesFirstTurn(int num) {

    }

    @Override
    public void askChooseTurn() {

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
    public void askExtractMarble() {

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
    public void askSetDefaulMarbleLeaderEffect() {

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
    public void showLeaderActionBox(PlayerBoard playerBoard) {
        ViewLeaderActionBox viewLeaderActionBox = new ViewLeaderActionBox(playerBoard);
        viewLeaderActionBox.plot();
    }

    @Override
    public void showSlotDevCard(PlayerBoard playerBoard) {
        ViewSlotDevCard viewSlotDevCard = new ViewSlotDevCard(playerBoard);
        viewSlotDevCard.plot();
    }

    @Override
    public void showWarehouse(PlayerBoard playerBoard) {
        ViewWarehouse viewWarehouse = new ViewWarehouse(playerBoard);
        viewWarehouse.plot();
    }

    @Override
    public void showStrongbox(PlayerBoard playerBoard) {
        ViewStrongbox viewStrongbox = new ViewStrongbox(playerBoard);
        viewStrongbox.plot();
    }

    @Override
    public void showFaithTrack(PlayerBoard playerBoard) {
        ViewFaithTrack viewFaithTrack = new ViewFaithTrack(playerBoard);
        viewFaithTrack.plot();
    }

    @Override
    public void showMarketTray(PlayerBoard playerBoard) {
        ViewMarketTray viewMarketTray = new ViewMarketTray(playerBoard);
        viewMarketTray.plot();
    }

    @Override
    public void showDevCardDeck(PlayerBoard playerBoard) {
        ViewDevCardDeck viewDevCardDeck = new ViewDevCardDeck(playerBoard);
        viewDevCardDeck.plot();
    }

    @Override
    public void showExtraChest(PlayerBoard playerBoard){
        ViewExtraChest viewExtraChest = new ViewExtraChest(playerBoard);
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

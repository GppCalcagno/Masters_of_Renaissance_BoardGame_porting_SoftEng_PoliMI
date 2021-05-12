package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Structure.*;
import it.polimi.ingsw.View.view;

import java.io.PrintStream;

public class Cli implements view {
    PlayerBoard playerBoard;

    private final PrintStream out;

    public Cli(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
        out = System.out;
    }

    @Override
    public void askServerInfo() {

    }

    @Override
    public void endturn() {

    }

    @Override
    public void askNickname() {
        out.println("Enter your nickname: ");
    }

    @Override
    public void askNumPlayer() {
        out.println("Enter the number of players: ");
    }

    @Override
    public void askChooseLeaderCards() {
        out.println("Enter the first Leader card: ");

        out.println("Enter the second Leader card: ");
    }

    @Override
    public void askChooseResourcesFirstTurn(int num) {
        if (num == 2 || num == 3) {
            out.println("Enter a resource: ");
        }
        else {
            if (num == 4) {
                out.println("Enter the first resource: ");

                out.println("Enter the second resource: ");
            }
        }
    }

    @Override
    public void askChooseTurn() {
        out.println("Actions: \n0: Extract marbles from Market tray\n1: Buy a Development card\n2: Choose resources to activate a base production\n3: Activate production of a Development card\n4: Active production of the Leader card\n5: Activate or discard a Leader card");
        out.println("Enter the relative number: ");
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
        out.println("Do you want to extract a column or a row? [c/r]: ");

        out.println("Which number of row do you want to extract? [0-2]: ");

        out.println("Which number of column do you want to extract? [0-3]: ");
    }

    @Override
    public void askAfterTakeMarble() {
        out.println("Actions:\n0: Exchange two rows of the Warehouse depots\n1: Add or discard an extracted marble (if you discard a marble, you'll give a faith point to each other player)\n2: Choose with which resource you'll convert an extracted white marble (if yuo have the special effect of the relative Leader card)");
        out.println("Enter the relative action's number: ");
    }

    @Override
    public void askExchange() {
        out.println("Enter the first row to exchange: ");

        out.println("Enter the second row to exchange: ");
    }

    @Override
    public void askAddDiscardMarble() {
        out.println("0: Discard this marble\n1: Add this marble to the Warehouse depots");
        out.println("Enter your choice: ");
    }

    @Override
    public void askSetDefaulMarbleLeaderEffect() {
        out.println("Enter the resource with which do you want to convert the White marble [0-1]: ");
    }

    @Override
    public void askSelectDevCard() {
        out.println("Enter the Development card's ID to buy: ");
    }


    @Override
    public void askChooseResourcesPurchaseDevCard() {
        out.println("Enter the type of resources from the Warehouse depots: ");

        out.println("Enter the type of resources from the Strongbox: ");

        if (!playerBoard.getExtrachest().isEmpty()) {
            out.println("Enter the type of resources from the Extra Chest: ");

            out.println("Enter the number of resources: ");
        }
    }

    @Override
    public void askInsertCard() {
        out.println("Enter the slot in which do you want to insert the card [0-2]: ");
    }

    @Override
    public void askActiveProducition() {

    }

    @Override
    public void askEndProduction() {

    }

    @Override
    public void askUpdateStateLeaderAction() {
        out.println("Which card do you want to activate or discard [0-1]: ");

        out.println("Do you want to activate or discard this card? [0-1]: ");
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

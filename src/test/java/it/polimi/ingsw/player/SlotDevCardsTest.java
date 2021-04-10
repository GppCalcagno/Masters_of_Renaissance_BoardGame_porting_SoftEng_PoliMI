package it.polimi.ingsw.player;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.stub.stubDevelopmentCard;
import org.junit.jupiter.api.Test;
import stub.stubResources;

import static org.junit.jupiter.api.Assertions.*;

class SlotDevCardsTest {

    @Test
    void maxLevelPurchase() {
        stubDevelopmentCard c = new stubDevelopmentCard(1);
        SlotDevCards s = new SlotDevCards();

        assertEquals(false, s.maxLevelPurchase(c));

    }

    @Test
    void insertCards() throws CantDoIt {
        stubDevelopmentCard c = new stubDevelopmentCard(0);
        stubDevelopmentCard c1 = new stubDevelopmentCard(1);
        SlotDevCards s = new SlotDevCards();



        s.insertCards(1,c);
        s.insertCards(1,c1);

        s.show();

    }

    @Test
    void checkUsage() throws CantDoIt {
        stubDevelopmentCard c = new stubDevelopmentCard(0);
        stubDevelopmentCard c1 = new stubDevelopmentCard(1);
        stubDevelopmentCard c2 = new stubDevelopmentCard(2);
        stubDevelopmentCard c3 = new stubDevelopmentCard(0);
        stubDevelopmentCard c4 = new stubDevelopmentCard(1);
        SlotDevCards s = new SlotDevCards();

        s.insertCards(0,c);
        s.insertCards(0,c1);
        s.insertCards(0,c2);
        s.insertCards(1,c3);
        s.insertCards(1,c4);
        //s.show();
        assertEquals(true, s.checkUsage(c4));




    }

    @Test
    void baseProduction() {

    }

    @Test
    void cardProduction() {
    }

    @Test
    void emptyBuffer() {
        SlotDevCards s = new SlotDevCards();
        stubResources r1 = new stubResources(0);
        stubResources r2 = new stubResources(5);
        stubResources r3 = new stubResources(3);
        stubResources r4 = new stubResources(1);

        s.baseProduction(r1);
        s.baseProduction(r2);


        s.showBuffer();
        s.emptyBuffer();
        s.showBuffer();


    }
}
package it.polimi.ingsw.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.card.leadereffect.ChestLeader;
import it.polimi.ingsw.card.leadereffect.DiscountLeader;
import it.polimi.ingsw.card.leadereffect.ProductionLeader;
import it.polimi.ingsw.card.leadereffect.TrasformationMarbleLeader;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.*;
import it.polimi.ingsw.requirements.RequestedLevelDevelopmentCards;
import it.polimi.ingsw.requirements.RequestedResources;
import it.polimi.ingsw.requirements.RequestedTypeDevelopmentCards;
import it.polimi.ingsw.requirements.Requirements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeaderCardDeck {
    /**
     * This attribute is the total number of Leader Cards
     */
    private final int size = 16;

    /**
     * This attribute is a vector that contains the reference of all Leader Cards
     */
    private List<LeaderAction> leaderCardList;

    /**
     * This is the constructor method
     */
    public LeaderCardDeck() throws IOException {
        this.leaderCardList = new ArrayList<>();

        RuntimeTypeAdapterFactory<Requirements> requirementsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Requirements.class)
                .registerSubtype(RequestedResources.class)
                .registerSubtype(RequestedLevelDevelopmentCards.class)
                .registerSubtype(RequestedTypeDevelopmentCards.class);
        RuntimeTypeAdapterFactory<Resources> resourcesRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Resources.class)
                .registerSubtype(Coins.class)
                .registerSubtype(Servants.class)
                .registerSubtype(Shields.class)
                .registerSubtype(Stones.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(requirementsRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(resourcesRuntimeTypeAdapterFactory)
                .create();

        String discount = Files.readString(Paths.get("src/cardJSON/leaderCard/VettDiscountLeader_LeaderCards.json"));
        DiscountLeader[] discountLeaders = gson.fromJson(discount, DiscountLeader[].class);

        String chest = Files.readString(Paths.get("src/cardJSON/leaderCard/VettChestLeader_LeaderCards.json"));
        ChestLeader[] chestLeaders = gson.fromJson(chest, ChestLeader[].class);

        String production = Files.readString(Paths.get("src/cardJSON/leaderCard/VettProductionLeader_LeaderCards.json"));
        ProductionLeader[] productionLeaders = gson.fromJson(production, ProductionLeader[].class);

        String transformation = Files.readString(Paths.get("src/cardJSON/leaderCard/VettTrasformationMarbleLeader_LeaderCards.json"));
        TrasformationMarbleLeader[] transformationMarbleLeaders = gson.fromJson(transformation, TrasformationMarbleLeader[].class);

        this.leaderCardList.addAll(Arrays.asList(discountLeaders));
        this.leaderCardList.addAll(Arrays.asList(chestLeaders));
        this.leaderCardList.addAll(Arrays.asList(productionLeaders));
        this.leaderCardList.addAll(Arrays.asList(transformationMarbleLeaders));

        Collections.shuffle(this.leaderCardList);
    }

    /**
     * This method returns a Leader Card from the vector indicated from the index given by the Player
     * @param i index of the card
     * @return the chosen Leader Action
     */
    public LeaderAction getLeaderCardList(int i) {
        return leaderCardList.get(i);
    }

    /**
     * This method gives the chosen Leader Card to the Player
     * @param i index of the chosen Leader Card
     * @param player to which it is given the card
     */
    public void givetoPlayer (int i, Player player) {
        player.addLeaderAction(leaderCardList.remove(i));
    }

    /**
     * This method returns Leader Card's number
     * @return the number of Leader Cards
     */
    public int getSize() {
        return this.leaderCardList.size();
    }

    /**
     * This method returns the leaderCardList attribute
     * @return a List of LeaderAction
     */
    public List<LeaderAction> getLeaderCardList(){
        return this.leaderCardList;
    }

}

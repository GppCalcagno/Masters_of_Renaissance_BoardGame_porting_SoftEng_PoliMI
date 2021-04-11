package it.polimi.ingsw.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
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

public class LeaderCardDeck {
    /**
     * This attribute is the total number of Leader Cards
     */
    private final int size = 16;

    /**
     * This attribute is a vector that contains the reference of all Leader Cards
     */
    private ArrayList<LeaderAction> leaderCardList;

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

        String discount = Files.readString(Paths.get("C:\\Users\\giochi\\IdeaProjects\\ingswAM2021-Caironi-Calcagno-Chiurco\\src\\cardJSON\\VettDiscountLeader_LeaderCards.json"));
        DiscountLeader[] discountLeaders = gson.fromJson(discount, DiscountLeader[].class);

        String chest = Files.readString(Paths.get("C:\\Users\\giochi\\IdeaProjects\\ingswAM2021-Caironi-Calcagno-Chiurco\\src\\cardJSON\\VettChestLeader_LeaderCards.json"));
        ChestLeader[] chestLeaders = gson.fromJson(chest, ChestLeader[].class);

        String production = Files.readString(Paths.get("C:\\Users\\giochi\\IdeaProjects\\ingswAM2021-Caironi-Calcagno-Chiurco\\src\\cardJSON\\VettProductionLeader_LeaderCards.json"));
        ProductionLeader[] productionLeaders = gson.fromJson(production, ProductionLeader[].class);

        String transformation = Files.readString(Paths.get("C:\\Users\\giochi\\IdeaProjects\\ingswAM2021-Caironi-Calcagno-Chiurco\\src\\cardJSON\\VettTrasformationMarbleLeader_LeaderCards.json"));
        TrasformationMarbleLeader[] transformationMarbleLeaders = gson.fromJson(transformation, TrasformationMarbleLeader[].class);

        this.leaderCardList.addAll(Arrays.asList(discountLeaders));
        this.leaderCardList.addAll(Arrays.asList(chestLeaders));
        this.leaderCardList.addAll(Arrays.asList(productionLeaders));
        this.leaderCardList.addAll(Arrays.asList(transformationMarbleLeaders));
    }

    /**
     * This method returns a Leader Card from the vector indicated from the index given by the Player
     * @param i index of the card
     * @return the chosen Leader Action
     */
    public LeaderAction getLeaderCardVet(int i) {
        return leaderCardList.get(i);
    }

    /**
     * This method gives the chosen Leader Card to the Player
     * @param i index of the chosen Leader Card
     * @param player to which it is given the card
     */
    public void givetoPlayer (int i, Player player) {
        player.addLeaderAction(leaderCardList.get(i));
    }

    public void addleaderAction(LeaderAction leaderAction){
        if(leaderCardList.size()<=size)
            leaderCardList.add(leaderAction);
    }

    public int getSize() {
        return 0;
    }
}

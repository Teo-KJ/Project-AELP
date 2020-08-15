package com.example.aelpfacilitator.Entity;

import java.io.Serializable;

public class CommunityChest extends Card implements Serializable {

    public CommunityChest() {

    }

    public CommunityChest(String id, String cardTitle, String cardContent, boolean isDrawn, boolean updated, String ownerID){
        super(id, cardTitle, cardContent, isDrawn, updated, ownerID);
    }
}
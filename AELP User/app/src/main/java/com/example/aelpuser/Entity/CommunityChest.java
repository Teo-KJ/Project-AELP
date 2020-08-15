package com.example.aelpuser.Entity;

import java.io.Serializable;

public class CommunityChest extends Card implements Serializable {

    public CommunityChest() {

    }

    public CommunityChest(String id, String cardTitle, String cardContent, String addNotes, boolean isDrawn, boolean updated, String ownerID){
        super(id, cardTitle, cardContent, addNotes, isDrawn, updated, ownerID);
    }
}

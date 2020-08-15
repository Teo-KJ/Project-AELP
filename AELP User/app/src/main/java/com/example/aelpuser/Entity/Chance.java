package com.example.aelpuser.Entity;

import java.io.Serializable;

public class Chance extends Card implements Serializable {

    public Chance() {

    }

    public Chance(String id, String cardTitle, String cardContent, String addNotes, boolean isDrawn, boolean updated, String ownerID){
        super(id, cardTitle, cardContent, addNotes, isDrawn, updated, ownerID);
    }
}
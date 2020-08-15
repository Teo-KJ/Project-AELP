package com.example.aelp_overall_dashboard.Entity;

import java.io.Serializable;

public class Chance extends Card implements Serializable {

    public Chance() {

    }

    public Chance(String id, String cardTitle, String cardContent, boolean isDrawn, boolean updated, String ownerID){
        super(id, cardTitle, cardContent, isDrawn, updated, ownerID);
    }
}
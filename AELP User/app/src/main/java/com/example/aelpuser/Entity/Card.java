package com.example.aelpuser.Entity;

public class Card {
    private String id, cardTitle, cardContent, ownerID, addNotes;
    private boolean isDrawn, updated;

    public Card() {

    }

    public Card(String id, String cardTitle, String cardContent, String addNotes, boolean isDrawn, boolean updated, String ownerID){
        this.id = id;
        this.cardTitle = cardTitle;
        this.cardContent = cardContent;
        this.addNotes = addNotes;
        this.isDrawn = isDrawn;
        this.updated = updated;
        this.ownerID = ownerID;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public boolean isDrawn() {
        return isDrawn;
    }

    public void setDrawn(boolean drawn) {
        isDrawn = drawn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public String getAddNotes() {
        return addNotes;
    }

    public void setAddNotes(String addNotes) {
        this.addNotes = addNotes;
    }
}

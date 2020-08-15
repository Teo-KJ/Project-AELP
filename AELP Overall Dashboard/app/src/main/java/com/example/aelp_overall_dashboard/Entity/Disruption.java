package com.example.aelp_overall_dashboard.Entity;

import java.io.Serializable;

public class Disruption implements Serializable {

    private String id, title, message;
    private boolean updated, shownStatus;

    public Disruption(){

    }

    public Disruption(String id, String title, String message, boolean shownStatus, boolean updated) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.shownStatus = shownStatus;
        this.updated = updated;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShownStatus() {
        return shownStatus;
    }

    public void setShownStatus(boolean shownStatus) {
        this.shownStatus = shownStatus;
    }
}

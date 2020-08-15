package com.example.aelpuser.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String id, chosenCharacter, nickName;
    private double balance;
    private int lastDiceValue;
    private boolean isJailed;

    public User() {

    }

    public User(String id, String chosenCharacter, String nickName, double balance,
                int lastDiceValue, boolean isJailed){
        this.id = id;
        this.chosenCharacter = chosenCharacter;
        this.nickName = nickName;
        this.lastDiceValue = lastDiceValue;
        this.balance = balance;
        this.isJailed = isJailed;
    }

    public String getId() {
        return id;
    }

    public String getChosenCharacter() {
        return chosenCharacter;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getLastDiceValue() {
        return lastDiceValue;
    }

    public void setLastDiceValue(int lastDiceValue) {
        this.lastDiceValue = lastDiceValue;
    }

    public boolean isJailed() {
        return isJailed;
    }

    public void setJailed(boolean jailed) {
        isJailed = jailed;
    }
}
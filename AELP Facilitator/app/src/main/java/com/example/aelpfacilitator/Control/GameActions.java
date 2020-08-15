package com.example.aelpfacilitator.Control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.aelpfacilitator.DAOHelper.CardFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.DisruptionFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.UserFirebaseHelper;
import com.example.aelpfacilitator.Entity.Chance;
import com.example.aelpfacilitator.Entity.CommunityChest;
import com.example.aelpfacilitator.Entity.Disruption;
import com.example.aelpfacilitator.Entity.Property;
import com.example.aelpfacilitator.R;
import java.util.ArrayList;

public class GameActions extends AppCompatActivity {

    ArrayList<Property> allPropertiesList = new ArrayList<>();
    ArrayList<CommunityChest> allComChestCardsList = new ArrayList<>();
    ArrayList<Chance> allChanceCardsList = new ArrayList<>();
    ArrayList<Disruption> allDisruptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_actions);

        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton checkProperty = findViewById(R.id.checkPropertyBtn);
        checkProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActions.this, CheckGameActions.class);
                intent.putExtra("option", 1);
                startActivity(intent);
            }
        });

        ImageButton checkCC = findViewById(R.id.checkCommunityChestBtn);
        checkCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActions.this, CheckGameActions.class);
                //intent.putExtra("check", 0);
                intent.putExtra("option", 2);
                startActivity(intent);
            }
        });

        ImageButton checkChance = findViewById(R.id.checkChanceBtn);
        checkChance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActions.this, CheckGameActions.class);
                //intent.putExtra("check", 1);
                intent.putExtra("option", 3);
                startActivity(intent);
            }
        });

        ImageButton disruption = findViewById(R.id.manageDisruptionsButton);
        disruption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActions.this, CheckGameActions.class);
                intent.putExtra("option", 4);
                startActivity(intent);
            }
        });

        ImageButton restart = findViewById(R.id.restartGameBtn);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete all users
                new UserFirebaseHelper().deleteAllUsers();

                // Reset all properties
                new PropertyFirebaseHelper().readProperties(new PropertyFirebaseHelper.DataStatus() {
                    @Override
                    public void DataLoaded(ArrayList<Property> properties) {
                        allPropertiesList = properties;
                    }
                });
                for (Property a : allPropertiesList){
                    new PropertyFirebaseHelper().updatePropertyOwner(a.getId(), "");
                    new PropertyFirebaseHelper().updatePropertyPurchaseStatus(a.getId(), false);
                }

                // Reset all Community Chest cards
                new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
                    @Override
                    public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) {
                        allComChestCardsList = cardList;
                    }

                    @Override
                    public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
                });
                for (CommunityChest a : allComChestCardsList){
                    new CardFirebaseHelper().updateCommunityChestCardOwner(a.getId(), "");
                    new CardFirebaseHelper().updateCommunityChestCardDrawn(a.getId(), false);
                }

                // Reset all Chance cards
                new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
                    @Override
                    public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

                    @Override
                    public void ChanceDataLoaded(ArrayList<Chance> cardList) {
                        allChanceCardsList = cardList;
                    }
                });
                for (Chance a : allChanceCardsList){
                    new CardFirebaseHelper().updateChanceCardOwner(a.getId(), "");
                    new CardFirebaseHelper().updateChanceCardDrawn(a.getId(), false);
                }

                // Reset Disruptions
                new DisruptionFirebaseHelper().readDisruptions(new DisruptionFirebaseHelper.DataStatus() {
                    @Override
                    public void DataLoaded(ArrayList<Disruption> disruptionsList) {
                        allDisruptions = disruptionsList;
                    }
                });
                for (Disruption a : allDisruptions){
                    new DisruptionFirebaseHelper().updateDisruptionDetails(a.getId(), "updated", false);
                    new DisruptionFirebaseHelper().updateDisruptionDetails(a.getId(), "shownStatus", false);
                }
            }
        });
    }
}

package com.example.aelpfacilitator.Dialogue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpfacilitator.DAOHelper.CardFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.DisruptionFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.UserFirebaseHelper;
import com.example.aelpfacilitator.Entity.Chance;
import com.example.aelpfacilitator.Entity.CommunityChest;
import com.example.aelpfacilitator.Entity.Disruption;
import com.example.aelpfacilitator.Entity.User;
import com.example.aelpfacilitator.R;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;

public class ViewCard extends AppCompatActivity {

    ArrayList<Disruption> disruptions = new ArrayList<>();
    Disruption disrupt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_view_card);
        final TextView name = findViewById(R.id.cardTitleTxtView), content = findViewById(R.id.cardContentTxtView);
        final ToggleButton drawnStatus = findViewById(R.id.toggleDrawnStatus);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.5));

        final int checkNum = getIntent().getIntExtra("option", 2);

        if (checkNum == 2){
            final String communityChestCardID = getIntent().getStringExtra("ComChestCards");
            new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
                @Override
                public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) {
                    for (CommunityChest a : cardList){
                        if (a.getId().equalsIgnoreCase(communityChestCardID)){
                            name.setText(a.getCardTitle());
                            content.setText(a.getCardContent());
                            identifyOwner(a.getOwnerID());
                            if (a.isDrawn()) drawnStatus.setChecked(true);
                            else drawnStatus.setChecked(false);
                        }
                    }
                }

                @Override
                public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
            });
        }
        else if (checkNum == 3){
            final String chanceCardID = getIntent().getStringExtra("ChanceCards");
            new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
                @Override
                public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

                @Override
                public void ChanceDataLoaded(ArrayList<Chance> cardList) {
                    for (Chance a : cardList) {
                        if (a.getId().equalsIgnoreCase(chanceCardID)) {
                            name.setText(a.getCardTitle());
                            content.setText(a.getCardContent());
                            identifyOwner(a.getOwnerID());
                            if (a.isDrawn()) drawnStatus.setChecked(true);
                            else drawnStatus.setChecked(false);
                        }
                    }
                }
            });
        }
        else{
            TextView drawnBy = findViewById(R.id.drawnTextView);
            drawnBy.setVisibility(View.INVISIBLE);
            final String disruptionID = getIntent().getStringExtra("selectedDisruption");
            new DisruptionFirebaseHelper().readDisruptions(new DisruptionFirebaseHelper.DataStatus() {
                @Override
                public void DataLoaded(ArrayList<Disruption> disruptionsList) {
                    disruptions = disruptionsList;
                    for (Disruption d : disruptionsList){
                        if (d.getId().equalsIgnoreCase(disruptionID)){
                            disrupt = d;
                            name.setText(d.getTitle());
                            content.setText(d.getMessage());
                            if (d.isShownStatus()) drawnStatus.setChecked(true);
                            else drawnStatus.setChecked(false);
                        }
                    }
                }
            });

            drawnStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        disrupt.setShownStatus(true);
                        new DisruptionFirebaseHelper().updateDisruptionDetails(disruptionID, "shownStatus", true);

                        for (Disruption d : disruptions){
                            new DisruptionFirebaseHelper().updateDisruptionDetails(d.getId(), "updated", false);
                        }
                        disrupt.setUpdated(true);
                        new DisruptionFirebaseHelper().updateDisruptionDetails(disrupt.getId(), "updated",true);
                    }
                    else {
                        disrupt.setShownStatus(false);
                        new DisruptionFirebaseHelper().updateDisruptionDetails(disruptionID, "shownStatus", false);
                    }
                }
            });
        }

        Button noted = findViewById(R.id.notedBtn);
        noted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void identifyOwner(final String id){
        final TextView owner = findViewById(R.id.ownerNameTxtBox);
        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                for (User a: users){
                    if (a.getId().equalsIgnoreCase(id)) {
                        owner.setText(a.getNickName());
                    }
                }
            }
        });
    }
}

package com.example.aelp_overall_dashboard.Control;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import com.example.aelp_overall_dashboard.DAOHelper.CardFirebaseHelper;
import com.example.aelp_overall_dashboard.DAOHelper.DisruptionFirebaseHelper;
import com.example.aelp_overall_dashboard.DAOHelper.UserFirebaseHelper;
import com.example.aelp_overall_dashboard.Entity.Chance;
import com.example.aelp_overall_dashboard.Entity.CommunityChest;
import com.example.aelp_overall_dashboard.Entity.Disruption;
import com.example.aelp_overall_dashboard.Entity.User;
import com.example.myapplication.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView allPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_main);

        allPlayers = findViewById(R.id.playersGridView);
        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                PlayersGridView adapter = new PlayersGridView(MainActivity.this, R.layout.custom_players_gridview, users);
                allPlayers.setAdapter(adapter);
            }
        });

        final TextView comChestCardTitle = findViewById(R.id.comChestCardTitleTxtView),
                comChestCardContent = findViewById(R.id.comChestCardContentTxtView);

        new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
            @Override
            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) {
                for (CommunityChest a : cardList){
                    if (a.isUpdated()) {
                        comChestCardTitle.setText(a.getCardTitle());
                        comChestCardContent.setText(a.getCardContent());
                    }

                }
            }

            @Override
            public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
        });

        final TextView disruprionTitle = findViewById(R.id.disruptionTitleTxtView),
                disruptionContent = findViewById(R.id.disruptionContentTxtView);

        new DisruptionFirebaseHelper().readDisruptions(new DisruptionFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<Disruption> disruptionsList) {
                for (Disruption d : disruptionsList){
                    if (d.isUpdated()) {
                        disruprionTitle.setText(d.getTitle());
                        disruptionContent.setText(d.getMessage());
                    }
                }
            }
        });

    }
}
package com.example.aelpuser.Control;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpuser.DAOHelper.CardFirebaseHelper;
import com.example.aelpuser.Entity.Chance;
import com.example.aelpuser.Entity.CommunityChest;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class DrawCard extends AppCompatActivity {

    CommunityChest selectedCCCard = new CommunityChest();
    ArrayList<CommunityChest> allCCCards = new ArrayList<>();
    Chance selectedChanceCard = new Chance();
    ArrayList<Chance> allChanceCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_card);

        // Get user object from previous activity
        final User user = (User) getIntent().getSerializableExtra("userClass");

        // Back button
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final TextView comChestCardTitle = findViewById(R.id.comChestCardTitleTxtView), chanceCardTitle = findViewById(R.id.chanceCardTitleTxtView),
        comChestCardContent = findViewById(R.id.comChestCardContentTxtView), chanceCardContent = findViewById(R.id.chanceCardContentTxtView);
        ImageButton drawComChestCard = findViewById(R.id.drawComChestCardButton), drawChanceCard = findViewById(R.id.drawChanceCardButton);

        // Get all the cards currently not drawn
        new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
            @Override
            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) {
                for (CommunityChest a : cardList){
                    if (!a.isDrawn()) allCCCards.add(a);
                }
            }

            @Override
            public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
        });

        // Get all the cards currently not drawn
        new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
            @Override
            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

            @Override
            public void ChanceDataLoaded(ArrayList<Chance> cardList) {
                for (Chance a : cardList){
                    if (!a.isDrawn()) allChanceCards.add(a);
                }
            }
        });

        // Get a Community Chest card at random after pressing the draw button
        drawComChestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCCCard = allCCCards.get(new Random().nextInt(allCCCards.size()));
                comChestCardTitle.setText(selectedCCCard.getCardTitle());
                comChestCardContent.setText(selectedCCCard.getCardContent());
                selectedCCCard.setDrawn(true);
                new CardFirebaseHelper().updateCommunityChestCardDrawn(selectedCCCard.getId(), true);
                selectedCCCard.setOwnerID(user.getId());
                new CardFirebaseHelper().updateCommunityChestCardOwner(selectedCCCard.getId(), user.getId());

                for (CommunityChest a : allCCCards){
                    new CardFirebaseHelper().updateComChestDisplay(a.getId(), false);
                }
                selectedCCCard.setUpdated(true);
                new CardFirebaseHelper().updateComChestDisplay(selectedCCCard.getId(), true);
            }
        });

        // Get a Chance card at random after pressing the draw button
        drawChanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedChanceCard = allChanceCards.get(new Random().nextInt(allChanceCards.size()));
                chanceCardTitle.setText(selectedChanceCard.getCardTitle());
                chanceCardContent.setText(selectedChanceCard.getCardContent());
                selectedChanceCard.setDrawn(true);
                new CardFirebaseHelper().updateChanceCardDrawn(selectedChanceCard.getId(), true);
                selectedChanceCard.setOwnerID(user.getId());
                new CardFirebaseHelper().updateChanceCardOwner(selectedChanceCard.getId(), user.getId());

                for (Chance a : allChanceCards){
                    new CardFirebaseHelper().updateChanceDisplay(a.getId(), false);
                }
                selectedChanceCard.setUpdated(true);
                new CardFirebaseHelper().updateChanceDisplay(selectedChanceCard.getId(), true);
            }
        });
    }
}

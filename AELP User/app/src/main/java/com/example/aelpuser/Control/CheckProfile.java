package com.example.aelpuser.Control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.aelpuser.DAOHelper.CardFirebaseHelper;
import com.example.aelpuser.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Entity.Chance;
import com.example.aelpuser.Entity.CommunityChest;
import com.example.aelpuser.Entity.Property;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;
import java.util.ArrayList;

public class CheckProfile extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_profile);
        final ListView ownedProperties = findViewById(R.id.ownedPropertiesLV), chanceCards = findViewById(R.id.chanceCardsLV),
                        comChestCards = findViewById(R.id.communityCardsLV);

        // To obtain the user class from previous activity
        final User readUser = (User) getIntent().getSerializableExtra("userClass");
        viewProfileStatus(readUser.getId());

        // Go back
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Check all the properties owned by the user and show the name of the properties
        new PropertyFirebaseHelper().readProperties(new PropertyFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<Property> properties) {
                ArrayList<String> propertiesOwnedList = new ArrayList<>();
                for (Property prop : properties){
                    if (prop.getOwnerID() != null && prop.getOwnerID().equalsIgnoreCase(user.getId())){
                        propertiesOwnedList.add(prop.getPropertyName());
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(CheckProfile.this, android.R.layout.simple_list_item_1, propertiesOwnedList);
                ownedProperties.setAdapter(arrayAdapter);
                //ownedProperties.setOnItemClickListener();
            }
        });

        // Check all the community chest cards drawn by the user and show the name of the cards
        new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
            @Override
            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) {
                ArrayList<String> drawnCards = new ArrayList<>();
                for (CommunityChest card : cardList){
                    if (card.getOwnerID() != null && card.getOwnerID().equalsIgnoreCase(user.getId()))
                        drawnCards.add(card.getCardTitle());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(CheckProfile.this, android.R.layout.simple_list_item_1, drawnCards);
                comChestCards.setAdapter(arrayAdapter);
            }

            @Override
            public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
        });

        // Check all the chance cards drawn by the user and show the name of the cards
        new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
            @Override
            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

            @Override
            public void ChanceDataLoaded(ArrayList<Chance> cardList) {
                final ArrayList<String> drawnCards = new ArrayList<>();
                for (Chance card : cardList){
                    if (card.getOwnerID() != null && card.getOwnerID().equalsIgnoreCase(user.getId()))
                        drawnCards.add(card.getCardTitle());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(CheckProfile.this, android.R.layout.simple_list_item_1, drawnCards);
                chanceCards.setAdapter(arrayAdapter);
                chanceCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        // Special treatment for Steal Money chance card
                        if (drawnCards.get(i).equalsIgnoreCase("Steal Money")){
                            Intent intent = new Intent(CheckProfile.this, PayOtherPlayers.class);
                            intent.putExtra("userID", user.getId());
                            intent.putExtra("check", 1);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    // Function to view the profile
    public void viewProfileStatus(final String userID){
        final TextView name = findViewById(R.id.avatarNameTxtView);
        final TextView balance = findViewById(R.id.playerBalanceTxtView);

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                for (User a: users){
                    if (a.getId().equalsIgnoreCase(userID)){
                        user = a;
                        name.setText(a.getNickName() + ", " + a.getChosenCharacter());
                        balance.setText("M$" + a.getBalance() + "0");
                    }
                }
            }
        });
    }
}

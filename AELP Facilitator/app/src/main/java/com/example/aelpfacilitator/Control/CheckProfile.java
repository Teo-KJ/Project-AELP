package com.example.aelpfacilitator.Control;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpfacilitator.DAOHelper.CardFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.UserFirebaseHelper;
import com.example.aelpfacilitator.Entity.Chance;
import com.example.aelpfacilitator.Entity.CommunityChest;
import com.example.aelpfacilitator.Entity.Property;
import com.example.aelpfacilitator.Entity.User;
import com.example.aelpfacilitator.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;

public class CheckProfile extends AppCompatActivity {

    User user;
    ToggleButton sendJail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_profile);
        final ListView ownedProperties = findViewById(R.id.ownedPropertiesLV), chanceCards = findViewById(R.id.chanceCardsLV),
                comChestCards = findViewById(R.id.communityCardsLV);

        // To obtain the user class from previous activity
        final User readUser = (User) getIntent().getSerializableExtra("userClass");
        viewProfileStatus(readUser.getId());

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Check all the properties owned by the user
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

        // Check all the community chest cards drawn by the user
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

        // Check all the chance cards drawn by the user
        new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
            @Override
            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

            @Override
            public void ChanceDataLoaded(ArrayList<Chance> cardList) {
                ArrayList<String> drawnCards = new ArrayList<>();
                for (Chance card : cardList){
                    if (card.getOwnerID() != null && card.getOwnerID().equalsIgnoreCase(user.getId()))
                        drawnCards.add(card.getCardTitle());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(CheckProfile.this, android.R.layout.simple_list_item_1, drawnCards);
                chanceCards.setAdapter(arrayAdapter);
            }
        });

        sendJail = findViewById(R.id.sendJailToggleButton);
        sendJail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new UserFirebaseHelper().updateUserJailStatus(user.getId(), true);
                } else {
                    new UserFirebaseHelper().updateUserJailStatus(user.getId(), false);
                    double updatedBal = user.getBalance() - 500;
                    user.setBalance(updatedBal);
                    new UserFirebaseHelper().updateUser(user.getId(), "balance", (int)(updatedBal));
                }
            }
        });
    }

    // Function to view the profile
    public void viewProfileStatus(final String userID){
        final TextView name = findViewById(R.id.avatarNameTxtView);
        final EditText balance = findViewById(R.id.playerBalanceTxtView);
        Button save = findViewById(R.id.saveBtn);

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                for (User a: users){
                    if (a.getId().equalsIgnoreCase(userID)){
                        user = a;
                        name.setText(a.getNickName() + ", " + a.getChosenCharacter());
                        balance.setText(Double.toString(a.getBalance()));

                        if (user.isJailed()) sendJail.setChecked(true);
                        else sendJail.setChecked(false);
                    }
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double newBalance = Double.parseDouble(balance.getText().toString().trim());
                user.setBalance(newBalance);
                new UserFirebaseHelper().updateUser(userID, "balance", (int) (newBalance));
            }
        });
    }
}
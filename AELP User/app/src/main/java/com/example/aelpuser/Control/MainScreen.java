package com.example.aelpuser.Control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;
import com.example.aelpuser.Dialogue.RollDice;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // To obtain the user class from previous activity
        final User readUuser = (User) getIntent().getSerializableExtra("userClass");
        readUser(readUuser.getId());

        // Purchase property
        ImageButton buyProperty = findViewById(R.id.purchasePropertyButton);
        buyProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(MainScreen.this, PurchaseProperty.class);
                j.putExtra("userID", user.getId());
                startActivity(j);
            }
        });

        // Check user's profile
        ImageButton checkProfile = findViewById(R.id.checkProfileButton);
        checkProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(MainScreen.this, CheckProfile.class);
                j.putExtra("userClass", user);
                startActivity(j);
            }
        });

        // Roll dice
        ImageButton rollDiceButton = findViewById(R.id.rollDiceButton);
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreen.this, RollDice.class);
                i.putExtra("userClass", user);
                startActivity(i);

            }
        });

        // Pay to other players or to bank
        ImageButton payButton = findViewById(R.id.payOthersButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreen.this, PayOtherPlayers.class);
                i.putExtra("userID", user.getId());
                startActivity(i);
            }
        });

        // Once player has passed Go, to press on the button to collect the money
        ImageButton passGo = findViewById(R.id.passGoButton);
        passGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect M$200 when passing Go
                double increase = user.getBalance() + 200;
                user.setBalance(increase);
                new UserFirebaseHelper().updateUser(user.getId(), "balance", (int)(increase));
            }
        });

        // Draw Community Chest or Chance card
        ImageButton drawCard = findViewById(R.id.drawCardButton);
        drawCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreen.this, DrawCard.class);
                i.putExtra("userClass", user);
                startActivity(i);
            }
        });
    }

    // Function to read user
    public void readUser (final String userID){
        final TextView avatar = findViewById(R.id.avatarNameTxtView);
        final TextView balance = findViewById(R.id.playerBalanceTxtView);
        final TextView jailStatus = findViewById(R.id.jailStatusTextView);

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                for (User a: users){
                    if (a.getId().equalsIgnoreCase(userID)){
                        user = a;
                        avatar.setText(a.getNickName() + ", " + a.getChosenCharacter());
                        balance.setText("M$" + a.getBalance() + "0");
                        if (a.isJailed()) jailStatus.setText("You are currently Jailed.");
                        else jailStatus.setText("");
                    }
                }
            }
        });
    }

    // Prevents going back to main activity
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
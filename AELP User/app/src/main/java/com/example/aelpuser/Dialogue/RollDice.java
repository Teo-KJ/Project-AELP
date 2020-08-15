package com.example.aelpuser.Dialogue;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;

public class RollDice extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_roll_dice);

        // To obtain the user class from previous activity
        final User user = (User) getIntent().getSerializableExtra("userClass");

        // To set the size of the dialogue box
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.4));

        // Display the dice values. Dice values are generated at random, from 1 to 6.
        TextView diceValue1 = findViewById(R.id.diceFig1TxtBox);
        TextView diceValue2 = findViewById(R.id.diceFig2TxtBox);
        int value1 = (int) (Math.random() * 6 + 1);
        int value2 = (int) (Math.random() * 6 + 1);
        final int value = value1 + value2;
        diceValue1.setText(Integer.toString(value1));
        diceValue2.setText(Integer.toString(value2));

        // Save the rolled dice value into the database
        Button acknowledge = findViewById(R.id.ackButton);
        acknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                updateDiceValue(user, value);
            }
        });
    }

    // Function to update the user's dice value in the database server
    public void updateDiceValue(User user, int diceValue){
        new UserFirebaseHelper().updateUser(user.getId(), "lastDiceValue", (int)diceValue);
    }
}

package com.example.aelpuser.Control;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Dialogue.ConfirmPayMoney;
import com.example.aelpuser.R;
import java.util.ArrayList;

public class PayOtherPlayers extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_other_players);

        // To obtain the user class from previous activity
        final String userID = getIntent().getStringExtra("userID");
        final int check = getIntent().getIntExtra("check", 0);
        readUser(userID);

        // Back button
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // To obtain the list of available users for user to choose from
        final TextView amountToPay = findViewById(R.id.paidAmtTxtBox);
        final ListView players = findViewById(R.id.playersListLV);

        // Pay to bank
        Button payToBank = findViewById(R.id.payToBankButton);
        TextView pleaseStatement = findViewById(R.id.pleaseSelectTxtView);
        // As this interface shares with steal money option, to hide the pay to bank option if its to steal money
        if (check == 1){
            payToBank.setVisibility(View.INVISIBLE);
            pleaseStatement.setText("Please select the amount ($M) to steal:");
        }
        payToBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountToPay.getText().toString().isEmpty())
                    Toast.makeText(PayOtherPlayers.this, "Enter an amount to continue", Toast.LENGTH_SHORT).show();
                else {
                    double amtPaid = Double.parseDouble(amountToPay.getText().toString().trim());
                    double newBal = user.getBalance() - amtPaid;
                    user.setBalance(newBal);
                    new UserFirebaseHelper().updateUser(userID, "balance", (int)(newBal));
                }
            }
        });

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                final ArrayList<String> userList = new ArrayList<>();
                final ArrayList<User> allUsers = new ArrayList<>();

                // Filter out all players except for the user player
                for (User a: users){
                    if (!(a.getId().equalsIgnoreCase(user.getId()))) {
                        userList.add(a.getNickName());
                        allUsers.add(a);
                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(PayOtherPlayers.this, android.R.layout.simple_list_item_1, userList);
                players.setAdapter(arrayAdapter);

                // Select the player to pay to or steal money from
                players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // If the player does not enter a value, a toast is displayed to remind the player
                        if (amountToPay.getText().toString().isEmpty())
                            Toast.makeText(PayOtherPlayers.this, "Enter an amount to continue", Toast.LENGTH_SHORT).show();
                        else if (check == 1){
                            Intent intent = new Intent(PayOtherPlayers.this, ConfirmPayMoney.class);
                            intent.putExtra("userClass", user);
                            intent.putExtra("receiver", allUsers.get(i));
                            intent.putExtra("amount", amountToPay.getText().toString().trim());
                            intent.putExtra("check", 1);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(PayOtherPlayers.this, ConfirmPayMoney.class);
                            intent.putExtra("userClass", user);
                            intent.putExtra("receiver", allUsers.get(i));
                            intent.putExtra("amount", amountToPay.getText().toString().trim());
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    // Function to read the user
    public void readUser (final String userID){
        final TextView avatar = findViewById(R.id.avatarNameTxtView);
        final TextView balance = findViewById(R.id.playerBalanceTxtView);

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                for (User a: users){
                    if (a.getId().equalsIgnoreCase(userID)){
                        avatar.setText(a.getNickName() + ", " + a.getChosenCharacter());
                        balance.setText("M$" + a.getBalance() + "0");
                        user = a;
                    }
                }
            }
        });
    }
}

package com.example.aelpuser.Dialogue;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;

public class ConfirmPayMoney extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_pay_money);

        Button cancel = findViewById(R.id.cancelButton), confirm = findViewById(R.id.confirmButton);
        TextView paidAmount = findViewById(R.id.amountTxtView), receiverUser = findViewById(R.id.userTxtView),
        steal = findViewById(R.id.stealTextView), from = findViewById(R.id.fromTextView);

        // To obtain the user classes and amount from previous activity
        final User user = (User) getIntent().getSerializableExtra("userClass");
        final User receiver = (User) getIntent().getSerializableExtra("receiver");
        final double amount = Double.valueOf(getIntent().getStringExtra("amount"));
        final int check = getIntent().getIntExtra("check", 0);

        // To set the size of the dialogue box
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.4));

        // Set the user and amount on the dialogue box
        paidAmount.setText("M$" + amount + "0");
        receiverUser.setText(receiver.getNickName());

        // If function is to steal money, update the field accordingly
        if (check == 1){
            steal.setText("Steal");
            from.setText("From");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Confirm transaction to steal or pay
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 1) stealMoney(user, receiver, amount);
                else updateFinances(user, receiver, amount);
            }
        });
    }

    // Function to deduct the money from the payee while increasing the money for the receiver
    public void updateFinances(User payee, User receiver, final double amt){
        double paidAmt = payee.getBalance() - amt;
        payee.setBalance(paidAmt);
        new UserFirebaseHelper().updateUser(payee.getId(), "balance", (int)(paidAmt));

        double receivedAmt = receiver.getBalance() + amt;
        receiver.setBalance(receivedAmt);
        new UserFirebaseHelper().updateUser(receiver.getId(), "balance", (int)(receivedAmt));
    }

    // Function to steal money from the targeted player
    public void stealMoney(User thief, User victim, final double amt){
        double paidAmt = thief.getBalance() + amt;
        thief.setBalance(paidAmt);
        new UserFirebaseHelper().updateUser(thief.getId(), "balance", (int)(paidAmt));

        double receivedAmt = victim.getBalance() - amt;
        victim.setBalance(receivedAmt);
        new UserFirebaseHelper().updateUser(victim.getId(), "balance", (int)(receivedAmt));
    }
}

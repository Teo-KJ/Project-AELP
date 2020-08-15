package com.example.aelpuser.Dialogue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpuser.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Entity.Property;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmPurchaseProperty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_confirm_purchase_property);

        Button cancel = findViewById(R.id.cancelButton), confirm = findViewById(R.id.confirmButton);

        // To obtain the user and property classes from previous activity
        final User user = (User) getIntent().getSerializableExtra("userClass");
        final Property property = (Property) getIntent().getSerializableExtra("property");

        // To set the size of the dialogue box
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.75), (int) (height * 0.4));

        // Set the name of property onto the dialogue
        TextView propertyName = findViewById(R.id.propertyTxtBox);
        propertyName.setText(property.getPropertyName());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFinances(user, property);
                finish();
            }
        });
    }

    // Function to deduct the property price amount from the user, while updating the property as sold
    public void updateFinances(User payee, Property property){
        double newBalance = payee.getBalance() - property.getPrice();
        payee.setBalance(newBalance);
        new UserFirebaseHelper().updateUser(payee.getId(), "balance", (int)(newBalance));

        property.setPurchased(true);
        new PropertyFirebaseHelper().updatePropertyPurchaseStatus(property.getId(), true);
        property.setOwnerID(payee.getId());
        new PropertyFirebaseHelper().updatePropertyOwner(property.getId(), payee.getId());
    }
}

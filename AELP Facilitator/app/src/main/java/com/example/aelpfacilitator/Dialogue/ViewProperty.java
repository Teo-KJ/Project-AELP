package com.example.aelpfacilitator.Dialogue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpfacilitator.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.UserFirebaseHelper;
import com.example.aelpfacilitator.Entity.Property;
import com.example.aelpfacilitator.Entity.User;
import com.example.aelpfacilitator.R;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;

public class ViewProperty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_view_property);
        final EditText name = findViewById(R.id.nameTxtBox), colour = findViewById(R.id.colourTxtBox),
                price = findViewById(R.id.priceTxtBox), rentalPrice = findViewById(R.id.rentalpriceTxtBox);
        TextView originalName = findViewById(R.id.propertyName);
        final ToggleButton isPurchaseStatus = findViewById(R.id.togglePurchaseStatus);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.5));

        final Property property = (Property) getIntent().getSerializableExtra("property");
        name.setText(property.getPropertyName());
        originalName.setText(property.getPropertyName());
        colour.setText(property.getColour());
        price.setText(Double.toString(property.getPrice()));
        rentalPrice.setText(Double.toString(property.getRental()));
        identifyOwner(property.getOwnerID());

        if (property.isPurchased()) isPurchaseStatus.setChecked(true);
        else isPurchaseStatus.setChecked(false);

        Button cancel = findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button save = findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = name.getText().toString().trim();
                String newColour = colour.getText().toString().trim();
                String newPrice = price.getText().toString().trim();
                String newRental = rentalPrice.getText().toString().trim();
                updatePropertyDetails(property.getId(), newName, newColour, newPrice, newRental);
                finish();
            }
        });
    }

    public void updatePropertyDetails(String propertyID, String newName, String newColour, String newPrice, String newRental){
        new PropertyFirebaseHelper().updatePropertyDetails(propertyID, "propertyName", newName);
        new PropertyFirebaseHelper().updatePropertyDetails(propertyID, "colour", newColour);
        new PropertyFirebaseHelper().updatePropertyPrice(propertyID, "price", Double.valueOf(newPrice));
        new PropertyFirebaseHelper().updatePropertyPrice(propertyID, "rental", Double.valueOf(newRental));
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

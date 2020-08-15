package com.example.aelpfacilitator.Dialogue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpfacilitator.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpfacilitator.R;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProperty extends AppCompatActivity {

    String propertyName;
    String colour;
    double propertyPrice, rentalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_add_property);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.4));

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final EditText name = findViewById(R.id.nameTxtBox);
        final EditText price = findViewById(R.id.priceTxtBox);
        final EditText chosenColour = findViewById(R.id.colourTxtBox);
        final EditText rent = findViewById(R.id.rentalPriceTxtBox);
        Button confirmToAdd = findViewById(R.id.confirmAdd);
        confirmToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyName = name.getText().toString().trim();
                propertyPrice = Double.parseDouble(price.getText().toString());
                colour = chosenColour.getText().toString().trim();
                rentalPrice = Double.parseDouble(rent.getText().toString().trim());
                new PropertyFirebaseHelper().addProperty(propertyName, colour, propertyPrice, rentalPrice);
                finish();
            }
        });
    }
}

package com.example.aelpuser.Control;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpuser.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.Dialogue.ConfirmPurchaseProperty;
import com.example.aelpuser.Dialogue.PropertyListAdapter;
import com.example.aelpuser.Entity.Property;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class PurchaseProperty extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_property);

        // To obtain the user class from previous activity
        final String userID = getIntent().getStringExtra("userID");

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewProfileStatus(userID);

        // List all the available properties for sale
        final ListView allPropertiesDisplay = findViewById(R.id.propertiesListLV);
        new PropertyFirebaseHelper().readProperties(new PropertyFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(final ArrayList<Property> properties) {
                final ArrayList<Property> available = new ArrayList<>();
                for (Property prop: properties){
                    // If the property is available, then display for the user.
                    if (!prop.isPurchased()) available.add(prop);
                }

                PropertyListAdapter adapter = new PropertyListAdapter(PurchaseProperty.this, R.layout.property_adapter_view_layout, available);
                allPropertiesDisplay.setAdapter(adapter);

                // User selects a property to purchase
                allPropertiesDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // If there are insufficient balance to purchase the property
                        if (user.getBalance() < available.get(i).getPrice()){
                            Toast.makeText(PurchaseProperty.this, "You do not have sufficient funds!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            Intent intent = new Intent(PurchaseProperty.this, ConfirmPurchaseProperty.class);
                            intent.putExtra("userClass", user);
                            intent.putExtra("property", available.get(i));
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    // View the profile from the updated database
    public void viewProfileStatus(final String userID){
        final TextView balance = findViewById(R.id.playerBalanceTxtView);

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(ArrayList<User> users) {
                for (User a: users){
                    if (a.getId().equalsIgnoreCase(userID)){
                        balance.setText("M$" + a.getBalance() + "0");
                        user = a;
                    }
                }
            }
        });
    }
}

package com.example.aelpfacilitator.Control;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpfacilitator.DAOHelper.CardFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.DisruptionFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.PropertyFirebaseHelper;
import com.example.aelpfacilitator.Dialogue.AddCard;
import com.example.aelpfacilitator.Dialogue.AddProperty;
import com.example.aelpfacilitator.Dialogue.ChanceCardListAdapter;
import com.example.aelpfacilitator.Dialogue.ComChestCardListAdapter;
import com.example.aelpfacilitator.Dialogue.DisruptionListAdapter;
import com.example.aelpfacilitator.Dialogue.PropertyListAdapter;
import com.example.aelpfacilitator.Dialogue.ViewCard;
import com.example.aelpfacilitator.Dialogue.ViewProperty;
import com.example.aelpfacilitator.Entity.Chance;
import com.example.aelpfacilitator.Entity.CommunityChest;
import com.example.aelpfacilitator.Entity.Disruption;
import com.example.aelpfacilitator.Entity.Property;
import com.example.aelpfacilitator.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class CheckGameActions extends AppCompatActivity {

    ArrayList<Property> allPropertiesList = new ArrayList<>();
    ArrayList<CommunityChest> allComChestCards = new ArrayList<>();
    ArrayList<Chance> allChanceCards = new ArrayList<>();
    ArrayList<Disruption> allDisruptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_game_actions);

        // Option is used to identify the option selected from Game Options
        final int option = getIntent().getIntExtra("option", 1);

        TextView title = findViewById(R.id.titleTextView);
        final ListView displayAll = findViewById(R.id.listView);
        Button newEntity = findViewById(R.id.addBtn), reset = findViewById(R.id.resetBtn);

        // Back button
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Depending on the option, do the correct task
        switch (option){
            // Manage Properties
            case 1:
                title.setText("Check and configure properties");

                // View all properties
                new PropertyFirebaseHelper().readProperties(new PropertyFirebaseHelper.DataStatus() {
                    @Override
                    public void DataLoaded(final ArrayList<Property> properties) {
                        PropertyListAdapter adapter = new PropertyListAdapter(CheckGameActions.this, R.layout.property_adapter_view_layout, properties);
                        displayAll.setAdapter(adapter);

                        displayAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(CheckGameActions.this, ViewProperty.class);
                                intent.putExtra("property", properties.get(i));
                                startActivity(intent);
                            }
                        });
                    }
                });

                // Add new property
                newEntity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(CheckGameActions.this, AddProperty.class);
                        startActivity(i);
                    }
                });

                // Reset properties
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new PropertyFirebaseHelper().readProperties(new PropertyFirebaseHelper.DataStatus() {
                            @Override
                            public void DataLoaded(ArrayList<Property> properties) {
                                allPropertiesList = properties;
                            }
                        });
                        for (Property prop : allPropertiesList){
                            new PropertyFirebaseHelper().updatePropertyPurchaseStatus(prop.getId(), false);
                            new PropertyFirebaseHelper().updatePropertyOwner(prop.getId(), "");
                        }
                    }
                });
                break;

                // Manage Community Chest cards
            case 2:
                title.setText("Check and configure Community Chest Cards");

                // View all cards
                new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
                    @Override
                    public void ComChestDataLoaded(final ArrayList<CommunityChest> cardList) {
                        ComChestCardListAdapter adapter = new ComChestCardListAdapter(CheckGameActions.this, R.layout.card_adapter_view_layout, cardList);
                        displayAll.setAdapter(adapter);

                        displayAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(CheckGameActions.this, ViewCard.class);
                                intent.putExtra("ComChestCards", cardList.get(i).getId());
                                intent.putExtra("option", option);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
                });

                // Add new card
                newEntity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CheckGameActions.this, AddCard.class);
                        intent.putExtra("check", option);
                        startActivity(intent);
                    }
                });

                // Reset cards
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CardFirebaseHelper().readCommunityChestCards(new CardFirebaseHelper.DataStatus() {
                            @Override
                            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) {
                                allComChestCards = cardList;
                            }

                            @Override
                            public void ChanceDataLoaded(ArrayList<Chance> cardList) { }
                        });

                        for (CommunityChest a : allComChestCards) {
                            a.setDrawn(false);
                            new CardFirebaseHelper().updateCommunityChestCardDrawn(a.getId(), false);
                            a.setOwnerID("");
                            new CardFirebaseHelper().updateCommunityChestCardOwner(a.getId(), "");
                            a.setUpdated(false);
                            new CardFirebaseHelper().updateComChestDisplay(a.getId(), false);
                        }
                    }
                });
                break;

                // Manage Chance cards
            case 3:
                title.setText("Check and configure Chance Cards");

                // View all cards
                new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
                    @Override
                    public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

                    @Override
                    public void ChanceDataLoaded(final ArrayList<Chance> cardList) {
                        ChanceCardListAdapter adapter = new ChanceCardListAdapter(CheckGameActions.this, R.layout.card_adapter_view_layout, cardList);
                        displayAll.setAdapter(adapter);

                        displayAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(CheckGameActions.this, ViewCard.class);
                                intent.putExtra("ChanceCards", cardList.get(i).getId());
                                intent.putExtra("option", option);
                                startActivity(intent);
                            }
                        });
                    }
                });

                // Add new card
                newEntity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CheckGameActions.this, AddCard.class);
                        intent.putExtra("check", option);
                        startActivity(intent);
                    }
                });

                // Reset all cards
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CardFirebaseHelper().readChanceCards(new CardFirebaseHelper.DataStatus() {
                            @Override
                            public void ComChestDataLoaded(ArrayList<CommunityChest> cardList) { }

                            @Override
                            public void ChanceDataLoaded(ArrayList<Chance> cardList) {
                                allChanceCards = cardList;
                            }
                        });

                        for (Chance a : allChanceCards){
                            a.setDrawn(false);
                            new CardFirebaseHelper().updateChanceCardDrawn(a.getId(), false);
                            a.setOwnerID("");
                            new CardFirebaseHelper().updateChanceCardOwner(a.getId(), "");
                            a.setUpdated(false);
                            new CardFirebaseHelper().updateChanceDisplay(a.getId(), false);
                        }
                    }
                });
                break;

            case 4:
                title.setText("Manage Disruptions to the Game");

                // Read designed disruptions
                new DisruptionFirebaseHelper().readDisruptions(new DisruptionFirebaseHelper.DataStatus() {
                    @Override
                    public void DataLoaded(final ArrayList<Disruption> disruptionsList) {
                        DisruptionListAdapter adapter = new DisruptionListAdapter(CheckGameActions.this, R.layout.card_adapter_view_layout, disruptionsList);
                        displayAll.setAdapter(adapter);
                        displayAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(CheckGameActions.this, ViewCard.class);
                                intent.putExtra("selectedDisruption", disruptionsList.get(i).getId());
                                intent.putExtra("option", option);
                                startActivity(intent);
                            }
                        });
                    }
                });

                // Add new disruption
                newEntity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CheckGameActions.this, AddCard.class);
                        intent.putExtra("check", option);
                        startActivity(intent);
                    }
                });

                // Reset disruption shown status
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DisruptionFirebaseHelper().readDisruptions(new DisruptionFirebaseHelper.DataStatus() {
                            @Override
                            public void DataLoaded(ArrayList<Disruption> disruptionsList) {
                                allDisruptions = disruptionsList;
                            }
                        });

                        for (Disruption a : allDisruptions){
                            a.setUpdated(false);
                            new DisruptionFirebaseHelper().updateDisruptionDetails(a.getId(), "updated", false);
                            a.setShownStatus(false);
                            new DisruptionFirebaseHelper().updateDisruptionDetails(a.getId(), "shownStatus", false);
                        }
                    }
                });
                break;
        }

    }
}

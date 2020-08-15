package com.example.aelpfacilitator.Dialogue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.aelpfacilitator.DAOHelper.CardFirebaseHelper;
import com.example.aelpfacilitator.DAOHelper.DisruptionFirebaseHelper;
import com.example.aelpfacilitator.R;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddCard extends AppCompatActivity {

    String titleName, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_add);
        final int check = getIntent().getIntExtra("check", 2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.6));

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final EditText titleTB = findViewById(R.id.titleTxtBox);
        final EditText contentTB = findViewById(R.id.contentTxtBox);
        TextView title = findViewById(R.id.addTitleTxtView);
        Button confirmToAdd = findViewById(R.id.confirmAdd);

        if (check == 2){
            confirmToAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleName = titleTB.getText().toString().trim();
                    content = contentTB.getText().toString().trim();
                    new CardFirebaseHelper().addCommunityChestCards(titleName, content);
                    finish();
                }
            });
        }
        else if (check == 3) {
            confirmToAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleName = titleTB.getText().toString().trim();
                    content = contentTB.getText().toString().trim();
                    new CardFirebaseHelper().addChanceCards(titleName, content);
                    finish();
                }
            });
        }

        else{
            title.setText("Add new Disruption");
            confirmToAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleName = titleTB.getText().toString().trim();
                    content = contentTB.getText().toString().trim();
                    new DisruptionFirebaseHelper().addDisruption(titleName, content);
                    finish();
                }
            });
        }
    }
}

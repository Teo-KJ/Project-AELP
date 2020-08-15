package com.example.aelpuser.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.aelpuser.Entity.User;
import com.example.aelpuser.DAOHelper.UserFirebaseHelper;
import com.example.aelpuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference dbAvatars;
    String selected;
    EditText nickname;
    ArrayList<String> avatarList;
    ArrayAdapter<String> adapter;
    final double NEW_BALANCE = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read the list of avatars from the Firebase server, and display to the dropdown box
        dbAvatars = FirebaseDatabase.getInstance().getReference().child("avatar");
        Spinner spinner = findViewById(R.id.spinner1);

        avatarList = new ArrayList<>();
        /*Resources res = getResources();
        final String[] landmarks = res.getStringArray(R.array.landmarksOption);*/
        readAvatars();

        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, avatarList);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.landmarksOption, android.R.layout.simple_spinner_item);*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int k=0; k<avatarList.size(); k++){
                    if (k==i)
                        selected = avatarList.get(k);
                }
                /*Random random = new Random();
                selected = landmarks.get(random.nextInt(landmarks.size()));*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        nickname = findViewById(R.id.nickNameTextBox);
        Button next = findViewById(R.id.nextButton);

        // Create new session for new user into the game
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = newUser();
                Intent i = new Intent(MainActivity.this, MainScreen.class);

                // Pass the user class to the next activity
                i.putExtra("userClass", user);
                startActivity(i);
            }
        });
    }

    // Function to create new user into the database server
    public User newUser(){
        //String id = Integer.toString((int)(Math.random() * 10000));
        String usrNickname = nickname.getText().toString();
        User user = new UserFirebaseHelper().addUser(selected, usrNickname, NEW_BALANCE);
        Toast.makeText(this, "Welcome to the Game!", Toast.LENGTH_LONG).show();
        return user;
    }

    // For spinner class
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    // Function to read avatars from database server
    public void readAvatars(){
        dbAvatars.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                avatarList.clear();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()){
                    avatarList.add(keyNode.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
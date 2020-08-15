package com.example.aelpfacilitator.Control;

import android.os.Bundle;

import com.example.aelpfacilitator.DAOHelper.UserFirebaseHelper;
import com.example.aelpfacilitator.Entity.User;
import com.example.aelpfacilitator.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;

import android.content.Intent;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    /*ArrayList<String> timeList = new ArrayList<>();
    ArrayList<String> descList = new ArrayList<>();

    HashMap<String, String> users = new HashMap<>();
    HashMap <String, String> history = new HashMap<>();
    TreeMap<String, String> historySorted;

    public Integer i = 1;
    public TextView stopwatch;
    public Button start, stop;
    CustomListAdapter adapter;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0 ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Hour ;
    int numOfPlayer = 0;*/

    // ON CREATE ACTIVITY CLASS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Added Variables:
        final ListView historyList = findViewById(R.id.history_box);
        /*stopwatch = findViewById(R.id.stopwatch);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);*/
        final TextView numPlayers = findViewById(R.id.num_players);

        new UserFirebaseHelper().readUsers(new UserFirebaseHelper.DataStatus() {
            @Override
            public void DataLoaded(final ArrayList<User> users) {
                ArrayList<String> userNicknames = new ArrayList<>();
                for (User use: users){
                    userNicknames.add(use.getNickName());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, userNicknames);
                historyList.setAdapter(arrayAdapter);

                historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent j = new Intent(MainActivity.this, CheckProfile.class);
                        j.putExtra("userClass", users.get(i));
                        startActivity(j);
                    }
                });
                numPlayers.setText("There are " + users.size() + " players");
            }
        });

        // For the stopwatch
        /*handler = new Handler();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });*/

        Button gameActions = findViewById(R.id.button3);
        gameActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActions.class);
                startActivity(intent);
            }
        });
    }

    // STOPWATCH  -  This is for Stopwatch TextView function
    /*public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Hour = Seconds/360;
            Minutes = (Seconds / 60)%60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            stopwatch.setText("" +  String.format("%02d",Hour) +":"+  String.format("%02d",Minutes) + ":" + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
        }
    };*/

    /*// HISTORY LIST  -  Method for putting the input TreeMap map to timeList and descList.
    protected void convertMapToList (TreeMap<String, String> map) {

        if (timeList != null) timeList.clear();
        if (descList != null) descList.clear();
        for (String i : map.keySet()) {
            timeList.add(0,i);
            descList.add(0, map.get(i));
        }
        //Log.i("TAG time", timeList.toString());
    }

    // HISTORY LIST - Sort the HashMap by changing it into TreeMap
    protected void sortMap (HashMap<String, String> map) {
        historySorted = new TreeMap<>();
        historySorted.putAll(map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mainToPlayer(View view){
        Intent intent = new Intent (this, PlayerActivity.class);
        startActivity(intent);
    }

    username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                users = (HashMap) dataSnapshot.getValue();
                Log.i("TAG", "Value is: " + users.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("TAG", "Failed to read value.", error.toException());
            }
        });*/
}

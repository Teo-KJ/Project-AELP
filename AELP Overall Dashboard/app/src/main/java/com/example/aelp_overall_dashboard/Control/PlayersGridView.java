package com.example.aelp_overall_dashboard.Control;

import androidx.annotation.NonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aelp_overall_dashboard.Entity.User;
import com.example.myapplication.R;

import java.util.ArrayList;

public class PlayersGridView extends ArrayAdapter<User> {

    public PlayersGridView(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_players_gridview, null);
        }

        User user = getItem(position);
        TextView name = v.findViewById(R.id.nameTextView);
        TextView balance = v.findViewById(R.id.wealthTextView);
        TextView diceRollFig = v.findViewById(R.id.diceTextView);
        TextView jailStatus = v.findViewById(R.id.jailStatusTextView);

        name.setText(user.getNickName());
        balance.setText("M$" + user.getBalance() + "0");
        diceRollFig.setText("Dice: " + user.getLastDiceValue());

        if (user.isJailed()) jailStatus.setText("JAILED");

        return v;
    }
}

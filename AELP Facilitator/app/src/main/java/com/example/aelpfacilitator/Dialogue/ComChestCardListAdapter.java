package com.example.aelpfacilitator.Dialogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.aelpfacilitator.Entity.CommunityChest;
import com.example.aelpfacilitator.R;

import java.util.ArrayList;

public class ComChestCardListAdapter extends ArrayAdapter<CommunityChest> {
    private Context context;
    int resource;

    public ComChestCardListAdapter(Context cont, int res, ArrayList<CommunityChest> cards){
        super(cont, res, cards);
        context = cont;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getCardTitle();
        boolean isDrawn = getItem(position).isDrawn();
        String drawnStatus = "";
        if (isDrawn) drawnStatus = "Taken";
        else drawnStatus = "Available";

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.card_adapter_view_layout, parent, false);

        TextView nameText = convertView.findViewById(R.id.name);
        nameText.setText(name);
        TextView pStatus = convertView.findViewById(R.id.status);
        pStatus.setText(drawnStatus);

        return convertView;
    }
}

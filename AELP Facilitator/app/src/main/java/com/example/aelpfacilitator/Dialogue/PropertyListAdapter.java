package com.example.aelpfacilitator.Dialogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.aelpfacilitator.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.aelpfacilitator.Entity.Property;
import java.util.ArrayList;

public class PropertyListAdapter extends ArrayAdapter<Property> {
    private Context context;
    int resource;

    public PropertyListAdapter(Context cont, int res, ArrayList<Property> properties){
        super(cont, res, properties);
        context = cont;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getPropertyName();
        double price= getItem(position).getPrice();
        boolean isPurchased = getItem(position).isPurchased();
        String purchaseStatus = "";
        if (isPurchased) purchaseStatus = "Purchased";
        else purchaseStatus = "Available";

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.property_adapter_view_layout, parent, false);

        TextView nameText = convertView.findViewById(R.id.name);
        nameText.setText(name);
        TextView priceText = convertView.findViewById(R.id.price);
        priceText.setText(price + "0");
        TextView pStatus = convertView.findViewById(R.id.isPurchased);
        pStatus.setText(purchaseStatus);

        return convertView;
    }
}

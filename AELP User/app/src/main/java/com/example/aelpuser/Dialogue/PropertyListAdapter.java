package com.example.aelpuser.Dialogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.aelpuser.Entity.Property;
import com.example.aelpuser.R;
import java.util.ArrayList;

// Class to customise the list view, so that the more details of the property are displayed.
public class PropertyListAdapter extends ArrayAdapter<Property> {
    private Context context;

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

        // Display the information onto the customised list view
        TextView nameText = convertView.findViewById(R.id.nameTxtView);
        nameText.setText(name);
        TextView priceText = convertView.findViewById(R.id.priceTxtView);
        priceText.setText("M$" + price + "0");
        TextView pStatus = convertView.findViewById(R.id.isPurchasedTxtView);
        pStatus.setText(purchaseStatus);

        return convertView;
    }
}

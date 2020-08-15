package com.example.aelp_overall_dashboard.DAOHelper;

import androidx.annotation.NonNull;

import com.example.aelp_overall_dashboard.Entity.Property;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PropertyFirebaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference propertyReference;
    private ArrayList<Property> propertyList = new ArrayList<>();

    public PropertyFirebaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        propertyReference = mDatabase.getReference("property");
    }

    public interface DataStatus{
        void DataLoaded(ArrayList<Property> properties);
    }

    public void readProperties (final DataStatus dataStatus){
        propertyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                propertyList.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Property p = keyNode.getValue(Property.class);
                    propertyList.add(p);
                }
                dataStatus.DataLoaded(propertyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public Property addProperty (String propertyName, String colour, double price, double rent){
        String id = propertyReference.push().getKey();
        Property property = new Property(id, propertyName, colour, price, rent, false, "");
        propertyReference.child(id).setValue(property);
        return property;
    }

    public void updatePropertyDetails(String id, String attribute, String value){
        propertyReference.child(id).child(attribute).setValue(value);
    }

    public void updatePropertyPrice(String id, String attribute, double value){
        propertyReference.child(id).child(attribute).setValue(value);
    }

    public void updatePropertyOwner(String id, String value){
        propertyReference.child(id).child("ownerID").setValue(value);
    }

    public void updatePropertyPurchaseStatus(String id, boolean value){
        propertyReference.child(id).child("purchased").setValue(value);
    }
}
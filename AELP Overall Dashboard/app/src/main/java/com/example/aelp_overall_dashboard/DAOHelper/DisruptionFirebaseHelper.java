package com.example.aelp_overall_dashboard.DAOHelper;

import androidx.annotation.NonNull;

import com.example.aelp_overall_dashboard.Entity.Disruption;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisruptionFirebaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference disruptionReference;
    private ArrayList<Disruption> disruptionsList = new ArrayList<>();

    public DisruptionFirebaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        disruptionReference = mDatabase.getReference("disruption");
    }

    public interface DataStatus{
        void DataLoaded(ArrayList<Disruption> disruptionsList);
    }

    public void readDisruptions (final DataStatus dataStatus){
        disruptionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                disruptionsList.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Disruption p = keyNode.getValue(Disruption.class);
                    disruptionsList.add(p);
                }
                dataStatus.DataLoaded(disruptionsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public Disruption addDisruption (String title, String message){
        String id = disruptionReference.push().getKey();
        Disruption disruption = new Disruption(id, title, message, false, false);
        disruptionReference.child(id).setValue(disruption);
        return disruption;
    }

    public void updateDisruptionDetails(String id, String attribute, boolean value){
        disruptionReference.child(id).child(attribute).setValue(value);
    }
}

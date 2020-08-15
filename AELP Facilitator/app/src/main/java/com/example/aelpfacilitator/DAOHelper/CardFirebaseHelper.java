package com.example.aelpfacilitator.DAOHelper;

import androidx.annotation.NonNull;
import com.example.aelpfacilitator.Entity.Chance;
import com.example.aelpfacilitator.Entity.CommunityChest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CardFirebaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference ccCardReference, chanceCardReference;
    private ArrayList<CommunityChest> ccCardList = new ArrayList<>();
    private ArrayList<Chance> chanceCardList = new ArrayList<>();

    public CardFirebaseHelper(){

        mDatabase = FirebaseDatabase.getInstance();
        ccCardReference = mDatabase.getReference("community_chest_cards");
        chanceCardReference = mDatabase.getReference("chance_cards");
    }

    public interface DataStatus{
        void ComChestDataLoaded(ArrayList<CommunityChest> cardList);
        void ChanceDataLoaded(ArrayList<Chance> cardList);
    }

    // Add new cards and read cards from the database server
    public void readCommunityChestCards (final CardFirebaseHelper.DataStatus dataStatus){
        ccCardReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ccCardList.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    CommunityChest cc = keyNode.getValue(CommunityChest.class);
                    ccCardList.add(cc);
                }
                dataStatus.ComChestDataLoaded(ccCardList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public CommunityChest addCommunityChestCards (String cardTitle, String cardContent){
        String id = ccCardReference.push().getKey();
        CommunityChest communityChest = new CommunityChest(id, cardTitle, cardContent, false, false, "");
        ccCardReference.child(id).setValue(communityChest);
        return communityChest;
    }

    public void readChanceCards (final CardFirebaseHelper.DataStatus dataStatus){
        chanceCardReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chanceCardList.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Chance cc = keyNode.getValue(Chance.class);
                    chanceCardList.add(cc);
                }
                dataStatus.ChanceDataLoaded(chanceCardList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public Chance addChanceCards (String cardTitle, String cardContent){
        String id = chanceCardReference.push().getKey();
        Chance chance = new Chance(id, cardTitle, cardContent, false, false, "");
        chanceCardReference.child(id).setValue(chance);
        return chance;
    }

    // Update of cards and the attributes
    public void updateCommunityChestCardDrawn(String cardID, boolean value){
        ccCardReference.child(cardID).child("drawn").setValue(value);
    }

    public void updateCommunityChestCardOwner(String cardID, String value){
        ccCardReference.child(cardID).child("ownerID").setValue(value);
    }

    public void updateChanceCardDrawn(String cardID, boolean value){
        chanceCardReference.child(cardID).child("drawn").setValue(value);
    }

    public void updateChanceCardOwner(String cardID, String value){
        chanceCardReference.child(cardID).child("ownerID").setValue(value);
    }

    public void updateComChestDisplay(String cardID, boolean value){
        ccCardReference.child(cardID).child("updated").setValue(value);
    }

    public void updateChanceDisplay(String cardID, boolean value){
        chanceCardReference.child(cardID).child("updated").setValue(value);
    }
}
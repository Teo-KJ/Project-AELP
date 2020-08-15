package com.example.aelpuser.DAOHelper;

import androidx.annotation.NonNull;
import com.example.aelpuser.Entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class UserFirebaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference userReference, userHistoryReference;
    private ArrayList<User> userList = new ArrayList<>();

    public UserFirebaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        userReference = mDatabase.getReference("user");
    }

    public interface DataStatus{
        void DataLoaded(ArrayList<User> users);
    }

    // Read all users from the database server
    public void readUsers(final DataStatus dataStatus){
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    User u = keyNode.getValue(User.class);
                    userList.add(u);
                }
                dataStatus.DataLoaded(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    // Add new user
    public User addUser(String avatar, String nickname, double balance){
        String id = userReference.push().getKey();
        User user = new User(id, avatar, nickname, balance, 0, false);
        //User userB = new User(id, null, null, null);
        userReference.child(id).setValue(user);
        //userHistoryReference.child(id).setValue(userB);
        return user;
    }

    // Update user and attributes
    public void updateUser(String id, String attribute, int value){
        userReference.child(id).child(attribute).setValue(value);
    }

    public void updateUserJailStatus(String id, boolean value){
        userReference.child(id).child("jailed").setValue(value);
    }

    // Delete users
    public void deleteUser(String id){
        userReference.child(id).setValue(null);
    }

    public void deleteAllUsers(){
        userReference.removeValue();
    }
}
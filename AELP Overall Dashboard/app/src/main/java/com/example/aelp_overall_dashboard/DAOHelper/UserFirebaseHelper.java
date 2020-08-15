package com.example.aelp_overall_dashboard.DAOHelper;

import androidx.annotation.NonNull;
import com.example.aelp_overall_dashboard.Entity.User;
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
    private ArrayList<User> userHistList = new ArrayList<>();

    public UserFirebaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        userReference = mDatabase.getReference("user");
        userHistoryReference = mDatabase.getReference("user_history");
    }

    public interface DataStatus{
        void DataLoaded(ArrayList<User> users);
    }

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

    public void readUsersHistory(final DataStatus dataStatus){
        userHistoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userHistList.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    User u = keyNode.getValue(User.class);
                    userHistList.add(u);
                }
                dataStatus.DataLoaded(userHistList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public User addUser(String avatar, String nickname, double balance){
        String id = userReference.push().getKey();
        User user = new User(id, avatar, nickname, balance, 0, false);
        //User userB = new User(id, null, null, null);
        userReference.child(id).setValue(user);
        //userHistoryReference.child(id).setValue(userB);
        return user;
    }

    public void updateUser(String id, String attribute, int value){
        userReference.child(id).child(attribute).setValue(value);
    }

    public void updateUserJailStatus(String id, boolean value){
        userReference.child(id).child("jailed").setValue(value);
    }

    public void deleteUser(String id){
        userReference.child(id).setValue(null);
    }

    public void deleteAllUsers(){
        userReference.removeValue();
    }
}
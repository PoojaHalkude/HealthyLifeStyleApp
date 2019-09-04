package com.example.healthylifestyleapp;

import android.view.View;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataHelper {
    private FirebaseDatabase UserDatabase;
    private DatabaseReference userReference;
    private List<UserData> userList=new ArrayList<>();

    FirebaseDataHelper()
    {
        UserDatabase.getInstance();
        userReference= UserDatabase.getReference("user");
    }
    interface DataStatus
    {
    void dataIsLoaded(List<UserData> userDataList,List<String> key);
    void dataIsUpdated();
    void dataIsInserted();
    void dataIsDeleted();
    }
    public void ReadData(final DataStatus dataStatus)
    {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                List<String> key = new ArrayList<>();
                for(DataSnapshot keynode: dataSnapshot.getChildren())
                {
                    key.add(keynode.getKey());
                    UserData userData = (UserData) keynode.getValue(UserData.class);
                    userList.add(userData);
                }
                dataStatus.dataIsLoaded(userList,key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
   /* public void addUser(UserData userData, final DataStatus dataStatus) {
        String key = userReference.push().getKey();
        userReference.child(key).setValue(userList).addOnSuccessListener((OnSuccessListener)
       {
            dataStatus.dataIsInserted();
        });
    }*/
}

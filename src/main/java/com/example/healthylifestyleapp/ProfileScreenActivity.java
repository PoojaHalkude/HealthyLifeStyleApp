package com.example.healthylifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileScreenActivity extends AppCompatActivity {
   ImageView ImageViewProfilePic;
TextView TextViewUserName,TextViewMobile;
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";
    // Creating URI.
    Uri FilePathUri;
    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        ImageViewProfilePic.findViewById(R.id.ImageViewProfilePic);
        TextViewUserName.findViewById(R.id.TextViewUserName);
        TextViewMobile.findViewById(R.id.TextViewMobile);
        loadUserInfo();


    }

    private void loadUserInfo() {
    }
}

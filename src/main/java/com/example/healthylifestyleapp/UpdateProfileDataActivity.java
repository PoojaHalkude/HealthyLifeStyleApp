package com.example.healthylifestyleapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class UpdateProfileDataActivity extends AppCompatActivity {

    // Creating button.
    Button ChooseButton, UploadButton,ButtonSkip;
    FirebaseAuth mAuth;

    // Creating EditText.
    EditText ImageName, EditTextEmail, EditTextMobileNo, EditTextName;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();
    // Creating ImageView.
    ImageView SelectImage,imageViewProfile;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";
    // Creating URI.
    Uri FilePathUri;
    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;
String name,url,phone,email;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 71;
    ProgressDialog progressDialog;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_data);
        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();
     imageViewProfile=findViewById(R.id.imageViewProfile);
        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        EditTextName = findViewById(R.id.EditTextName);
        EditTextEmail = findViewById(R.id.EditTextEmail);
        EditTextMobileNo = findViewById(R.id.EditTextMobileNo);
        //Assign ID'S to button.
        ChooseButton = (Button) findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button) findViewById(R.id.ButtonUploadImage);
        ButtonSkip=findViewById(R.id.ButtonSkip);
        // Assign ID's to EditText.
        ImageName = (EditText) findViewById(R.id.EditTextName);
        @SuppressLint("RestrictedApi")
        UserData userNew = new UserData("Pooja","p@gmail.com","9049651515");
        @SuppressLint("RestrictedApi")
        UserUploadInfo userUploadInfo = new UserUploadInfo(name,email,phone,url);
        mDatabaseReference = mDatabase.getReference().child("user");
        mDatabaseReference.setValue(userUploadInfo);

        // Assign ID'S to image view.
        SelectImage = (ImageView) findViewById(R.id.ShowImageView);
        /*mDatabaseReference = mDatabase.getReference().child("name");
        mDatabaseReference.setValue("Pooja Halkude");*/
        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(this);
       /* if (user != null)
        {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }*/

        // Adding click listener to Choose image button.
        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });
       /* UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("pooja", "User profile updated.");
                        }
                    }
                });*/
        // Adding click listener to Upload image button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                UploadImageFileToFirebaseStorage();

            }
        });
        ButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent1 = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(myIntent1);


            }
        });    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                GoogleSignInAccount account = null;
                    //fiebaseAuthWithGoogle(account);
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Image Selected");

            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    }

    /*private void fiebaseAuthWithGoogle(final GoogleSignInAccount account) {
        if(BuildConfig.DEBUG) Log.d("Tag","Fiebase auth with Google"+account.getDisplayName());
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth

    }
*/
    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child("images/" + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String Name = EditTextName.getText().toString().trim();
                            String email = EditTextEmail.getText().toString().trim();
                            String mobile = EditTextMobileNo.getText().toString().trim();
                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();
                            //byte[] bytes = new byte[0];

                          /*  Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            DisplayMetrics dm = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(dm);

                            imageViewProfile.setMinimumHeight(dm.heightPixels);
                            imageViewProfile.setMinimumWidth(dm.widthPixels);
                            imageViewProfile.setImageBitmap(bm);*/
                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            UserUploadInfo imageUploadInfo = new UserUploadInfo(Name, email, mobile, taskSnapshot.getClass().toString());

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);

                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(UpdateProfileDataActivity.this, "failed"+exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }

                    });
            Intent myIntent= new Intent(this, UserProfileActivity.class);
            startActivity(myIntent);

           mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TextView TextViewUserName = (TextView) findViewById(R.id.TextViewUserName1);
                    TextView  TextViewEmail1=findViewById(R.id.TextViewEmail1);

                    if (dataSnapshot.exists()){
                        HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                        for (String key : dataMap.keySet()){

                            Object data = dataMap.get(key);

                            try{
                                HashMap<String, Object> userData = (HashMap<String, Object>) data;

                                UserUploadInfo mUser = new UserUploadInfo((String) userData.get("name"), (String) userData.get("email"),(String) userData.get("mobile"),(String) userData.get("url") );
                                // addTextToView(mUser.getUserName() );
                                String v = dataSnapshot.getValue(String.class);
                                TextViewUserName.setText(v);
                                String newEmail=dataSnapshot.getValue(String.class);
                                TextViewEmail1.setText(newEmail);


                                // outputName.setText(name);


                            }catch (ClassCastException cce){

// If the object can’t be casted into HashMap, it means that it is of type String.

                                try{

                                    String mString = String.valueOf(dataMap.get(key));

                                   // addTextToView(mString);

                                }catch (ClassCastException cce2){

                                }
                            }

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });


        } else {

            Toast.makeText(UpdateProfileDataActivity.this, "Please Select Image or Add Your Details", Toast.LENGTH_LONG).show();

        }

    }


}



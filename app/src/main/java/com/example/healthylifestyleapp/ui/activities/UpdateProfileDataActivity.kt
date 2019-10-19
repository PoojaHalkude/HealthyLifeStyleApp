package com.example.healthylifestyleapp.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.UserData
import com.example.healthylifestyleapp.model.UserUploadInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_update_profile_data.*
import java.io.IOException

class UpdateProfileDataActivity : AppCompatActivity() {


    private val mDatabase = FirebaseDatabase.getInstance()
    private var mDatabaseReference = mDatabase.reference
    // Creating ImageView.

    // Folder path for Firebase Storage.
    internal var Storage_Path = "All_Image_Uploads/"

    // Root Database Name for Firebase Database.
    internal var Database_Path = "All_Image_Uploads_Database"
    // Creating URI.
    internal var FilePathUri: Uri? = null
    // Creating StorageReference and DatabaseReference object.
    internal lateinit var storageReference: StorageReference
    internal lateinit var databaseReference: DatabaseReference

    // Image request code for onActivityResult() .
    internal var Image_Request_Code = 71
    internal lateinit var progressDialog: ProgressDialog
    internal var user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_data)
        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().reference

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path)

        //Assign ID'S to button.
        // Assign ID's to EditText.
        @SuppressLint("RestrictedApi")
        val userNew =
            UserData("Pooja", "pooja@gmail.com", "9049651515")
        mDatabaseReference = mDatabase.reference.child("user")
        mDatabaseReference.setValue(user)

        // Assign ID'S to image view.
        mDatabaseReference = mDatabase.reference.child("name")
        mDatabaseReference.setValue("Pooja Halkude")
        // Assigning Id to ProgressDialog.
        progressDialog = ProgressDialog(this)
        if (user != null) {
            // Name, email address, and profile photo Url
            val name = user!!.displayName
            val email = user!!.email
            val photoUrl = user!!.photoUrl
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user!!.uid
        }

        // Adding click listener to Choose image button.
        ButtonChooseImage.setOnClickListener {
            // Creating intent.
            val intent = Intent()

            // Setting intent type as image to select image from phone storage.
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Please Select Image"),
                Image_Request_Code
            )
        }
        /* UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jar It")
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
        ButtonUploadImage.setOnClickListener {
            // Calling method to upload selected image on Firebase storage.
            UploadImageFileToFirebaseStorage()
        }
        ButtonSkip.setOnClickListener {
            val myIntent1 = Intent(applicationContext, UserProfileActivity::class.java)
            startActivity(myIntent1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == Image_Request_Code && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            FilePathUri = data.data

            try {

                // Getting selected image into Bitmap.
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, FilePathUri)

                // Setting up bitmap selected image into ImageView.
                ShowImageView.setImageBitmap(bitmap)

                // After selecting image change choose button above text.
                ButtonChooseImage.text = "Image Selected"

            } catch (e: IOException) {

                e.printStackTrace()
            }

        }

    }

    // Creating Method to get the selected image file Extension from File Path URI.
    fun GetFileExtension(uri: Uri): String? {

        val contentResolver = contentResolver

        val mimeTypeMap = MimeTypeMap.getSingleton()

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    fun UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...")

            // Showing progressDialog.
            progressDialog.show()

            // Creating second StorageReference.
            val storageReference2nd = storageReference.child(
                "images/" + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri!!)
            )

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    // Getting image name from EditText and store into string variable.
                    val Name = EditTextName.text.toString().trim { it <= ' ' }
                    val email = EditTextEmail.text.toString().trim { it <= ' ' }
                    val mobile = EditTextMobileNo.text.toString().trim { it <= ' ' }
                    // Hiding the progressDialog after done uploading.
                    progressDialog.dismiss()

                    // Showing toast message after done uploading.
                    Toast.makeText(
                        applicationContext,
                        "Image Uploaded Successfully ",
                        Toast.LENGTH_LONG
                    ).show()

                    val imageUploadInfo =
                        UserUploadInfo(
                            Name,
                            email,
                            mobile,
                            taskSnapshot.javaClass.toString()
                        )

                    // Getting image upload ID.
                    val ImageUploadId = databaseReference.push().key

                    // Adding image upload id s child element into databaseReference.
                    databaseReference.child(ImageUploadId!!).setValue(imageUploadInfo)
                }
                // If something goes wrong .
                .addOnFailureListener { exception ->
                    // Hiding the progressDialog.
                    progressDialog.dismiss()

                    // Showing exception erro message.
                    Toast.makeText(
                        this@UpdateProfileDataActivity,
                        "failed" + exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                // On progress change upload time.
                .addOnProgressListener {
                    // Setting progressDialog Title.
                    progressDialog.setTitle("Image is Uploading...")
                }
            val myIntent = Intent(this, UserProfileActivity::class.java)
            startActivity(myIntent)
        } else {

            Toast.makeText(
                this@UpdateProfileDataActivity,
                "Please Select Image or Add Your Details",
                Toast.LENGTH_LONG
            ).show()

        }

    }


}




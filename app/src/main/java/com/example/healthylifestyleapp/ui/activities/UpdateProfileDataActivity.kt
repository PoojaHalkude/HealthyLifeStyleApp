package com.example.healthylifestyleapp.ui.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.UserUploadInfo
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.example.healthylifestyleapp.utils.next
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update_profile_data.*
import org.jetbrains.anko.toast
import java.io.IOException

class UpdateProfileDataActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    // Creating URI.
    private var filePathUri: Uri? = null
    // Creating StorageReference and DatabaseReference object.
    private lateinit var storageReference: StorageReference

    // Image request code for onActivityResult() .
    private var code = 71
    private lateinit var progressDialog: ProgressDialog
    private var profile: UserUploadInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_data)
        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().reference

        fetchProfileDetails()

        // Assigning Id to ProgressDialog.
        progressDialog = ProgressDialog(this)


        // Adding click listener to Choose image button.
        ButtonChooseImage.setOnClickListener {
            // Creating intent.
            val intent = Intent()

            // Setting intent type as image to select image from phone storage.
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_image)),
                code
            )
        }

        // Adding click listener to Upload image button.
        ButtonUploadImage.setOnClickListener {
            // Calling method to upload selected image on Firebase storage.
            uploadImageFileToFirebaseStorage()
        }
    }

    private fun fetchProfileDetails() {
        val user = FirebaseAuth.getInstance().currentUser
        val name =
            if (user != null && !TextUtils.isEmpty(user.displayName)) user.displayName else ""
        val email = if (user != null && !TextUtils.isEmpty(user.email)) user.email else ""
        val phoneNumber =
            if (user != null && !TextUtils.isEmpty(user.phoneNumber)) user.phoneNumber else ""
        val photoUrl =
            if (user != null && !TextUtils.isEmpty(user.photoUrl.toString())) user.photoUrl.toString() else ""
        firebaseDatabase.getReference("users/${firebaseAuth.currentUser!!.uid}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    profile = p0.getValue(UserUploadInfo::class.java)
                    if (profile != null) {
                        if (!TextUtils.isEmpty(profile!!.userName)) {
                            EditTextName.setText(profile!!.userName)
                        } else {
                            EditTextName.setText(name)
                        }

                        if (!TextUtils.isEmpty(profile!!.myemail)) {
                            EditTextEmail.setText(profile!!.myemail)
                        } else {
                            EditTextEmail.setText(email)
                        }

                        if (!TextUtils.isEmpty(profile!!.mobile)) {
                            EditTextMobileNo.setText(profile!!.mobile)
                        } else {
                            EditTextMobileNo.setText(phoneNumber)
                        }

                        if (!TextUtils.isEmpty(photoUrl) && photoUrl != "null") {
                            Picasso.get().load(photoUrl).placeholder(R.drawable.ic_account_circle)
                                .into(showImageView)
                        } else {
                            Picasso.get().load(profile!!.imageURL)
                                .placeholder(R.drawable.ic_account_circle)
                                .into(showImageView)
                        }
                    } else {

                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == code && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            filePathUri = data.data

            try {

                // Getting selected image into Bitmap.
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePathUri)

                // Setting up bitmap selected image into ImageView.
                showImageView.setImageBitmap(bitmap)

                // After selecting image change choose button above text.
                ButtonChooseImage.text = getString(R.string.image_selected)

            } catch (e: IOException) {

                e.printStackTrace()
            }

        }

    }

    // Creating Method to get the selected image file Extension from File Path URI.
    private fun getFileExtension(uri: Uri): String? {

        val contentResolver = contentResolver

        val mimeTypeMap = MimeTypeMap.getSingleton()

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))

    }

    // Creating uploadImageFileToFirebaseStorage method to upload image on storage.
    private fun uploadImageFileToFirebaseStorage() {

        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }

        if (!isFormEmpty()) {
            toast(getString(R.string.fill_all_details))
            return
        }

        // Checking whether filePathUri Is empty or not.
        if (filePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle(getString(R.string.image_is_uploading))

            // Showing progressDialog.
            progressDialog.show()

            // Creating second StorageReference.
            val storageReference2nd = storageReference.child(
                "images/" + System.currentTimeMillis() + "." + getFileExtension(filePathUri!!)
            )

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(filePathUri!!)
                .addOnSuccessListener { _ ->
                    storageReference2nd.downloadUrl.addOnCompleteListener { uriTask ->

                        // Getting image name from EditText and store into string variable.
                        val name = EditTextName.text.toString().trim { it <= ' ' }
                        val email = EditTextEmail.text.toString().trim { it <= ' ' }
                        val mobile = EditTextMobileNo.text.toString().trim { it <= ' ' }
                        // Hiding the progressDialog after done uploading.
                        progressDialog.dismiss()

                        // Showing toast message after done uploading.
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.image_upload_successful),
                            Toast.LENGTH_LONG
                        ).show()

                        val uploadInfo =
                            UserUploadInfo(
                                userName = name,
                                myemail = email,
                                mobile = mobile,
                                imageURL = uriTask.result.toString()
                            )

                        // Adding image upload id s child element into databaseReference.
                        firebaseDatabase.getReference("users/${firebaseAuth.currentUser!!.uid}")
                            .setValue(uploadInfo).addOnCompleteListener {
                                toast(getString(R.string.profile_update_success))
                                next()
                            }
                            .addOnFailureListener {
                                toast(getString(R.string.update_profile_failed))
                            }
                    }

                }
                // If something goes wrong .
                .addOnFailureListener {
                    // Hiding the progressDialog.
                    progressDialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.failed_to_upload_image),
                        Toast.LENGTH_LONG
                    ).show()
                }

                // On progress change upload time.
                .addOnProgressListener {
                    // Setting progressDialog Title.
                    progressDialog.setTitle(getString(R.string.image_is_uploading))
                }
        } else {
            // Adding image upload id s child element into databaseReference.
            profile!!.userName = EditTextName.text.toString().trim { it <= ' ' }
            profile!!.myemail = EditTextEmail.text.toString().trim { it <= ' ' }
            profile!!.mobile = EditTextMobileNo.text.toString().trim { it <= ' ' }

            firebaseDatabase.getReference("users/${firebaseAuth.currentUser!!.uid}")
                .setValue(profile).addOnCompleteListener {
                    toast(getString(R.string.profile_update_success))
                    next()
                }
                .addOnFailureListener {
                    toast(getString(R.string.update_profile_failed))
                }
        }
    }

    private fun isFormEmpty(): Boolean {
        return !TextUtils.isEmpty(EditTextName.text.toString())
                && !TextUtils.isEmpty(EditTextEmail.text.toString())
                && !TextUtils.isEmpty(EditTextMobileNo.text.toString())
    }


}




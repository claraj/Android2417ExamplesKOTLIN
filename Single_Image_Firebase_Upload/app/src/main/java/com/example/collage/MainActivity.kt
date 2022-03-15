package com.example.collage

import android.content.Intent
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var mainView: View
    private lateinit var imageButton1: ImageButton
    private lateinit var uploadImageFab: FloatingActionButton
    private lateinit var uploadProgressBar: ProgressBar


    private var newPhotoPath: String? = null
    private var visibleImagePath: String? = null
    private var imageFilename: String? = null
    private var photoUri: Uri? = null

    private lateinit var storage: FirebaseStorage

    private val NEW_PHOTO_PATH_KEY = "new photo path key"
    private val VISIBLE_IMAGE_PATH_KEY = "visible image path key"

    private val cameraActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> handleImage(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storage = Firebase.storage

        Log.d(TAG, "onCreate $newPhotoPath")

        newPhotoPath = savedInstanceState?.getString(NEW_PHOTO_PATH_KEY)
        visibleImagePath = savedInstanceState?.getString(VISIBLE_IMAGE_PATH_KEY)

        mainView = findViewById(R.id.content)
        imageButton1 = findViewById(R.id.imageButton1)
        uploadImageFab = findViewById(R.id.upload_image)
        uploadProgressBar = findViewById(R.id.upload_progress_bar)

        imageButton1.setOnClickListener {
            takePicture()
        }

        uploadImageFab.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        if (photoUri != null && imageFilename != null) {

            uploadProgressBar.visibility = View.VISIBLE

            val imageStorageRootRef = storage.reference
            val imagesRef = imageStorageRootRef.child("images")
            val imageFileRef = imagesRef.child(imageFilename!!)

            imageFileRef.putFile(photoUri!!)
                .addOnCompleteListener {
                    Snackbar.make(this.mainView, "Image uploaded!", Snackbar.LENGTH_LONG).show()
                    uploadProgressBar.visibility = View.GONE

                }
                .addOnFailureListener {
                    Snackbar.make(this.mainView, "Error uploading image", Snackbar.LENGTH_LONG).show()
                    uploadProgressBar.visibility = View.GONE

                }
        } else {
            Snackbar.make(this.mainView, "Take a picture first", Snackbar.LENGTH_LONG).show()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NEW_PHOTO_PATH_KEY, newPhotoPath)
        outState.putString(VISIBLE_IMAGE_PATH_KEY, visibleImagePath)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.d(TAG, "on window focus changed, visible image is $visibleImagePath")
        if (hasFocus) {
            visibleImagePath?.let { loadImage(imageButton1, it) }
        }
    }

    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val (photoFile, photoFilePath) = createImageFile()
        if (photoFile != null){
            newPhotoPath = photoFilePath
            photoUri = FileProvider.getUriForFile(
                this,
                "com.example.collage.fileprovider",
                photoFile
            )
            Log.d(TAG, "$photoUri\n$photoFilePath")  // what's the difference?
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            cameraActivityLauncher.launch(takePictureIntent)
        }
    }


    private fun handleImage(result: ActivityResult) {
        when (result.resultCode) {
            RESULT_OK -> {
                Log.d(TAG, "Result ok, image at $newPhotoPath")
                visibleImagePath = newPhotoPath
            }
            RESULT_CANCELED -> {
                Log.d(TAG, "Result cancelled, no picture taken")
            }
        }
    }

    private fun createImageFile(): Pair<File?, String?> {
        return try {
            val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            imageFilename = "COLLAGE_$dateTime"
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(imageFilename, ".jpg", storageDir)
            file to file.absolutePath
        } catch (ex: IOException) {
            null to null
        }
    }


    private fun loadImage(imageButton: ImageButton, photoFilePath: String) {
        Picasso.get()
            .load(File(photoFilePath))
            .error(android.R.drawable.stat_notify_error)
            .fit()
            .centerCrop()
            .into(imageButton, object: Callback {
                override fun onSuccess() {
                    Log.d(TAG, "successfully loaded $photoFilePath")
                }

                override fun onError(e: Exception?) {
                    Log.e(TAG, "error loading $photoFilePath", e)
                }
            })
    }

}



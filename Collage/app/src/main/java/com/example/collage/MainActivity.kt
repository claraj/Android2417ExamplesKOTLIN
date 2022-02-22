package com.example.collage


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.suspendCoroutine

const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var mainView: View
    private lateinit var saveButton: FloatingActionButton
    private lateinit var imageButtons: List<ImageButton>
    private var photoPaths: ArrayList<String?> = arrayListOf(null, null, null, null)

    private var whichImageIndex: Int? = null

    private var newPhotoPath: String? = null

    private val PHOTO_LIST_ARRAY_KEY = "path list key"
    private val IMAGE_INDEX_KEY = "image index key"
    private val NEW_PHOTO_PATH_KEY = "new photo path key"


    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleImage(result)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        whichImageIndex = savedInstanceState?.getInt(IMAGE_INDEX_KEY) ?: -1
        newPhotoPath = savedInstanceState?.getString(NEW_PHOTO_PATH_KEY)
        photoPaths = savedInstanceState?.getStringArrayList(PHOTO_LIST_ARRAY_KEY) ?: arrayListOf(
            null,
            null,
            null,
            null
        )

        mainView = findViewById(R.id.content)

        saveButton = findViewById(R.id.saveFloatingActionButton)

        saveButton.setOnClickListener {
            saveCollageToGallery()
        }

        imageButtons = listOf(
            findViewById(R.id.imageButton1),
            findViewById(R.id.imageButton2),
            findViewById(R.id.imageButton3),
            findViewById(R.id.imageButton4)
        )

        for (imageButton: ImageButton in imageButtons) {
            imageButton.setOnClickListener { ib ->
                takePictureFor(ib as ImageButton)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(PHOTO_LIST_ARRAY_KEY, photoPaths)
        outState.putString(NEW_PHOTO_PATH_KEY, newPhotoPath)
        whichImageIndex?.let { outState.putInt(IMAGE_INDEX_KEY, it) }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            imageButtons.zip(photoPaths) { imageButton, photoPath ->
                photoPath?.let {  // if path is not null, load the image
                    loadImage(imageButton, photoPath)
                }}
        }
    }



    private fun takePictureFor(imageButton: ImageButton) {

        val index = imageButtons.indexOf(imageButton)
        whichImageIndex = index

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val (photoFile, photoPath) = createImageFile()
        if (photoFile != null) {
            newPhotoPath = photoPath
            val photoUri =
                FileProvider.getUriForFile(this, "com.example.collage.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            cameraActivityLauncher.launch(takePictureIntent)
        }
    }


    private fun handleImage(result: ActivityResult) {

        when (result.resultCode) {
            RESULT_OK -> {
                Log.d(TAG, "Result ok, image at $newPhotoPath")
                // overwrite the index with the current file in preparation for loading new image
                whichImageIndex?.let { photoPaths[it] = newPhotoPath }
            }
            RESULT_CANCELED -> {
                Log.d(TAG, "Result cancelled, no picture taken")
            }
        }
    }


    private fun createImageFile(): Pair<File?, String?> {
        return try {
            val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFilename = "COLLAGE_$dateTime"
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


    private fun saveCollageToGallery() {
        // TODO use a coroutine so not on main thread. Include lifecycle dependency in build.gradle
        if (photoPaths.all { it != null }) {
            val bitmap =
                Bitmap.createBitmap(mainView.width, mainView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            mainView.draw(canvas)   // draw the bitmap to a canvas

            val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val filename = "COMPLETE_COLLAGE_$dateTime"
            val fileOutputStream: OutputStream?

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {  // Q  AKA Android 10 or newer

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageURI: Uri? = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                fileOutputStream = imageURI?.let { contentResolver.openOutputStream(imageURI) }

                fileOutputStream?.use {  // closes the stream if the operation works or not
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    Snackbar.make(mainView, getString(R.string.saved), Snackbar.LENGTH_SHORT)
                        .show()
                }

            } else {
                Snackbar.make(
                    mainView,
                    getString(R.string.feature_unsupported),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        } else {
            Snackbar.make(
                mainView,
                getString(R.string.take_all_pictures_before_save),
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }
}



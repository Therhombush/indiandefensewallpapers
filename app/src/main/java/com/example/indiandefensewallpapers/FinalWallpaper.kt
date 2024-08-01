package com.example.indiandefensewallpapers

import android.Manifest
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.indiandefensewallpapers.databinding.ActivityFinalWallpaperBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*

class FinalWallpaper : AppCompatActivity() {
    private lateinit var binding: ActivityFinalWallpaperBinding
    private val PERMISSION_REQUEST_CODE = 1001
    private var imageUri: Uri? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the image URI or URL from the intent
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUrl = intent.getStringExtra("link")

        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString)
            displayImageFromUri(imageUri!!)
        } else if (imageUrl != null) {
            this.imageUrl = imageUrl
            displayImageFromUrl(imageUrl)
        } else {
            Toast.makeText(this, "No image source provided", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.buttonsetwallpaper.setOnClickListener {
            lifecycleScope.launch {
                val bitmap = async(Dispatchers.IO) {
                    imageUri?.let { uri -> uri.toBitmap() } ?: imageUrl?.let { URL(it).toBitmap() }
                }.await()
                if (bitmap != null) {
                    val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@FinalWallpaper, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@FinalWallpaper, "Failed to Set Wallpaper", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttondownload.setOnClickListener {
            checkPermissionsAndSaveImage()
        }
    }

    private fun displayImageFromUri(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.finalwallpaper)
    }

    private fun displayImageFromUrl(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.finalwallpaper)
    }

    private fun checkPermissionsAndSaveImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImage()
        } else {
            // Request storage permissions for Android 9 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                saveImage()
            }
        }
    }

    private fun saveImage() {
        lifecycleScope.launch {
            val bitmap = async(Dispatchers.IO) {
                imageUri?.let { uri -> uri.toBitmap() } ?: imageUrl?.let { URL(it).toBitmap() }
            }.await()

            if (bitmap != null) {
                saveBitmapToGallery(bitmap)
            } else {
                Toast.makeText(this@FinalWallpaper, "Failed to Download Image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Uri.toBitmap(): Bitmap? {
        return try {
            contentResolver.openInputStream(this)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val random = Random()
        val random1 = random.nextInt(520985)
        val random2 = random.nextInt(952663)
        val name = "IDW-${random1 + random2}"

        try {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "IndianDefenseWallpapers")
            }

            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (imageUri == null) {
                throw IOException("Failed to create new MediaStore record.")
            }

            resolver.openOutputStream(imageUri).use { outputStream ->
                if (outputStream == null) {
                    throw IOException("Failed to get output stream.")
                }
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                    throw IOException("Failed to save bitmap.")
                }
            }

            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Image Not Saved: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted, save the image
            saveImage()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}


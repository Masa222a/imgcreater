package com.example.imgcreater.viewmodel

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ResultViewModel: ViewModel() {
    val imageData = MutableLiveData<Bitmap>()

    fun saveImageToStrage(
        bitmap: Bitmap,
        contentResolver: ContentResolver,
        fileName: String = "screenshot.jpg",
        mimeType: String = "image/jpeg",
        directory: String = Environment.DIRECTORY_PICTURES,
        mediaContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ) {
        val imageOurStream: OutputStream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(MediaStore.Images.Media.RELATIVE_PATH, directory)
            }

            contentResolver.run {
                val uri = this.insert(mediaContentUri, values) ?: return
                imageOurStream = openOutputStream(uri) ?: return
            }
        } else {
            val imagePath = Environment.getExternalStoragePublicDirectory(directory).absolutePath
            val image = File(imagePath, fileName)
            imageOurStream = FileOutputStream(image)
        }

        imageOurStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
    }
}

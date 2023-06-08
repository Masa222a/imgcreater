package com.example.imgcreater.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.imgcreater.model.ImageDatabase
import com.example.imgcreater.model.ImageEntity
import com.example.imgcreater.repository.ImageRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    val allImages: LiveData<MutableList<ImageEntity>>
    val repository: ImageRepository

    init {
        val dao = ImageDatabase.getDatabase(application).imageDAO()
        repository = ImageRepository(dao)
        allImages = repository.allImages
    }
}

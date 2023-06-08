package com.example.imgcreater.repository

import androidx.lifecycle.LiveData
import com.example.imgcreater.model.ImageDao
import com.example.imgcreater.model.ImageEntity

class ImageRepository(private val imageDao: ImageDao) {

    val allImages: LiveData<MutableList<ImageEntity>> = imageDao.getAllImages()

    suspend fun insertImages(imageModel: ImageEntity) =
        imageDao.insertImage(imageModel)

    suspend fun deleteImages(imageModel: ImageEntity) =
        imageDao.deleteImage(imageModel)
}
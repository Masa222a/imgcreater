package com.example.imgcreater.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {
    @Insert
    fun insertImage(imageModel: ImageEntity)

    @Delete
    fun deleteImage(imageModel: ImageEntity)

    @Query("SELECT * FROM image_data_table")
    fun getAllImages(): LiveData<MutableList<ImageEntity>>
}
package com.example.imgcreater.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "image_data_table")
@Parcelize
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "image_url")
    val ImageUrl: String,
    @ColumnInfo(name = "image_word")
    val word: String
): Parcelable
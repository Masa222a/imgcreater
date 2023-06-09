package com.example.imgcreater.viewmodel

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.*
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.exception.OpenAIAPIException
import com.aallam.openai.api.exception.OpenAIException
import com.aallam.openai.api.exception.OpenAIHttpException
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.client.OpenAI
import com.example.imgcreater.BuildConfig
import com.example.imgcreater.model.ImageDatabase
import com.example.imgcreater.model.ImageEntity
import com.example.imgcreater.repository.ImageRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime


class MainViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private val apiKey = BuildConfig.API_KEY
    private val openAI = OpenAI(apiKey)

    private val repository: ImageRepository

    init {
        val dao = ImageDatabase.getDatabase(application).imageDAO()
        repository = ImageRepository(dao)
    }

    var imageUrl = MutableLiveData<String?>()
    var uri = MutableLiveData<Uri>()

    @OptIn(BetaOpenAI::class)
    fun getData(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val image =
                    openAI.imageURL(
                        creation = ImageCreation(
                            prompt = word,
                            n = 1,
                            size = ImageSize.is256x256
                        )
                    )
                Timber.d("$image")
                imageUrl.postValue(image[0].url)
            } catch (e: OpenAIException) {
                imageUrl.postValue(null)
                Timber.d(e)
            } catch (e: OpenAIHttpException) {
                imageUrl.postValue(null)
                Timber.d(e)
            } catch (e: OpenAIAPIException) {
                imageUrl.postValue(null)
                Timber.d(e)
            } catch (e: Exception) {
                imageUrl.postValue(null)
                Timber.d(e)
            }
        }
    }

    fun insertImage(image: ImageEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertImages(image)
        }
    }

    fun saveToStorage(context: Context, imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap: Bitmap = Picasso.get().load(imageUrl).get()
            val directory = ContextWrapper(context).getDir(
                "image",
                Context.MODE_PRIVATE
            )
            val localDateTime = LocalDateTime.now()
            val file = File(directory, "$localDateTime")
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                Timber.d("$bitmap")
                val parseUri = Uri.fromFile(file)
                uri.postValue(parseUri)
            }
        }
    }
}

package com.example.imgcreater.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.client.OpenAI
import com.example.imgcreater.BuildConfig
import com.example.imgcreater.model.ImageDatabase
import com.example.imgcreater.model.ImageEntity
import com.example.imgcreater.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private val apiKey = BuildConfig.API_KEY
    private val openAI = OpenAI(apiKey)

    val repository: ImageRepository

    init {
        val dao = ImageDatabase.getDatabase(application).imageDAO()
        repository = ImageRepository(dao)
    }

    var imageUrl = MutableLiveData<String>()

    @OptIn(BetaOpenAI::class)
    fun getData(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val image =
                openAI.imageURL(
                    creation = ImageCreation(
                        prompt = word,
                        n = 1,
                        size = ImageSize.is256x256
                    )
                )
            Log.d("viewmodel", "$image")
            imageUrl.postValue(image[0].url)
        }
    }

    fun insertImage(image: ImageEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertImages(image)
        }
    }
}

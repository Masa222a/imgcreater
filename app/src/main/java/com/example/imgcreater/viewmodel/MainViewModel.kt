package com.example.imgcreater.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val openAI = OpenAI("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")

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
}
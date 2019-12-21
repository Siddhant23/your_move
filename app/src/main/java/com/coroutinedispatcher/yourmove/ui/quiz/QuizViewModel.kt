package com.coroutinedispatcher.yourmove.ui.quiz

import androidx.lifecycle.*
import com.coroutinedispatcher.yourmove.api.YuGiOhApi
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.Card
import com.coroutinedispatcher.yourmove.utils.JUST_IMAGE_URL
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class QuizViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yuGiOhApi: YuGiOhApi,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _imageGeneratedUrl = MutableLiveData<String>()
    val imageGeneratedUrl: LiveData<String> = _imageGeneratedUrl

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): QuizViewModel
    }

    init {
        launchRandomImageApiCall()
    }

    private fun launchRandomImageApiCall() {
        viewModelScope.launch(appCoroutineDispatchers.ioDispatcher) {
            val response = yuGiOhApi.getRandomCard()
            if (response.isSuccessful) {
                generateImage(response.body())
            } else {
                //todo implement the error
            }
        }
    }

    private fun generateImage(body: List<Card>?) {
        body?.let {
            val randomCardId = it[0].id
            val newUrl = "$JUST_IMAGE_URL$randomCardId.jpg"
            _imageGeneratedUrl.postValue(newUrl)
        }
    }
}

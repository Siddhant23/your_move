package com.coroutinedispatcher.yourmove.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinedispatcher.yourmove.api.YuGiOhApi
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.Card
import com.coroutinedispatcher.yourmove.utils.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yuGiOhApi: YuGiOhApi,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var cardName: String = ""
    private var userCorrectAnswer: Int = 0
    private var userWrongAnswer: Int = 0
    private var userSkippedAnswer: Int = 0

    private val cardRequestExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            savedStateHandle.set(BUTTON_SAVED_STATE, BUTTON_STATE_ERROR)
        }

    val imageGeneratedUrl: LiveData<String> = savedStateHandle.getLiveData(QUIZ_IMAGE_STATE)
    val buttonState: LiveData<String> = savedStateHandle.getLiveData(BUTTON_SAVED_STATE)
    val userCorrectAnswerLiveData: LiveData<Int> =
        savedStateHandle.getLiveData(USER_CORRECT_ANSWER_STATE)
    val userWrongAnswerLiveData: LiveData<Int> =
        savedStateHandle.getLiveData(USER_WRONG_ANSWER_STATE)

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): QuizViewModel
    }

    init {
        savedStateHandle.set(USER_CORRECT_ANSWER_STATE, userCorrectAnswer)
        savedStateHandle.set(USER_WRONG_ANSWER_STATE, userWrongAnswer)
        savedStateHandle.set(USER_SKIPED_ANSWER_STATE, userSkippedAnswer)
        launchRandomImageApiCall()
    }

    private fun launchRandomImageApiCall() {
        viewModelScope.launch(appCoroutineDispatchers.mainDispatcher + cardRequestExceptionHandler) {
            savedStateHandle.set(BUTTON_SAVED_STATE, BUTTON_STATE_WAIT)
            withContext(appCoroutineDispatchers.ioDispatcher) {
                val response = yuGiOhApi.getRandomCard()
                if (response.isSuccessful) {
                    generateImage(response.body())
                } else {
                    withContext(appCoroutineDispatchers.mainDispatcher) {
                        savedStateHandle.set(BUTTON_SAVED_STATE, BUTTON_STATE_ERROR)
                    }
                }
            }
        }
    }

    private suspend fun generateImage(body: List<Card>?) {
        body?.let {
            val randomCardId = it[0].id
            val newUrl = "$JUST_IMAGE_URL$randomCardId.jpg"
            cardName = it[0].name
            withContext(appCoroutineDispatchers.mainDispatcher) {
                savedStateHandle.set(QUIZ_IMAGE_STATE, newUrl)
            }
        }
    }

    fun makeErrorButton() {
        savedStateHandle.set(BUTTON_SAVED_STATE, BUTTON_STATE_ERROR)
    }

    fun makeSuccessButton() {
        savedStateHandle.set(BUTTON_SAVED_STATE, BUTTON_STATE_SUCESS)
    }

    fun checkAnswerAndRelaunchCall(answer: String) {
        if (answer.equals(cardName, true)) {
            savedStateHandle.set(USER_CORRECT_ANSWER_STATE, ++userCorrectAnswer)
        } else {
            savedStateHandle.set(USER_WRONG_ANSWER_STATE, ++userWrongAnswer)
        }
        launchRandomImageApiCall()
    }
}

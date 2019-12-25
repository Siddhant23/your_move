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
import com.stavro_xhardha.rocket.Rocket
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.Days

class QuizViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yuGiOhApi: YuGiOhApi,
    private val rocket: Rocket,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var cardName: String = ""
    private var userCorrectAnswer: Int = 0
    private var userSkippedAnswer: Int = 0
    private var userWrongAnswer: Int = 0

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
    val userTotalScoreLiveData: LiveData<Int> = savedStateHandle.getLiveData(USER_TOTAL_SCORE_STATE)
    val dialogState: LiveData<Int> = savedStateHandle.getLiveData(DIALOG_VISIBILITY_STATE)


    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): QuizViewModel
    }

    init {
        savedStateHandle.set(USER_CORRECT_ANSWER_STATE, userCorrectAnswer)
        savedStateHandle.set(USER_SKIPED_ANSWER_STATE, userSkippedAnswer)
        val userTotalPoints = rocket.readInt(USER_TOTAL_SCORE_KEY)
        savedStateHandle.set(USER_TOTAL_SCORE_STATE, userTotalPoints)
        checkIfHoursHavePassed()
    }

    private fun checkIfHoursHavePassed() {
        val savedTime = rocket.readLong(USER_QUIZ_PREVENTION_TIME_KEY)
        val savedTimeInDateTime = DateTime(savedTime)
        val currentTime = DateTime()
        userWrongAnswer = rocket.readInt(USER_WRONG_ANSWER_TOTAL)
        if (Days.daysBetween(
                savedTimeInDateTime,
                currentTime
            ) <= Days.ONE || userWrongAnswer == WRONG_ANSWER_LIMIT
        ) {
            savedStateHandle.set(USER_WRONG_ANSWER_STATE, WRONG_ANSWER_LIMIT)
            savedStateHandle.set(DIALOG_VISIBILITY_STATE, DIALOG_STATE_VISIBILE)
        } else {
            savedStateHandle.set(USER_WRONG_ANSWER_STATE, userWrongAnswer)
            savedStateHandle.set(DIALOG_VISIBILITY_STATE, DIALOG_STATE_GONE)
            launchRandomImageApiCall()
        }
        if (Days.daysBetween(savedTimeInDateTime, currentTime) > Days.ONE) {
            rocket.writeInt(USER_WRONG_ANSWER_TOTAL, 0)
            savedStateHandle.set(DIALOG_VISIBILITY_STATE, DIALOG_STATE_GONE)
            savedStateHandle.set(USER_WRONG_ANSWER_STATE, 0)
        }
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
            userCorrectAnswer++
            savedStateHandle.set(USER_CORRECT_ANSWER_STATE, userCorrectAnswer)
            writeUserTotalScore()
        } else {
            savedStateHandle.set(USER_WRONG_ANSWER_STATE, ++userWrongAnswer)
            rocket.writeInt(USER_WRONG_ANSWER_TOTAL, userWrongAnswer)
        }

        if (userWrongAnswer < WRONG_ANSWER_LIMIT) {
            launchRandomImageApiCall()
        } else {
            savedStateHandle.set(BUTTON_SAVED_STATE, BUTTON_ALL_DONE_FOR_TODAY_STATE)
        }
    }

    private fun writeUserTotalScore() {
        val userCurrentTotalScore = rocket.readInt(USER_TOTAL_SCORE_KEY)
        rocket.writeInt(USER_TOTAL_SCORE_KEY, userCurrentTotalScore + userCorrectAnswer)
        val newUserTotalScore = rocket.readInt(USER_TOTAL_SCORE_KEY)
        savedStateHandle.set(USER_TOTAL_SCORE_STATE, newUserTotalScore)
    }

    fun forceUserToComeBackTomorrow() {
        val currentTime = DateTime().millis
        rocket.writeLong(USER_QUIZ_PREVENTION_TIME_KEY, currentTime)
        savedStateHandle.set(DIALOG_VISIBILITY_STATE, DIALOG_STATE_VISIBILE)
    }

    fun skipAnswer() {

    }
}

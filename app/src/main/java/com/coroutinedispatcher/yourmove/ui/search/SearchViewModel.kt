package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.db.YuGiOhDatabase
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.SEARCH_FRAGMENT_LOADING_STATE
import com.coroutinedispatcher.yourmove.utils.YUGIOH_CARDS_STATE
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class SearchViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val moshi: Moshi,
    private val db: YuGiOhDatabase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableListOfCards: MutableList<YuGiOhCard> = mutableListOf()
    val cardsLiveData: LiveData<List<YuGiOhCard>> = savedStateHandle.getLiveData(YUGIOH_CARDS_STATE)
    val loadingLiveData: LiveData<Int> = savedStateHandle.getLiveData(
        SEARCH_FRAGMENT_LOADING_STATE
    )

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }

    init {
        runBlocking {
            val data = db.dao().selectAll()
            data.forEach {
                Timber.d(it.name)
            }
        }
    }
}

package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.SEARCH_FRAGMENT_LOADING_STATE
import com.coroutinedispatcher.yourmove.utils.YUGIOH_CARDS_STATE
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.withContext


class SearchViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yugiohdao: YuGiOhDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableListOfCards: MutableList<YuGiOhCard> = mutableListOf()
    val cardsLiveData: LiveData<List<YuGiOhCard>> = savedStateHandle.getLiveData(YUGIOH_CARDS_STATE)
    val loadingLiveData: LiveData<Int> = savedStateHandle.getLiveData(
        SEARCH_FRAGMENT_LOADING_STATE
    )

    private val listConfig = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

    val yuGiOhCards: LiveData<PagedList<YuGiOhCard>> =
        liveData(appCoroutineDispatchers.ioDispatchers) {
            val data = yugiohdao.selectAll()
            withContext(appCoroutineDispatchers.mainDispatcher) {
                val sth = LivePagedListBuilder(data, listConfig).build()
                emit(sth.value!!)
            }
        }

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }
}

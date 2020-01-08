package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SimpleSQLiteQuery
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject


class HomeViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yugiohdao: YuGiOhDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var cards: LiveData<PagedList<YuGiOhCard>>

    private val listConfig = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

    private var finalSelectionQuery = ""

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): HomeViewModel
    }

    init {
        cards = LivePagedListBuilder(
            yugiohdao.selectAllMeetingCondition(
                SimpleSQLiteQuery("SELECT * FROM yugioh_cards $finalSelectionQuery ORDER BY name")
            ),
            listConfig
        ).build()
    }

    fun performSearch(query: String, lifecycleOwner: LifecycleOwner) {
        cards.removeObservers(lifecycleOwner)
        cards = LivePagedListBuilder(
            yugiohdao.selectAllMeetingCondition(
                SimpleSQLiteQuery(query)
            ),
            listConfig
        ).build()
    }
}

package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.SearchObject
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber


class HomeViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yugiohdao: YuGiOhDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var cards: LiveData<PagedList<YuGiOhCard>>

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): HomeViewModel
    }

    init {
        val listConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        val dataSourceFactory = yugiohdao.selectAll()
        cards = LivePagedListBuilder(dataSourceFactory, listConfig).build()
    }

    fun performSearch(searchObject: SearchObject) {

    }
}

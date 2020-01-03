package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch


class SearchViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yugiohdao: YuGiOhDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val listConfig = PagedList.Config.Builder()
        .setMaxSize(100000)
        .setPageSize(20)
        .setEnablePlaceholders(true)
        .build()

    var yuGiOhCards: MutableLiveData<List<YuGiOhCard>> = MutableLiveData()

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }

    init {
        viewModelScope.launch {
            val data = yugiohdao.selectAllTest()
            yuGiOhCards.postValue(data)
        }
    }
}

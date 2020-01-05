package com.coroutinedispatcher.yourmove.ui.advanced_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.withContext

class AdvancedSearchViewModel @AssistedInject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val yuGiOhDao: YuGiOhDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): AdvancedSearchViewModel
    }

    val levelData: LiveData<List<String>> = liveData {
        emit(listOf("Level", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
    }

    val typesLiveData: LiveData<List<String>> = liveData(appCoroutineDispatchers.ioDispatchers) {
        val allTypes = yuGiOhDao.selectAllTypes().toMutableList()
        allTypes.add(0, "Type")
        withContext(appCoroutineDispatchers.mainDispatcher) {
            emit(allTypes.toList())
        }
    }

    val racesLiveData: LiveData<List<String>> = liveData(appCoroutineDispatchers.ioDispatchers) {
        val allRaces = yuGiOhDao.selectAllRaces().toMutableList()
        allRaces.add(0, "Race")
        withContext(appCoroutineDispatchers.mainDispatcher) {
            emit(allRaces.toList())
        }
    }
}
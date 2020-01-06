package com.coroutinedispatcher.yourmove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.model.SearchObject
import com.coroutinedispatcher.yourmove.utils.Event

class SharedViewModel : ViewModel() {
    private val _searchObjectLiveData = MutableLiveData<Event<SearchObject>>()
    val searchObjectLiveData: LiveData<Event<SearchObject>> = _searchObjectLiveData

    fun pushSearchObject(searchObject: SearchObject) {
        _searchObjectLiveData.value = Event(searchObject)
    }
}
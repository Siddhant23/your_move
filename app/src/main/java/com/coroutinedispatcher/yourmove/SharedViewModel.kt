package com.coroutinedispatcher.yourmove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.utils.Event

class SharedViewModel : ViewModel() {
    private val _closeKeyboardEvent = MutableLiveData<Event<String>>()
    private val _slideBackEvent = MutableLiveData<Event<String>>()
    val closeKeyboardEvent: LiveData<Event<String>> = _closeKeyboardEvent
    val slideBackEvent: LiveData<Event<String>> = _slideBackEvent

    fun slideBack() {
        _slideBackEvent.value = Event("SlideBackEvent")
    }

    fun closeKeyboard() {
        _closeKeyboardEvent.value = Event("CloseKeyboard")
    }
}
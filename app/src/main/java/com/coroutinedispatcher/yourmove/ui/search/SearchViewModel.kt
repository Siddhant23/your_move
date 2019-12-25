package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber

class SearchViewModel @AssistedInject constructor(
    private val databaseReference: DatabaseReference,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val m = MutableLiveData<String>()

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }

    init {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Timber.d(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val yugiohcard = it.getValue(YuGiOhCard::class.java)
                    m.value = yugiohcard?.name
                }
            }

        })
    }
}

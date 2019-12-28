package com.coroutinedispatcher.yourmove.ui.search

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.SEARCH_FRAGMENT_ERROR_STATE
import com.coroutinedispatcher.yourmove.utils.SEARCH_FRAGMENT_LOADING_STATE
import com.coroutinedispatcher.yourmove.utils.YUGIOH_CARDS_STATE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class SearchViewModel @AssistedInject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableListOfCards: MutableList<YuGiOhCard> = mutableListOf()
    val cardsLiveData: LiveData<List<YuGiOhCard>> = savedStateHandle.getLiveData(YUGIOH_CARDS_STATE)
    val errorLiveData: LiveData<Int> = savedStateHandle.getLiveData(SEARCH_FRAGMENT_ERROR_STATE)
    val loadingLiveData: LiveData<Int> = savedStateHandle.getLiveData(
        SEARCH_FRAGMENT_LOADING_STATE
    )

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }

    init {
        checkConnection()
    }

    private fun checkConnection() {
        firebaseDatabase.getReference(".info/connected")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val connected = snapshot.getValue(Boolean::class.java) ?: false
                    if (connected) {
                        savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, View.VISIBLE)
                        savedStateHandle.set(SEARCH_FRAGMENT_ERROR_STATE, View.GONE)
                        retrieveData()
                    } else {
                        savedStateHandle.set(SEARCH_FRAGMENT_ERROR_STATE, View.VISIBLE)
                        savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, View.GONE)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun retrieveData() {
        firebaseDatabase.reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                savedStateHandle.set(SEARCH_FRAGMENT_ERROR_STATE, View.VISIBLE)
                savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, View.GONE)
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach { data ->
                    val imageUrlSmall =
                        data.child("card_images").child("0").child("image_url_small")
                            .getValue(String::class.java)
                    val yugiohcards = data.getValue(YuGiOhCard::class.java)

                    yugiohcards?.let { card ->
                        mutableListOfCards.add(
                            YuGiOhCard(
                                name = card.name,
                                type = card.type,
                                imageUrlSmall = imageUrlSmall,
                                race = card.race
                            )
                        )
                    }
                }
                savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, View.GONE)
                savedStateHandle.set(SEARCH_FRAGMENT_ERROR_STATE, View.GONE)
                savedStateHandle.set(YUGIOH_CARDS_STATE, mutableListOfCards)
            }
        })
    }
}

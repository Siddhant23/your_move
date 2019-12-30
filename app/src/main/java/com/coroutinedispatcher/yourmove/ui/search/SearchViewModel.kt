package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.*
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
                        retrieveData()
                    } else {
                        savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, OFFLINE)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun retrieveData() {
        savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, LOADING)
        firebaseDatabase.reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, OFFLINE)
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
                                id = card.id,
                                name = card.name,
                                type = card.type,
                                imageUrlSmall = imageUrlSmall,
                                race = card.race
                            )
                        )
                    }
                }
                savedStateHandle.set(SEARCH_FRAGMENT_LOADING_STATE, SYNCED)
                savedStateHandle.set(YUGIOH_CARDS_STATE, mutableListOfCards)
            }
        })
    }
}

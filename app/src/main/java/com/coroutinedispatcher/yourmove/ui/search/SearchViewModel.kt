package com.coroutinedispatcher.yourmove.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.YUGIOH_CARDS_STATE
import com.google.firebase.database.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber

class SearchViewModel @AssistedInject constructor(
    private val databaseReference: DatabaseReference,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableListOfCards: MutableList<YuGiOhCard> = mutableListOf()
    val cardsLiveData: LiveData<List<YuGiOhCard>> = savedStateHandle.getLiveData(YUGIOH_CARDS_STATE)
    private lateinit var eventListener: ValueEventListener

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }

    init {
        eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Timber.d(p0.message)
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
                savedStateHandle.set(YUGIOH_CARDS_STATE, mutableListOfCards)
            }
        })
    }

    fun filterList(input: String) {
        //todo filter the list locally ,or make advanced search
    }
}

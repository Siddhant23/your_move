package com.coroutinedispatcher.yourmove.ui.search

interface CardAdapterContract {
    fun scrollToTop()
    fun onCardClick(cardId: String)
}
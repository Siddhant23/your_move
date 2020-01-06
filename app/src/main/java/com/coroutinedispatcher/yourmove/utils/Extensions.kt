package com.coroutinedispatcher.yourmove.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.coroutinedispatcher.yourmove.model.YuGiOhCard

inline fun <reified T : ViewModel> Fragment.savedStateViewModel(
    crossinline provider: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, Bundle()) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T =
            provider(handle) as T
    }
}

inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
    }
}

val DIFF_UTIL_CARDS = object : DiffUtil.ItemCallback<YuGiOhCard>() {
    override fun areItemsTheSame(oldItem: YuGiOhCard, newItem: YuGiOhCard): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: YuGiOhCard, newItem: YuGiOhCard): Boolean =
        oldItem.name == newItem.name
}
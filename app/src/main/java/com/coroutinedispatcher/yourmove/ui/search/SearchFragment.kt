package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import timber.log.Timber

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by savedStateViewModel {
        YourMoveApplication.getYourMoveComponent().searchViewModelFactory.create(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.m.observe(this, Observer {
            Timber.d(it)
        })
    }
}

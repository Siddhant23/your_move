package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel

class SearchFragment : Fragment() {

    private var cardRecyclerView: RecyclerView? = null
    private val cardAdapter: CardAdapter by lazy {
        val picasso = YourMoveApplication.getYourMoveComponent().picasso
        CardAdapter(picasso)
    }

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
        initialiseComponents(view)
        searchViewModel.cardsLiveData.observe(this, Observer {
            cardAdapter.submitList(it)
        })
    }

    private fun initialiseComponents(view: View) {
        cardRecyclerView = view.findViewById(R.id.rv_yugioh_cards)
        cardRecyclerView?.adapter = cardAdapter
    }

    override fun onDestroyView() {
        cardRecyclerView?.adapter = null
        cardRecyclerView = null
        super.onDestroyView()
    }
}

package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchFragment : Fragment(), CardAdapterContract {

    private var cardRecyclerView: RecyclerView? = null
    private var fabSearch: FloatingActionButton? = null
    private val searchViewModel: SearchViewModel by savedStateViewModel {
        YourMoveApplication.getYourMoveComponent().searchViewModelFactory.create(it)
    }
    private val cardAdapter: CardAdapter by lazy {
        val picasso = YourMoveApplication.getYourMoveComponent().picasso
        CardAdapter(picasso, this)
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
        afterInitialize()
        searchViewModel.cards.observe(this, Observer {
            cardAdapter.submitList(it)
        })
    }

    private fun initialiseComponents(view: View) {
        cardRecyclerView = view.findViewById(R.id.rv_yugioh_cards)
        fabSearch = view.findViewById(R.id.fab_search)
        cardRecyclerView?.adapter = cardAdapter
    }

    private fun afterInitialize() {
        fabSearch?.setOnClickListener {
            findNavController().navigate(R.id.advancedSearchFragment)
        }
    }

    override fun onDestroyView() {
        cardRecyclerView?.adapter = null
        cardRecyclerView = null
        fabSearch = null
        super.onDestroyView()
    }

    override fun scrollToTop() {
    }

    override fun onCardClick(cardId: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToCardDetailsFragment(cardId)
        findNavController().navigate(action)
    }
}

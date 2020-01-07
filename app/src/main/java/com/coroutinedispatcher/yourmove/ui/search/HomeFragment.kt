package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.SharedViewModel
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(), CardAdapterContract {

    private var cardRecyclerView: RecyclerView? = null
    private var fabSearch: FloatingActionButton? = null
    private val homeViewModel: HomeViewModel by savedStateViewModel {
        YourMoveApplication.getYourMoveComponent().homeViewModelFactory.create(it)
    }
    private val cardAdapter: CardAdapter by lazy {
        val picasso = YourMoveApplication.getYourMoveComponent().picasso
        CardAdapter(picasso, this)
    }
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseComponents(view)
        afterInitialize()
        homeViewModel.cards.observe(this, Observer {
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
        sharedViewModel.searchObjectLiveData.observe(this, Observer {
            it.getContentIfNotHandled()?.let {searchObject ->
                homeViewModel.performSearch(searchObject)
            }
        })
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
        val action = HomeFragmentDirections.actionSearchFragmentToCardDetailsFragment(cardId)
        findNavController().navigate(action)
    }
}

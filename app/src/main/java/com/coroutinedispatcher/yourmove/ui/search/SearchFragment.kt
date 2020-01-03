package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.LOADING
import com.coroutinedispatcher.yourmove.utils.OFFLINE
import com.coroutinedispatcher.yourmove.utils.SYNCED
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment(), CardAdapterContract {

    private var cardRecyclerView: RecyclerView? = null
    private var etUserSearchInput: TextInputEditText? = null
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
        searchViewModel.yuGiOhCards.observe(this, Observer {
            cardAdapter.submitList(it)
//            cardAdapter.saveListState(it)
            etUserSearchInput?.isEnabled = true
        })
    }

    private fun initialiseComponents(view: View) {
        etUserSearchInput = view.findViewById(R.id.et_user_search_input)
        cardRecyclerView = view.findViewById(R.id.rv_yugioh_cards)
        cardRecyclerView?.adapter = cardAdapter
        etUserSearchInput?.isEnabled = false
    }

    private fun afterInitialize() {
        etUserSearchInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
                cardAdapter.filter.filter(input.toString())
            }
        })
    }

    override fun onDestroyView() {
        cardRecyclerView?.adapter = null
        cardRecyclerView = null
        etUserSearchInput = null
        super.onDestroyView()
    }

    override fun scrollToTop() {
        cardRecyclerView?.scrollToPosition(0)
    }

    override fun onCardClick(cardId: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToCardDetailsFragment(cardId)
        findNavController().navigate(action)
    }
}

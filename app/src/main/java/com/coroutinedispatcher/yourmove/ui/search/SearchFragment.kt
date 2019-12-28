package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private var cardRecyclerView: RecyclerView? = null
    private val cardAdapter: CardAdapter by lazy {
        val picasso = YourMoveApplication.getYourMoveComponent().picasso
        CardAdapter(picasso)
    }
    private var etUserSearchInput: TextInputEditText? = null

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
        afterInitialize()
        searchViewModel.cardsLiveData.observe(this, Observer {
            cardAdapter.submitList(it)
        })
    }

    private fun initialiseComponents(view: View) {
        etUserSearchInput = view.findViewById(R.id.et_user_search_input)
        cardRecyclerView = view.findViewById(R.id.rv_yugioh_cards)
        cardRecyclerView?.adapter = cardAdapter
    }

    private fun afterInitialize() {
        etUserSearchInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                searchViewModel.filterList(input.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (cardAdapter.itemCount == 0) {
                    Toast.makeText(
                        requireActivity(),
                        R.string.please_wait_for_the_list_to_be_filled,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onDestroyView() {
        cardRecyclerView?.adapter = null
        cardRecyclerView = null
        etUserSearchInput = null
        super.onDestroyView()
    }
}

package com.coroutinedispatcher.yourmove.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment(), CardAdapterContract {

    private var cardRecyclerView: RecyclerView? = null
    private val cardAdapter: CardAdapter by lazy {
        val picasso = YourMoveApplication.getYourMoveComponent().picasso
        CardAdapter(picasso, this)
    }
    private var etUserSearchInput: TextInputEditText? = null
    private var pbSearch: ProgressBar? = null
    private var clSearchFragmentHolder: ConstraintLayout? = null
    private var errorSnackBar: Snackbar? = null

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
            cardAdapter.saveListState(it)
            etUserSearchInput?.isEnabled = true
        })
        searchViewModel.errorLiveData.observe(this, Observer { errorVisibility ->
            clSearchFragmentHolder?.let { rootView ->
                if (errorVisibility == View.VISIBLE) {
                    errorSnackBar = Snackbar.make(
                        rootView,
                        R.string.error_occurred,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    errorSnackBar?.show()
                } else {
                    errorSnackBar = Snackbar.make(
                        rootView,
                        R.string.you_are_connected,
                        Snackbar.LENGTH_SHORT
                    )
                    errorSnackBar?.show()
                }
            }
        })
        searchViewModel.loadingLiveData.observe(this, Observer {
            pbSearch?.visibility = it
        })
    }

    private fun initialiseComponents(view: View) {
        etUserSearchInput = view.findViewById(R.id.et_user_search_input)
        cardRecyclerView = view.findViewById(R.id.rv_yugioh_cards)
        pbSearch = view.findViewById(R.id.pb_search)
        clSearchFragmentHolder = view.findViewById(R.id.cl_search_fragment_holder)
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
        clSearchFragmentHolder = null
        pbSearch = null
        errorSnackBar = null
        super.onDestroyView()
    }

    override fun scrollToTop() {
        cardRecyclerView?.scrollToPosition(0)
    }

    override fun onCardClick() {
    }
}

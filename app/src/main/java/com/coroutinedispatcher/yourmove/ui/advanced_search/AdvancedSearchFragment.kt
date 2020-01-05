package com.coroutinedispatcher.yourmove.ui.advanced_search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText


class AdvancedSearchFragment : BottomSheetDialogFragment() {
    private var levelSpinner: Spinner? = null
    private var raceSpinner: Spinner? = null
    private var typeSpinner: Spinner? = null
    private var defTextInputEditText: TextInputEditText? = null
    private var atkTextInputEditText: TextInputEditText? = null
    private var nameTextInputEditText: TextInputEditText? = null

    private val advancedSearchViewModel by savedStateViewModel<AdvancedSearchViewModel> {
        YourMoveApplication.getYourMoveComponent().advancedSearchViewModelFactory.create(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advanced_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponents(view)
        afterInitialize()
    }

    private fun initializeComponents(view: View) {
        levelSpinner = view.findViewById(R.id.sp_level)
        raceSpinner = view.findViewById(R.id.sp_race)
        typeSpinner = view.findViewById(R.id.sp_type)
        defTextInputEditText = view.findViewById(R.id.et_def)
        atkTextInputEditText = view.findViewById(R.id.et_atk)
        nameTextInputEditText = view.findViewById(R.id.et_name)
    }

    private fun afterInitialize() {
        advancedSearchViewModel.levelData.observe(viewLifecycleOwner, Observer {
            val adapter = makeAdapter(it)
            adapter?.let { incomingAdapter ->
                levelSpinner?.adapter = incomingAdapter
            }
        })

        advancedSearchViewModel.racesLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = makeAdapter(it)
            adapter?.let { incomingAdapter ->
                raceSpinner?.adapter = incomingAdapter
            }
        })

        advancedSearchViewModel.typesLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = makeAdapter(it)
            adapter?.let { incomingAdapter ->
                typeSpinner?.adapter = incomingAdapter
            }
        })

        levelSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }
        }
    }

    private fun makeAdapter(list: List<String>?): SpinnerAdapter? {
        list?.let {
            return object : ArrayAdapter<String>(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                it
            ) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val textView: TextView = view as TextView
                    if (position == 0) {
                        textView.setTextColor(Color.GRAY)
                    }
                    return view
                }
            }
        }
        return null
    }

    override fun onDestroyView() {
        levelSpinner = null
        raceSpinner = null
        typeSpinner = null
        defTextInputEditText = null
        atkTextInputEditText = null
        nameTextInputEditText = null
        super.onDestroyView()
    }
}
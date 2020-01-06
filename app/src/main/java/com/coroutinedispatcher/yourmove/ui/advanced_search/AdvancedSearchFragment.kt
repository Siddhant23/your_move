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
import com.coroutinedispatcher.yourmove.utils.viewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText


class AdvancedSearchFragment : BottomSheetDialogFragment() {
    private var levelSpinner: Spinner? = null
    private var raceSpinner: Spinner? = null
    private var typeSpinner: Spinner? = null
    private var defTextInputEditText: TextInputEditText? = null
    private var atkTextInputEditText: TextInputEditText? = null
    private var nameTextInputEditText: TextInputEditText? = null

    private val advancedSearchViewModel by viewModel {
        YourMoveApplication.getYourMoveComponent().advancedSearchViewModel
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
                val chosenField = parent?.getItemAtPosition(position).toString()
                if (chosenField != "Level") {

                }
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
                    val textView = super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0) {
                        textView.setTextColor(Color.GRAY)
                    } else {
                        textView.setTextColor(Color.WHITE)
                    }
                    return textView
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
package com.coroutinedispatcher.yourmove.ui.advanced_search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coroutinedispatcher.yourmove.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AdvancedSearchFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advanced_search, container, false)
    }


}

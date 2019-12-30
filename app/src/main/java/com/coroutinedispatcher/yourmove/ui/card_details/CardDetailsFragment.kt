package com.coroutinedispatcher.yourmove.ui.card_details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.coroutinedispatcher.yourmove.R

/**
 * A simple [Fragment] subclass.
 */
class CardDetailsFragment : Fragment() {

    private val args: CardDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = args.cardId
        view.findViewById<TextView>(R.id.tv).text = data
    }
}

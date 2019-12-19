package com.coroutinedispatcher.yourmove.ui.quiz

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.squareup.picasso.Picasso

class QuizFragment : Fragment() {
    private lateinit var viewModel: QuizViewModel

    private val picasso: Picasso by lazy {
        YourMoveApplication.getYourMoveComponent().picasso
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        val image = view.findViewById<ImageView>(R.id.iv_yugioh_card)
        picasso.load(R.drawable.yugioh_placeholder).fit().centerCrop().into(image)
    }
}

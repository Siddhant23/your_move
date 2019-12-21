package com.coroutinedispatcher.yourmove.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.squareup.picasso.Picasso

class QuizFragment : Fragment() {
    private val quizViewModel: QuizViewModel by savedStateViewModel {
        YourMoveApplication.getYourMoveComponent().quizViewModelFactory.create(it)
    }

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
        val image = view.findViewById<ImageView>(R.id.iv_yugioh_card)
        picasso.load(R.drawable.yugioh_placeholder).fit().centerCrop().into(image)
        quizViewModel.imageGeneratedUrl.observe(this, Observer {
            picasso.load(it).fit().centerCrop().placeholder(R.drawable.yugioh_placeholder)
                .error(R.drawable.yugioh_placeholder).into(image)
        })
    }
}

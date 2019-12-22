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
import com.coroutinedispatcher.yourmove.utils.BUTTON_STATE_ERROR
import com.coroutinedispatcher.yourmove.utils.BUTTON_STATE_SUCESS
import com.coroutinedispatcher.yourmove.utils.BUTTON_STATE_WAIT
import com.coroutinedispatcher.yourmove.utils.savedStateViewModel
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class QuizFragment : Fragment() {
    private val quizViewModel: QuizViewModel by savedStateViewModel {
        YourMoveApplication.getYourMoveComponent().quizViewModelFactory.create(it)
    }

    private val picasso: Picasso by lazy {
        YourMoveApplication.getYourMoveComponent().picasso
    }

    private var submitButton: MaterialButton? = null
    private var yuGiOhImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponents(view)
        afterInitialize()
    }

    private fun initializeComponents(view: View) {
        yuGiOhImage = view.findViewById(R.id.iv_yugioh_card)
        submitButton = view.findViewById(R.id.btn_submit)
        picasso.load(R.drawable.yugioh_placeholder).fit().centerCrop().into(yuGiOhImage)
    }

    private fun afterInitialize() {
        quizViewModel.imageGeneratedUrl.observe(this, Observer {
            picasso.load(it).fit().centerCrop()
                .placeholder(R.drawable.yugioh_placeholder)
                .error(R.drawable.yugioh_placeholder)
                .into(yuGiOhImage, object : Callback {
                    override fun onSuccess() {
                        quizViewModel.makeSuccessButton()
                    }

                    override fun onError(e: Exception?) {
                        quizViewModel.makeErrorButton()
                    }
                })
        })

        quizViewModel.buttonState.observe(this, Observer {
            when (it) {
                BUTTON_STATE_ERROR, BUTTON_STATE_SUCESS -> {
                    submitButton?.isEnabled = true
                }
                BUTTON_STATE_WAIT -> {
                    submitButton?.isEnabled = false
                }
            }
            submitButton?.text = it
        })

        submitButton?.setOnClickListener {
            quizViewModel.launchRandomImageApiCall()
        }
    }

    override fun onDestroyView() {
        yuGiOhImage = null
        submitButton = null
        super.onDestroyView()
    }
}

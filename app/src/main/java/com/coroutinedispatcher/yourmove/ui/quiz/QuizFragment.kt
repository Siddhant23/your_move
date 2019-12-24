package com.coroutinedispatcher.yourmove.ui.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.SharedViewModel
import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.utils.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class QuizFragment : Fragment() {
    private val quizViewModel: QuizViewModel by savedStateViewModel {
        YourMoveApplication.getYourMoveComponent().quizViewModelFactory.create(it)
    }

    private val picasso: Picasso by lazy {
        YourMoveApplication.getYourMoveComponent().picasso
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var submitButton: MaterialButton? = null
    private var yuGiOhImage: ImageView? = null
    private var usersAnswerTextInputEditText: TextInputEditText? = null
    private var userCorrectAnswerTextView: TextView? = null
    private var userWrongAnswerTextView: TextView? = null
    private var userTotalScoreTextView: TextView? = null
    private var skipButton: MaterialButton? = null
    private var dialog: AlertDialog? = null
    private var mustShowDialog = false

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
        usersAnswerTextInputEditText = view.findViewById(R.id.tiet_answer)
        userCorrectAnswerTextView = view.findViewById(R.id.tv_right_answer)
        userWrongAnswerTextView = view.findViewById(R.id.tv_user_wrong_answer)
        userTotalScoreTextView = view.findViewById(R.id.tv_user_all_time_score)
        skipButton = view.findViewById(R.id.btn_skip)
        picasso.load(R.drawable.yugioh_placeholder).fit().centerCrop().into(yuGiOhImage)
    }

    @SuppressLint("SetTextI18n")
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
                BUTTON_STATE_WAIT, BUTTON_ALL_DONE_FOR_TODAY_STATE -> {
                    submitButton?.isEnabled = false
                }
            }
            submitButton?.text = it
        })

        quizViewModel.userCorrectAnswerLiveData.observe(this, Observer {
            userCorrectAnswerTextView?.text =
                "${requireActivity().resources.getString(R.string.right_answer_score)} $it"
        })

        quizViewModel.userWrongAnswerLiveData.observe(this, Observer {
            if (it == WRONG_ANSWER_LIMIT) {
                skipButton?.isEnabled = false
                quizViewModel.forceUserToComeBackTomorrow()
                showInfoDialog()
            }
            userWrongAnswerTextView?.text =
                "${requireActivity().resources.getString(R.string.wrong_score)} $it"
        })

        quizViewModel.userTotalScoreLiveData.observe(this, Observer {
            userTotalScoreTextView?.text =
                "${requireActivity().resources.getString(R.string.total_user_points)} $it"
        })

        submitButton?.setOnClickListener {
            if (usersAnswerTextInputEditText?.text.toString().isNotEmpty()) {
                quizViewModel.checkAnswerAndRelaunchCall(usersAnswerTextInputEditText?.text.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    R.string.please_place_an_answer_to_submit,
                    Toast.LENGTH_SHORT
                ).show()
            }
            usersAnswerTextInputEditText?.text?.clear()
        }
    }

    private fun showInfoDialog() {
        dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.NotificationDialogTheme
        )
            .setTitle(R.string.app_name)
            .setMessage(R.string.come_tomorrow)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                sharedViewModel.slideBack()
                dialog.dismiss()
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        quizViewModel.startQuiz()
    }

    override fun onDestroyView() {
        yuGiOhImage = null
        submitButton = null
        usersAnswerTextInputEditText = null
        userCorrectAnswerTextView = null
        userWrongAnswerTextView = null
        skipButton = null
        userTotalScoreTextView = null
        dialog = null
        super.onDestroyView()
    }
}

package com.geanbrandao.eventmanager.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.TooltipCompat
import androidx.core.widget.doOnTextChanged
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.utils.Variable
import timber.log.Timber


class CustomAppCompatEditText: AppCompatEditText {

    private val STATE_ERROR: IntArray = intArrayOf(R.attr.state_error)

    var isInputValid = Variable(false)
    var mIsError = false
    var iconError: AppCompatImageView? = null
    var iconHelp: AppCompatImageView? = null

    var iconErrorId: Int = -1
    var iconHelpId: Int = -1

    var messageError: String? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {

        attrs.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.CustomAppCompatEditText, 0, 0)

            mIsError = typedArray.getBoolean(R.styleable.CustomAppCompatEditText_state_error, false)


            typedArray.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        iconError = rootView.findViewById(iconErrorId)
        iconHelp = rootView.findViewById(iconHelpId)
        Timber.d("iconError $iconError")

        iconError?.let { icon ->
            TooltipCompat.setTooltipText(icon, messageError)
        }

        iconHelp?.setOnClickListener {

        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 2)
        if (mIsError) {
            View.mergeDrawableStates(drawableState, STATE_ERROR)
        }
        return drawableState
    }

    private fun setError(boolean: Boolean) {
        mIsError = boolean
        handler.post {
            refreshDrawableState()
        }
    }

    fun isValid(isValidInput: (s: String) -> Boolean) {
        doOnTextChanged { text, _, _, _ ->
            text?.let {s ->
                val input = s.toString()
                if (input.isEmpty()) {
                    setError(false)
                    isInputValid.value = false
                    return@doOnTextChanged
                }
                if (isValidInput.invoke(input)) {
                    setError(false)
                    isInputValid.value = true
                } else {
                    setError(true)
                    isInputValid.value = false
                }
            }
        }
    }
}
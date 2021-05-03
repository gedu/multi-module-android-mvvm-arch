package com.teddy.brunch.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.teddy.brunch.R
import com.teddy.brunch.databinding.SimpleRateBarBinding
import kotlin.math.floor
import kotlin.math.min

class SimpleRateBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = SimpleRateBarBinding.inflate(LayoutInflater.from(context), this, true)
    private var currentRating: Float = 0f

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.SimpleRateBar, defStyleAttr, 0)
            currentRating = typedArray.getFloat(R.styleable.SimpleRateBar_rating, 0f)
            typedArray.recycle()
        }
        fillRateBar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun fillRateBar() {
        val filledSteps = min(floor(currentRating), 5f)
        val halfStep: Float = currentRating - filledSteps
        val fullDrawable = resources.getDrawable(R.drawable.ic_bubble_full)
        for (i in 0 until filledSteps.toInt()) {
            val bubbleView = getChildAt(i)
            bubbleView?.let {
                (it as AppCompatImageView).setImageDrawable(fullDrawable)
            }
        }

        if (halfStep > 0f) {
            val bubbleView = getChildAt(filledSteps.toInt())
            bubbleView?.let {
                (it as AppCompatImageView).setImageDrawable(resources.getDrawable(R.drawable.ic_bubble_half))
            }
        }
    }

    fun setRating(rate: Float) {
        currentRating = rate
        fillRateBar()
    }
}
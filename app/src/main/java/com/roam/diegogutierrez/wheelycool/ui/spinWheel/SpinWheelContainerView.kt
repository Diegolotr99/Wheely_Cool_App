package com.roam.diegogutierrez.wheelycool.ui.spinWheel

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.roam.diegogutierrez.domain.SpinItem
import com.roam.diegogutierrez.wheelycool.R
import com.roam.diegogutierrez.wheelycool.databinding.SpinWheelLayoutBinding
import com.roam.diegogutierrez.wheelycool.ui.common.convertDpToPixel
import java.util.Random


class SpinWheelContainerView(context: Context, attrs: AttributeSet) :
    RelativeLayout(context, attrs) {

    private var vBackgroundColor = 0
    private var textColor = 0
    private var textSize = 0
    private var borderColor = 0
    private var textPadding = 0
    private var edgeWidth = 0
    private var centerImage: Drawable? = null
    private var cursorImage: Drawable? = null

    private var spinWheelView: SpinWheelView
    private var ivCursorView: ImageView



    private val inflater = LayoutInflater.from(context)
    private val binding = SpinWheelLayoutBinding.inflate(inflater, this, true)


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SpinWheelView, 0, 0).apply {
            try {
                vBackgroundColor = getColor(R.styleable.SpinWheelView_backgroundColor, -0x340000)
                textSize = getDimensionPixelSize(
                    R.styleable.SpinWheelView_textSize, convertDpToPixel(15f, context)
                )
                textColor = getColor(R.styleable.SpinWheelView_textColor, 0)
                textPadding = getDimensionPixelSize(
                    R.styleable.SpinWheelView_textPadding, convertDpToPixel(10f, context)
                ) + convertDpToPixel(10f, context)
                cursorImage = getDrawable(R.styleable.SpinWheelView_cursor)
                centerImage = getDrawable(R.styleable.SpinWheelView_centerImage)
                edgeWidth = getInt(R.styleable.SpinWheelView_edgeWidth, 10)
                borderColor = getColor(R.styleable.SpinWheelView_edgeColor, 0)
            } finally {
                recycle()
            }
        }

        spinWheelView = binding.spinWheelView
        ivCursorView = binding.indicatorView
        spinWheelView.setItemBackgroundColor(vBackgroundColor)
        spinWheelView.setTextPadding(textPadding)
        spinWheelView.setTextSize(textSize)
        spinWheelView.setCenterImage(centerImage)
        spinWheelView.setBorderColor(borderColor)
        spinWheelView.setBorderWidth(edgeWidth.toFloat())
        if (textColor != 0) spinWheelView.setTextColor(textColor)
        ivCursorView.setImageDrawable(cursorImage)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //This is to control that the touch events triggered are only going to the SpinWheelView
        for (i in 0 until childCount) {
            if (isSpinWheelView(getChildAt(i))) {
                return super.dispatchTouchEvent(ev)
            }
        }
        return false
    }

    private fun isSpinWheelView(view: View): Boolean {
        if (view is ViewGroup) {
            for (i in 0 until childCount) {
                if (isSpinWheelView((view).getChildAt(i))) {
                    return true
                }
            }
        }
        return view is SpinWheelContainerView
    }

    fun setData(data: List<SpinItem>) {
        spinWheelView.setData(data)
    }

    fun startSpinWheelWithRandomTarget() {
        val r = Random()
        spinWheelView.rotateTo(r.nextInt(spinWheelView.getSpinItemListSize() - 1))
    }
}
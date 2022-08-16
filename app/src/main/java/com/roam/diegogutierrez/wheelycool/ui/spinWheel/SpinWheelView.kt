package com.roam.diegogutierrez.wheelycool.ui.spinWheel

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.roam.diegogutierrez.domain.SpinItem
import com.roam.diegogutierrez.wheelycool.R
import kotlin.math.cos
import kotlin.math.sin


open class SpinWheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var range = RectF()
    private var radius = 0
    private var arcPaint: Paint = Paint()
    private var backgroundPaint: Paint = Paint()
    private var textPaint: TextPaint = TextPaint()
    private val mStartAngle = 45f
    private var center = 0f
    private var padding = 0
    private var textPadding = 0
    private var textSize = 0
    private var roundOfNumber = 4
    private var edgeWidth = -1f
    private var isRunning = false
    private var borderColor = 0
    private var defaultBackgroundColor = 0
    private var drawableCenterImage: Drawable? = null
    private var textColor = 0

    private var spinItemList: List<SpinItem> = emptyList()

    private fun init() {
        arcPaint.isAntiAlias = true
        arcPaint.isDither = true
        textPaint = TextPaint()
        textPaint.isAntiAlias = true
        if (textColor != 0) textPaint.color = textColor
        textPaint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 14f,
            resources.displayMetrics
        )
        range = RectF(
            padding.toFloat(), padding.toFloat(),
            (padding + radius).toFloat(), (padding + radius).toFloat()
        )
    }

    fun getSpinItemListSize(): Int {
        return spinItemList.size
    }

    fun setData(spinItems: List<SpinItem>) {
        spinItemList = spinItems
        invalidate()
    }

    fun setItemBackgroundColor(color: Int) {
        defaultBackgroundColor = color
        invalidate()
    }

    fun setBorderColor(color: Int) {
        borderColor = color
        invalidate()
    }

    fun setTextPadding(padding: Int) {
        textPadding = padding
        invalidate()
    }

    fun setCenterImage(drawable: Drawable?) {
        drawableCenterImage = drawable
        invalidate()
    }

    fun setTextSize(size: Int) {
        textSize = size
        invalidate()
    }


    fun setBorderWidth(width: Float) {
        edgeWidth = width
        invalidate()
    }

    fun setTextColor(color: Int) {
        textColor = color
        invalidate()
    }

    /**
     * @param canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (spinItemList.isEmpty()) {
            return
        }
        drawBackgroundColor(canvas, defaultBackgroundColor)
        init()
        var tmpAngle = mStartAngle
        val sweepAngle = 360f / spinItemList.size
        for (i in spinItemList.indices) {
            val sliceColor = getColor(index = i)

            arcPaint.style = Paint.Style.FILL
            arcPaint.color = sliceColor
            canvas.drawArc(range, tmpAngle, sweepAngle, true, arcPaint)

            if (borderColor != 0 && edgeWidth > 0) {
                arcPaint.style = Paint.Style.STROKE
                arcPaint.color = borderColor
                arcPaint.strokeWidth = edgeWidth
                canvas.drawArc(range, tmpAngle, sweepAngle, true, arcPaint)
            }

            if (!TextUtils.isEmpty(spinItemList[i].title)) drawTitleText(
                canvas,
                tmpAngle,
                spinItemList[i].title,
            )

            tmpAngle += sweepAngle
        }
    }

    private fun drawBackgroundColor(canvas: Canvas, color: Int) {
        if (color == 0) return
        backgroundPaint = Paint()
        backgroundPaint.color = color
        canvas.drawCircle(center, center, center - 5, backgroundPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Float = measuredWidth.coerceAtMost(measuredHeight).toFloat()
        padding = if (paddingLeft == 0) 10 else paddingLeft
        radius = width.toInt() - padding * 2
        center = width / 2
        setMeasuredDimension(width.toInt(), width.toInt())
    }


    private fun drawTitleText(
        canvas: Canvas,
        tmpAngle: Float,
        mStr: String,
    ) {
        canvas.save()
        val arraySize = spinItemList.size
        val typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        textPaint.typeface = typeface
        textPaint.textSize = textSize.toFloat()
        textPaint.textAlign = Paint.Align.LEFT
        val textWidth = textPaint.measureText(mStr)
        val initFloat = tmpAngle + 360f / arraySize / 2
        val angle = (initFloat * Math.PI / 180).toFloat()
        val x = (center + radius / 2 / 2 * cos(angle.toDouble())).toInt()
        val y = (center + radius / 2 / 2 * sin(angle.toDouble())).toInt()
        val rect = RectF(
            x + textWidth, y.toFloat(),
            x - textWidth, y.toFloat()
        )
        val path = Path()
        path.addRect(rect, Path.Direction.CW)
        path.close()
        canvas.rotate(initFloat + arraySize / 18f, x.toFloat(), y.toFloat())
        canvas.drawTextOnPath(
            mStr,
            path,
            textPadding / 7f,
            textPaint.textSize / 2.75f,
            textPaint
        )
        canvas.restore()
    }

    /**
     * @return
     */
    private fun getAngleOfIndexTarget(index: Int): Float {
        return 360f / spinItemList.size * index
    }

    fun rotateTo(
        index: Int,
        rotation: SpinRotation = SpinRotation.CLOCKWISE,
        startSlow: Boolean = true
    ) {
        if (isRunning) {
            return
        }
        val rotationAssess = if (rotation.direction <= 0) 1 else -1

        if (getRotation() != 0.0f) {
            setRotation(getRotation() % 360f)
            val animationStart: TimeInterpolator =
                if (startSlow) AccelerateInterpolator() else LinearInterpolator()
            val multiplier: Float = if (getRotation() > 200f) 2F else 1.toFloat()
            animate()
                .setInterpolator(animationStart)
                .setDuration(500L)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        isRunning = true
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        isRunning = false
                        setRotation(0F)
                        rotateTo(index, rotation, false)
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
                .rotation(360f * multiplier * rotationAssess)
                .start()
            return
        }

        if (rotationAssess < 0) roundOfNumber++
        val targetAngle =
            360f * roundOfNumber * rotationAssess + 270f - getAngleOfIndexTarget(index) - 360f / spinItemList.size / 2
        animate()
            .setInterpolator(DecelerateInterpolator())
            .setDuration(roundOfNumber * 1000 + 900L)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    isRunning = true
                }

                override fun onAnimationEnd(animation: Animator) {
                    isRunning = false
                    setRotation(getRotation() % 360f)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            .rotation(targetAngle)
            .start()
    }

    private fun getColor(index: Int): Int {
        val isEvenSize = spinItemList.size % 2 == 0
        val isEvenIndex = index % 2 == 0
        if (index == 0 && !isEvenSize)
           return context.resources.getColor(R.color.teal_200, null)

        return if (isEvenIndex)
            context.resources.getColor(R.color.teal_500, null)
        else
            context.resources.getColor(R.color.teal_700, null)
    }

    enum class SpinRotation(val direction: Int) {
        CLOCKWISE(0),
        COUNTERCLOCKWISE(1)
    }
}
package com.example.task2_attendright.presentation.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.task2_attendright.R

class MyCircularProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0
    private var maxProgress = 100
    private var progressBarWidth = 20f
    private var backgroundColor = ContextCompat.getColor(context, R.color.gray300)
    private var progressColor = intArrayOf(
        ContextCompat.getColor(context,R.color.blue2),
        ContextCompat.getColor(context,R.color.blue3)
    )
    private var progressTextColor = ContextCompat.getColor(context, R.color.gray800)

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = progressBarWidth
        color = backgroundColor
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = progressBarWidth
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = progressTextColor
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    private val rectF = RectF()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = (Math.min(width, height) / 2) - progressBarWidth

        rectF.set(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius
        )

        canvas.drawArc(rectF, 0f, 360f, false, backgroundPaint)

        val sweepGradient = SweepGradient(
            width / 2,
            height / 2,
            progressColor,
            null
        )
        progressPaint.shader = sweepGradient

        val sweepAngle = (360f * progress) / maxProgress
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)

        val text = "$progress%"
        canvas.drawText(text, width / 2, height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
        invalidate()
    }

    fun setProgressBarWidth(width: Float) {
        this.progressBarWidth = width
        backgroundPaint.strokeWidth = width
        progressPaint.strokeWidth = width
        invalidate()
    }

    fun setProgressColor(color: IntArray) {
        this.progressColor = color
        invalidate()
    }

    override fun setBackgroundColor(color: Int) {
        this.backgroundColor = color
        backgroundPaint.color = color
        invalidate()
    }
}

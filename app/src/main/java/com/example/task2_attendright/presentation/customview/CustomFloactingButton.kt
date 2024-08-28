package com.example.task2_attendright.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.task2_attendright.R

class CustomFloatingButton @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null
) : AppCompatButton(context, attr) {

    private var iconDrawable: Drawable
    private var backgroundDrawable: Drawable
    private var txtColor: Int = 0
    private var fontFamily: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_semibold)

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.white)
        backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.circle_floating_button) as Drawable
        iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_add)!!
        typeface = fontFamily
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = backgroundDrawable
        setTextColor(txtColor)
        typeface = fontFamily
        gravity = android.view.Gravity.CENTER
        val drawableSize = 56
        iconDrawable.setBounds(
            (width - drawableSize) / 2,
            (height - drawableSize) / 2,
            (width + drawableSize) / 2,
            (height + drawableSize) / 2
        )
        iconDrawable.draw(canvas)
    }
}

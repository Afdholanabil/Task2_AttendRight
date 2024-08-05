package com.example.task2_attendright.presentation.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.task2_attendright.R


class MyButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private var txtColorActive: Int = 0
    private var txtColorInactive: Int = 0
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable
    init {
        txtColorActive = ContextCompat.getColor(context, android.R.color.background_light)
        txtColorInactive = ContextCompat.getColor(context, R.color.gray300)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_login) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_login_disable) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(if (isEnabled) txtColorActive else txtColorInactive)
        textSize = 14f

        gravity = Gravity.CENTER
    }
}
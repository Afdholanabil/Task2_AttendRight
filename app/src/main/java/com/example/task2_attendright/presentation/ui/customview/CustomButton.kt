package com.example.task2_attendright.presentation.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.task2_attendright.R


class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private var txtColor: Int = 0
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable
    init {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.rounded_button_signin_employee) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.rounded_button_signin_employee) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 16f
        gravity = Gravity.CENTER
        text = if(isEnabled) context.getString(R.string.txt_btn_signin_employee) else context.getString(R.string.txt_btn_signin_employee)
    }
}
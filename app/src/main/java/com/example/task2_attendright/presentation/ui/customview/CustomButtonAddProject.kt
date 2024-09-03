package com.example.task2_attendright.presentation.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.task2_attendright.R

class CustomButtonAddProject @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) :
    AppCompatButton(context, attr) {
    private var txtColor: Int = 0
    private var enabledBackground: Drawable
    private var icon: Drawable
    private var fontFamily: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_semibold)

    init {
        txtColor = ContextCompat.getColor(context, R.color.white)
        typeface = fontFamily
        icon = ContextCompat.getDrawable(context, R.drawable.ic_add_circle_outline) as Drawable
        enabledBackground =
            ContextCompat.getDrawable(context, R.drawable.rounded_btn_add_meet) as Drawable
        setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
        compoundDrawablePadding = 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = enabledBackground
        setTextColor(txtColor)
        textSize = 14f
        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
        gravity = Gravity.CENTER
        text = context.getString(R.string.txt_add_project)
    }
}

package com.example.task2_attendright.presentation.customview

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
import org.w3c.dom.Attr

class MyButtonSignInEmployee @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : AppCompatButton(context,attr) {
    private var txtColor: Int = ContextCompat.getColor(context, R.color.blue1)
    private var enabledBackground: Drawable = ContextCompat.getDrawable(context, R.drawable.bg_button_signing_employee) as Drawable
    private var fontFamily: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_semibold)

    init {
        setTextColor(txtColor)
        setTypeface(fontFamily, Typeface.NORMAL)
        textSize = 14f
        gravity = Gravity.CENTER
        background = enabledBackground
        text = context.getString(R.string.sigin_with_employee_id)
    }

    override fun onDraw(canvas: Canvas) {
        setTypeface(fontFamily, Typeface.NORMAL)
        super.onDraw(canvas)

    }
}
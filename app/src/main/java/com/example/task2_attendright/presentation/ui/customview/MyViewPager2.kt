package com.example.task2_attendright.presentation.ui.customview

import android.content.AttributionSource
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager2.widget.ViewPager2

class MyViewPager2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :  androidx.constraintlayout.widget.ConstraintLayout(context, attrs) {
    private val viewPager: ViewPager2

    init {
        viewPager = ViewPager2(context, attrs)
        viewPager.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        addView(viewPager)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // Return false to disable swiping
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        // Return false to disable swiping
        return false
    }

    fun getViewPager(): ViewPager2 {
        return viewPager
    }
}
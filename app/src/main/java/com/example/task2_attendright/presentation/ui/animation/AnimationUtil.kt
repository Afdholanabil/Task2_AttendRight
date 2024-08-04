package com.example.task2_attendright.presentation.ui.animation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.task2_attendright.R

class AnimationUtil {

    companion object {
        fun startActivityWithSlideAnimation(activity:AppCompatActivity, intent: Intent) {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun finishActivityWithSlideAnimation(activity: AppCompatActivity, intent: Intent) {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        fun startFragmentWithSlideAnimation(fragment: FragmentActivity, intent: Intent) {
            fragment.startActivity(intent)
            fragment.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

        fun finishFragmentWithSlideAnimation(fragment: FragmentActivity, intent: Intent) {
            fragment.startActivity(intent)
            fragment.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
    }
}
package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityMyProfileBinding
import com.example.task2_attendright.presentation.ui.fragments.MyProfileViewFragment

class MyProfileActivity : AppCompatActivity() {

    private var _binding : ActivityMyProfileBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        if (savedInstanceState == null ){
            replaceFragmentMyProfile(MyProfileViewFragment())
        }
    }

    fun replaceFragmentMyProfile(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment_my_profile, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

}
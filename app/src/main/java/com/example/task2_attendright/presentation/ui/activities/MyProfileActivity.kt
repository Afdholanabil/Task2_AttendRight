package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
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
        _binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container_fragment_my_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

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


    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container_fragment_my_profile)

        if (currentFragment is MyProfileViewFragment) {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("FRAGMENT_TO_OPEN", 4)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        } else {
            super.onBackPressed()
        }
    }

}
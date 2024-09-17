package com.example.task2_attendright.presentation.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.data.local.UserModel
import com.example.task2_attendright.databinding.ActivityLoginWemailBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginWEmailActivity : AppCompatActivity() {
    private var _binding: ActivityLoginWemailBinding? = null
    private val binding get() = _binding
    private lateinit var refData: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLoginWemailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        refData = Firebase.database.reference

        binding!!.btnSignin.setOnClickListener {
            saveData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun saveData() {
        val etEmail = binding!!.etPwLogin1.text.toString()
        val etPassword = binding!!.etPwLogin.text.toString()
        val user = UserModel(etEmail, etPassword)
        refData.child("User").setValue(user).addOnCompleteListener { taskk ->
            if (etEmail.isEmpty() || etPassword.isEmpty()) {
                Toast.makeText(this, "Isi Data Dulu Dek", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
                intent = Intent(this, DashboardActivity::class.java)
                AnimationUtil.startActivityWithSlideAnimation(this, intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
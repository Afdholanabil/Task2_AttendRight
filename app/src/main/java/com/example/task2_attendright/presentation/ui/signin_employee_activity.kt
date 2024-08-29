package com.example.task2_attendright.presentation.ui

//import android.content.Intent
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivitySigninEmployeeBinding


class signin_employee_activity : AppCompatActivity() {
    private var _binding: ActivitySigninEmployeeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySigninEmployeeBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        binding.btnSigninEmployee.setOnClickListener {
            startActivity(Intent(this, location_activity::class.java))
        }

        binding.employeePwEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (isPasswordValid(password)) {
                    binding.employeePwEdittext.setBackgroundResource(R.drawable.rounded_edittext_employee_pw)
                    binding.errorMessage.visibility = View.GONE
                } else {
                    binding.employeePwEdittext.setBackgroundResource(R.drawable.rounded_edittext_employee_pw_wrong)
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 // Example condition
    }
}
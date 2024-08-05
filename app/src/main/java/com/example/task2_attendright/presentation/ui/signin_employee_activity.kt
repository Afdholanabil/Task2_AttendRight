package com.example.task2_attendright.presentation.ui

//import android.content.Intent
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivitySigninEmployeeBinding
import com.example.yourapp.MapsTest


class signin_employee_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_signin_employee)

        val passwordEditText = findViewById<EditText>(R.id.employee_pw_edittext)
        val errorMessage = findViewById<TextView>(R.id.error_message)

        val btnMap = findViewById<Button>(R.id.btn_signin_employee)
        btnMap.setOnClickListener{
            startActivity(Intent(this, maps_screen::class.java))
        }

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (isPasswordValid(password)) {
                    passwordEditText.setBackgroundResource(R.drawable.rounded_edittext_employee_pw)
                    errorMessage.visibility = View.GONE
                } else {
                    passwordEditText.setBackgroundResource(R.drawable.rounded_edittext_employee_pw_wrong)
                    errorMessage.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 // Example condition
    }
}
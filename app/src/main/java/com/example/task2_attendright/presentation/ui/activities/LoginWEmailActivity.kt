package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.task2_attendright.databinding.ActivityLoginWemailBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.example.task2_attendright.presentation.viewmodel.LoginState
import com.example.task2_attendright.presentation.viewmodel.UserViewModel
import com.example.task2_attendright.presentation.viewmodel.UserViewModelFactory
import com.example.task2_attendright.data.repository.UserRepositoryImpl
import com.example.task2_attendright.domain.repository.UserRepository
import com.example.task2_attendright.domain.usecase.user.LoginUseCase
import kotlinx.coroutines.launch

class LoginWEmailActivity : AppCompatActivity() {
    private var _binding : ActivityLoginWemailBinding? = null
    private val binding get() = _binding!!

    // Karena tidak pakai Room atau local data source, langsung pakai data statis
    private val userRepository: UserRepository by lazy {
        val userDao = MainActivity.database.userDao()
        UserRepositoryImpl(userDao)
    }

    private val loginUseCase = LoginUseCase(userRepository)
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this, UserViewModelFactory(loginUseCase))[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLoginWemailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLoginState()

        binding.btnSignin.setOnClickListener {
            val email = binding.tlEmailLogin.editText?.text.toString().trim()
            val password = binding.tlPwLogin.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showError("Email or password cannot be empty")
                return@setOnClickListener
            }

            // Panggil login di ViewModel
            userViewModel.login(email, password)
        }

        binding.btnSigninWithEmployeeId.setOnClickListener {
            Toast.makeText(this, "SignIn With Gmail !", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            userViewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Idle -> {
                        // Idle, do nothing
                        binding.tvErrorPw.visibility = View.GONE
                    }
                    is LoginState.Loading -> {
                        // Sedang loading, sembunyikan error
                        binding.tvErrorPw.visibility = View.GONE
                    }
                    is LoginState.Success -> {
                        if (state is LoginState.Success) {
                            val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
                            prefs.edit().putString("loggedUserId", state.user.userId).apply()

                            val intent = Intent(this@LoginWEmailActivity, DashboardActivity::class.java)
                            AnimationUtil.startActivityWithSlideAnimation(this@LoginWEmailActivity, intent)
                            finish()
                        }


                    }
                    is LoginState.Error -> {
                        // Tampilkan error message
                        binding.tvErrorPw.text = state.message
                        binding.tvErrorPw.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        binding.tvErrorPw.text = message
        binding.tvErrorPw.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

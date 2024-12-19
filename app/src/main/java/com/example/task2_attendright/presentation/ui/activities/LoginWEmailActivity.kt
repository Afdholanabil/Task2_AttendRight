package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.task2_attendright.data.local.datastore.SessionDataStore
import com.example.task2_attendright.data.repository.SessionRepositoryImpl
import com.example.task2_attendright.databinding.ActivityLoginWemailBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.example.task2_attendright.presentation.viewmodel.LoginState
import com.example.task2_attendright.presentation.viewmodel.UserViewModel
import com.example.task2_attendright.presentation.viewmodel.UserViewModelFactory
import com.example.task2_attendright.data.repository.UserRepositoryImpl
import com.example.task2_attendright.domain.repository.UserRepository
import com.example.task2_attendright.domain.usecase.session.SaveSessionUseCase
import com.example.task2_attendright.domain.usecase.user.LoginUseCase
import kotlinx.coroutines.launch

class LoginWEmailActivity : AppCompatActivity() {
    private var _binding : ActivityLoginWemailBinding? = null
    private val binding get() = _binding!!

    private val userRepository: UserRepository by lazy {
        val userDao = MainActivity.database.userDao()
        UserRepositoryImpl(userDao)
    }

    private val loginUseCase = LoginUseCase(userRepository)
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this, UserViewModelFactory(loginUseCase))[UserViewModel::class.java]
    }

    private var rememberMeChecked : Boolean = false

    private val sessionRepository by lazy {
        val sessionDataStore = SessionDataStore(applicationContext)
        SessionRepositoryImpl(sessionDataStore)
    }
    private val saveSessionUseCase by lazy {
        SaveSessionUseCase(sessionRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLoginWemailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
        val lastRememberMe = prefs.getBoolean("rememberMe", false)
        if (lastRememberMe) {
            val lastEmail = prefs.getString("loggedUserEmail", "")
            if (!lastEmail.isNullOrEmpty()) {
                binding.tlEmailLogin.editText?.setText(lastEmail)
            }
        } else {
            // If rememberMe false previously, no email shown
            binding.tlEmailLogin.editText?.setText("")
        }

        observeLoginState()

        binding.checkboxLogin.setOnCheckedChangeListener { _, isChecked ->
            rememberMeChecked = isChecked
        }

        binding.btnSignin.setOnClickListener {
            val email = binding.tlEmailLogin.editText?.text.toString().trim()
            val password = binding.tlPwLogin.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showError("Email or password cannot be empty")
                return@setOnClickListener
            }

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
                        binding.tvErrorPw.visibility = View.GONE
                    }
                    is LoginState.Loading -> {
                        binding.tvErrorPw.visibility = View.GONE
                    }
                    is LoginState.Success -> {
                        val user = state.user

                        // Selalu simpan session
                        saveSessionUseCase(user.userId, user.email, rememberMeChecked)

                        val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
                        val editor = prefs.edit()
                        editor.putString("loggedUserId", user.userId)

                        // Set rememberMe dan email sesuai pilihan
                        if (rememberMeChecked) {
                            editor.putBoolean("rememberMe", true)
                            editor.putString("loggedUserEmail", user.email)
                        } else {
                            editor.putBoolean("rememberMe", false)
                            editor.remove("loggedUserEmail")
                        }
                        editor.apply()

                        val intent = Intent(this@LoginWEmailActivity, DashboardActivity::class.java)
                        AnimationUtil.startActivityWithSlideAnimation(this@LoginWEmailActivity, intent)
                        finish()
                    }
                    is LoginState.Error -> {
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

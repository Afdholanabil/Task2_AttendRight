package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.datastore.SessionDataStore
import com.example.task2_attendright.data.local.db.AttendRightDatabase
import com.example.task2_attendright.data.local.db.user.UserEntity
import com.example.task2_attendright.data.repository.SessionRepositoryImpl
import com.example.task2_attendright.databinding.ActivityMainBinding
import com.example.task2_attendright.databinding.CustomToastBinding
import com.example.task2_attendright.domain.usecase.session.CheckSessionResult
import com.example.task2_attendright.domain.usecase.session.CheckSessionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val checkSessionUseCase: CheckSessionUseCase by lazy {
        val sessionRepo = SessionRepositoryImpl(SessionDataStore(applicationContext))
        // Session timeout 24 jam
        CheckSessionUseCase(sessionRepo, sessionTimeoutMillis = 24 * 60 * 60 * 1000)
    }

    private var _binding :ActivityMainBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        database = Room.databaseBuilder(
            applicationContext,
            AttendRightDatabase::class.java, "attendright_db"
        ).build()

        // Pre-populate user
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = database.userDao()
            val user1 = UserEntity(
                userId = "user123",
                name = "Ginanjar Putra",
                role = "UI/UX Developer",
                gender = "Male",
                birthDate = "1990-01-01",
                phone = "08123456789",
                religion = "Islam",
                email = "user@example.com",
                password = "password123"
            )

            val user2 = UserEntity(
                userId = "user456",
                name = "Andi Setiawan",
                role = "Android Developer",
                gender = "Male",
                birthDate = "1992-05-10",
                phone = "08123456890",
                religion = "Kristen",
                email = "andi@example.com",
                password = "passandi456"
            )

            userDao.insertUsers(user1, user2)
        }

        // Jalankan animasi lalu check session
        binding!!.tvTitleSlashScreen.alpha = 0f
        binding!!.tvTitleSlashScreen.animate().setDuration(3000).alpha(1f).withEndAction {
            lifecycleScope.launchWhenCreated {
                val result = checkSessionUseCase()
                when(result) {
                    is CheckSessionResult.SessionActive -> {
                        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is CheckSessionResult.SessionExpired -> {
                        showCustomToast("Session telah habis")
                        val intent = Intent(this@MainActivity, LoginWEmailActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    CheckSessionResult.NoSession -> {
                        val intent = Intent(this@MainActivity, LoginWEmailActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun showCustomToast(message: String) {
        applicationContext.let { ctx ->

            val toastBinding = CustomToastBinding.inflate(LayoutInflater.from(ctx))
            toastBinding.message.text = message

            val toast = Toast(ctx)
            toast.duration = Toast.LENGTH_LONG
            toast.view = toastBinding.root

            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        lateinit var database: AttendRightDatabase
            private set
    }
}

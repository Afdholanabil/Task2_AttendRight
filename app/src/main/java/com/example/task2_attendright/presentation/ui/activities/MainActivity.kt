package com.example.task2_attendright.presentation.ui.activities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.db.AttendRightDatabase
import com.example.task2_attendright.data.local.db.user.UserEntity
import com.example.task2_attendright.databinding.ActivityMainBinding
import com.example.task2_attendright.util.DailyCheckReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var _binding :ActivityMainBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        binding!!.tvTitleSlashScreen.alpha = 0f
        binding!!.tvTitleSlashScreen.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this, LoginWEmailActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
        database = Room.databaseBuilder(
            applicationContext,
            AttendRightDatabase::class.java, "attendright_db"
        ).build()

        // Pre-populate user
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = database.userDao()
            // Tambahkan beberapa user
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
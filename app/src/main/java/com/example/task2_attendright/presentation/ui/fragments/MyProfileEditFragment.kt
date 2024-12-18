package com.example.task2_attendright.presentation.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.Gender
import com.example.task2_attendright.data.repository.UserRepositoryImpl
import com.example.task2_attendright.databinding.CustomToastBinding
import com.example.task2_attendright.databinding.FragmentMyProfileEditBinding
import com.example.task2_attendright.presentation.ui.activities.MainActivity
import com.example.task2_attendright.presentation.ui.activities.MyProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class MyProfileEditFragment : Fragment() {
   private var _binding : FragmentMyProfileEditBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileEditBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    private fun loadUserData() {
        val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("loggedUserId", null)

        if (loggedUserId != null) {
            val userDao = MainActivity.database.userDao()
            val userRepository = UserRepositoryImpl(userDao)
            CoroutineScope(Dispatchers.Main).launch {
                val user = userRepository.getUserById(loggedUserId)
                if (user != null) {
                    binding!!.tlFullName.editText?.setText(user.name)
                    binding!!.actvPosition.setText(user.role, false) // Karena ExposedDropdownMenu
                    binding!!.actvGender.setText(user.gender, false) // Karena ExposedDropdownMenu

                    binding!!.tlBirthDate.editText?.setText(user.birthDate)
                    binding!!.tlPhone.editText?.setText(user.phone)
                    binding!!.actvReligion.setText(user.religion, false)
                }
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val positions = resources.getStringArray(R.array.positions_array)
        val positionsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, positions)
        binding!!.actvPosition.setAdapter(positionsAdapter)

        val religions = resources.getStringArray(R.array.religions_array)
        val religionsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, religions)
        binding!!.actvReligion.setAdapter(religionsAdapter)

        val gender = resources.getStringArray(R.array.gender_array)
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, gender)
        binding!!.actvGender.setAdapter(genderAdapter)


        ViewCompat.setOnApplyWindowInsetsListener(binding!!.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, imeInsets.bottom)

            insets
        }

        binding!!.tvCancel.setOnClickListener {
            (activity as MyProfileActivity).supportFragmentManager.popBackStack()
        }

        Glide.with(requireContext()).load(R.drawable.image_26).circleCrop().into(binding!!.ivProfileMyProfile)

        binding!!.btnSaveEditMyProfile.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val loggedUserId = prefs.getString("loggedUserId", null) ?: return@setOnClickListener

            val userDao = MainActivity.database.userDao()
            val userRepository = UserRepositoryImpl(userDao)

            // ambil data dari form
            val name = binding!!.tlFullName.editText?.text.toString()
            val role = binding!!.actvPosition.text.toString()
            val genderList = resources.getStringArray(R.array.gender_array)
            val selectedGender = binding!!.actvGender.text.toString()
            val birthDate = binding!!.tlBirthDate.editText?.text.toString()
            val phone = binding!!.tlPhone.editText?.text.toString()
            val religion = binding!!.actvReligion.text.toString()

            // Dapatkan user lama untuk ambil email & password (tidak diubah)
            CoroutineScope(Dispatchers.IO).launch {
                val oldUser = userRepository.getUserById(loggedUserId)
                if (oldUser != null) {
                    val updatedUser = oldUser.copy(
                        name = name,
                        role = role,
                        gender = selectedGender,
                        birthDate = birthDate,
                        phone = phone,
                        religion = religion
                        // email & password tetap sama
                    )

                    userRepository.updateUserProfile(updatedUser)

                    withContext(Dispatchers.Main) {
                        showCustomToast("Changes Saved!")
                        // kembali ke MyProfileViewFragment
                        (activity as MyProfileActivity).supportFragmentManager.popBackStack()
                    }
                }
            }
        }


        binding!!.tlBirthDate.editText?.setOnClickListener {
            showDatePickerDialog()
        }


    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Format yyyy-MM-dd
            val dateStr = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth)
            binding!!.tlBirthDate.editText?.setText(dateStr)
        }, year, month, day).show()
    }


    private fun showCustomToast(message: String) {
        context?.let { ctx ->

            val toastBinding = CustomToastBinding.inflate(LayoutInflater.from(ctx))
            toastBinding.message.text = message

            val toast = Toast(ctx)
            toast.duration = Toast.LENGTH_LONG
            toast.view = toastBinding.root

            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProfileEditFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
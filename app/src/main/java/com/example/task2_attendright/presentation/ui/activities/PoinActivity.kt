package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityPoinBinding
import com.example.task2_attendright.presentation.ui.adapter.HistoryMonthAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PoinActivity : AppCompatActivity() {
    private var _binding : ActivityPoinBinding? = null
    private val binding get() = _binding

    private var totalPoin : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityPoinBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.tvTotalPoin.text = totalPoin?.toString() ?: "-"

        val vp = binding!!.viewPagerContainer
        val tabLayout = binding!!.tabsMonthHistoryPoin
        vp.adapter = HistoryMonthAdapter(this)

        TabLayoutMediator(tabLayout, vp) { tab, position ->
            val tabView = LayoutInflater.from(this).inflate(R.layout.item_month_history_poin, null)
            val tabTitle = tabView.findViewById<TextView>(R.id.tv_title_month)
            val cardView = tabView.findViewById<MaterialCardView>(R.id.cv_month_history_poin_item)
            tabTitle.text = getMonth(position)
            tabTitle.setTextColor(resources.getColorStateList(R.color.text_month_selector, null))
            cardView.setCardBackgroundColor(resources.getColorStateList(R.color.card_month_selector, null))
            tab.customView = tabView
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    val tabTitle = it.findViewById<TextView>(R.id.tv_title_month)
                    val cardView = it.findViewById<MaterialCardView>(R.id.cv_month_history_poin_item)
                    tabTitle.isSelected = true
                    cardView.isSelected = true
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    val tabTitle = it.findViewById<TextView>(R.id.tv_title_month)
                    val cardView = it.findViewById<MaterialCardView>(R.id.cv_month_history_poin_item)
                    tabTitle.isSelected = false
                    cardView.isSelected = false
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        back()
    }

    private fun getMonth(position:Int) :String {
        val months = listOf( "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli","Agustus", "September", "Oktober", "November", "Desember")
        return months[position]
    }

    private fun back(){
        binding!!.tvBackPoin.setOnClickListener {
            finish()
        }
        binding!!.ivPoinPoin.setOnClickListener {
            finish()
        }
    }
}
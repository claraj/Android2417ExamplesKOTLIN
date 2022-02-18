package com.example.hydration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.DateFormatSymbols
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var waterViewPager: ViewPager2
    private lateinit var waterTabLayout: TabLayout

    private val waterViewModel: WaterViewModel by lazy {
        WaterViewModelFactory((application as HydrationApplication).repository).create(
            WaterViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waterViewModel.allRecords.observe(this) { records ->
            Log.d("MAIN_ACTIVITY", "$records")
        }

        val days = getWeekdays()
//        val days = resources.getStringArray(R.array.days)

        waterViewPager = findViewById(R.id.water_view_pager)
        waterTabLayout = findViewById(R.id.water_days_tab_layout)

        val pagerAdapter = WaterViewPagerAdapter(this, days)
        waterViewPager.adapter = pagerAdapter

        TabLayoutMediator(waterTabLayout, waterViewPager) { tab, position ->
            tab.text = days[position]
        }.attach()

        scrollToToday()
    }

    fun scrollToToday() {
        val today = Calendar.getInstance(Locale.getDefault())
        val day = today.get(Calendar.DAY_OF_WEEK)  // a number
        waterViewPager.setCurrentItem(day - 1, false)
    }

    private fun getWeekdays(): List<String> {
        val dateFormatSymbols = DateFormatSymbols.getInstance(Locale.getDefault())
        // dfs.weekdays is an 8 element array, first element is blank
        // { , Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday }
        // so the days can be numbered starting at 1.
        // Filter out the first blank day.
        return dateFormatSymbols.weekdays.asList().filter { it.isNotBlank() }
    }
}

package com.example.hydration

import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.Clock
import java.time.DayOfWeek
import java.time.LocalDate
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

        val days = resources.getStringArray(R.array.days).asList()

        waterViewPager = findViewById(R.id.water_view_pager)
        waterTabLayout = findViewById(R.id.water_days_tab_layout)

        val pagerAdapter = WaterViewPagerAdapter(this, days)
        waterViewPager.adapter = pagerAdapter

        TabLayoutMediator(waterTabLayout, waterViewPager) { tab, position ->
            tab.text = days[position]
        }.attach()

        // Scroll to today. Older versions of Android will have to start on Monday or write more code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val today = LocalDate.now()
            val day = DayOfWeek.from(today).value
            // days are numbered from Monday = 1 to Sunday = 7 so subtract 1 for ViewPager index
            // the ViewPager will notify the tabs so they will update too
            waterViewPager.setCurrentItem(day - 1, false)
        }
    }
}


//        waterViewModel.insertNewRecord(WaterRecord("Tuesday", 3))
//        waterViewModel.insertNewRecord(WaterRecord("Wednesday", 4))

//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.content, HydrationFragment.newInstance("Tuesday"))
//            .commit()






//
//    }
//}


//

//
//

//
//        /*
//        waterViewModel.allRecords.observe(this) { records ->
//            Log.d("MAIN_ACTIVITY", "$records")
//        }
//
//        waterViewModel.insertNewRecord(WaterRecord("Tuesday", 3))
//        */
//
//
////        supportFragmentManager
////            .beginTransaction()
////            .add(R.id.content, HydrationFragment.newInstance("Tuesday"))
////            .commit()
//    }
//}
//
//


//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val today = LocalDate.now()
//                  DayOfWeek.values().asList()
//            val day = DayOfWeek.from(today).value
////            // days are numbered from Monday = 1 to Sunday = 7
////            waterTabLayout.setScrollPosition(day - 1, 0f, true)
////            waterViewPager.setCurrentItem(day - 1, false)
//        }
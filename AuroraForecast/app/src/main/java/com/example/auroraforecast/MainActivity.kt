package com.example.auroraforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


private const val NUM_PAGES = 2

class MainActivity : AppCompatActivity() {

    private var reportListFragment: ReportListFragment = ReportListFragment.newInstance()
    private var chartFragment: ChartFragment = ChartFragment.newInstance()
    private lateinit var viewPager: ViewPager2

    // An inner class can access properties of the enclosing class.
    // A nested class (without the inner keyword), cannot.
    inner class TableChartPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                reportListFragment
            } else {
                chartFragment
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        val pagerAdapter = TableChartPagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }
}
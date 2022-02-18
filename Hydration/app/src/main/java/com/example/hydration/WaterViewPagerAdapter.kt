package com.example.hydration

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WaterViewPagerAdapter(activity: FragmentActivity, private val days: List<String>):
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return days.size
    }

    override fun createFragment(position: Int): Fragment {
        return HydrationFragment.newInstance(days[position])
    }
}


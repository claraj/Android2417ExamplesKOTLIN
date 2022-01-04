package com.example.auroraforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


private const val NUM_PAGES = 2

class MainActivity : AppCompatActivity() {

    private var recordListFragment: RecordListFragment = RecordListFragment.newInstance()
    private var chartFragment: ChartFragment = ChartFragment.newInstance()
    
    private lateinit var viewPager: ViewPager2

    // An inner class can access properties of the enclosing class.
    // A nested class (without the inner keyword), cannot.
    inner class TableChartPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int { return NUM_PAGES }

        override fun createFragment(position: Int): Fragment {
            if (position == 0) {
                return recordListFragment
            } else {
                return chartFragment
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        val pagerAdapter = TableChartPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        Notifications(this).createNotificationChannel()

        ForecastWorkRequestManager(this).start()

        ForecastApiRequest(this).requestAurora(
            { reportList ->
                recordListFragment.updateView(reportList)
                chartFragment.updateData(reportList)
            },
            { error ->
                showError("Unable to fetch aurora info")
            }
        )

        val stopButton: Button = findViewById(R.id.stop)
        stopButton.setOnClickListener {
            ForecastWorkRequestManager(this).stopAll()
        }
    }

    // a single-expression function
    private fun showError(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}
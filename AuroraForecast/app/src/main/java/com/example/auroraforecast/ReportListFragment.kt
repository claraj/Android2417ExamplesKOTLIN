package com.example.auroraforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val TAG = "REPORT LIST FRAGMENT"

class ReportListFragment : Fragment() {

    lateinit var reportsListRecycler: RecyclerView
    lateinit var reportList: List<Report>

    fun updateView(reportList: List<Report>) {

        this.reportList = reportList

        if (this::reportsListRecycler.isInitialized && this::reportList.isInitialized) {
            Log.d(TAG, "new list with elements count: ${reportList.size}")
            val recycler = ReportRecyclerAdapter(reportList)
            reportsListRecycler.adapter = recycler
        } else {
            Log.d(TAG, "view not initialized")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report_list, container, false)

        reportsListRecycler = view.findViewById(R.id.reports)
        reportsListRecycler.layoutManager = LinearLayoutManager(context)

        Log.d(TAG, "Recycler has been initialized")

        if (this::reportList.isInitialized) {
            updateView(reportList)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportListFragment()
    }
}
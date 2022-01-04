package com.example.auroraforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * A fragment representing a list of Items.
 */

private const val TAG = "RECORD LIST FRAGMENT"

class RecordListFragment : Fragment() {

    lateinit var reportsListRecycler: RecyclerView
    lateinit var reportList: List<Report>


    fun updateView(reportList: List<Report>) {

        this.reportList = reportList

        if (this::reportsListRecycler.isInitialized && this::reportList.isInitialized) {
            Toast.makeText(context, "update list", Toast.LENGTH_LONG).show()
            Log.d(TAG, "new list with elements count: ${reportList.size}")

            val recycler = ReportRecyclerAdapter(reportList)
            reportsListRecycler.adapter = recycler // ReportRecycler(reportList)
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
        val view = inflater.inflate(R.layout.fragment_record_list, container, false)

        reportsListRecycler = view.findViewById(R.id.reports)
        reportsListRecycler.layoutManager = LinearLayoutManager(context)
//        val tmp = listOf<Report>()
        val tmp = listOf(Report("2021-12-27 03:00:00", 2, "Observed"))

        //reportsListRecycler.adapter = ReportRecyclerAdapter(dataSet = tmp)

        Log.d(TAG, "Recycler has been initialized")

        if (this::reportList.isInitialized) {
            updateView(reportList)
        }

//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = ReportRecyclertmp(PlaceholderContent.ITEMS)
//            }
//        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecordListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
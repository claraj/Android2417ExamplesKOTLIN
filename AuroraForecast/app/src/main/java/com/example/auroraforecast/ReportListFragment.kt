package com.example.auroraforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

private const val TAG = "REPORT LIST FRAGMENT"

class ReportListFragment : Fragment() {

    private lateinit var reportsListRecycler: RecyclerView
    private lateinit var auroraViewModel: AuroraViewModel

    companion object {
        @JvmStatic
        fun newInstance() = ReportListFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report_list, container, false)

        reportsListRecycler = view.findViewById(R.id.reports)
        reportsListRecycler.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Once the view has been set up, configure the ViewModel to get data for the view.
        auroraViewModel = ViewModelProvider(this).get(AuroraViewModel::class.java)
        registerObserver()
    }

    private fun registerObserver() {

        val reportsObserver: Observer<ReportWrapper> = Observer { reportWrapper ->
            if (reportWrapper.reports != null) {
                updateList(reportWrapper.reports)}
            else if (reportWrapper.error != null) {
                ViewUtil.showError(requireContext(),"Error fetching aurora data")
            }
        }

        auroraViewModel.reports.observe(viewLifecycleOwner, reportsObserver)
    }

    private fun updateList(reportList: List<Report>) {

        if (this::reportsListRecycler.isInitialized) {
            Log.d(TAG, "New list with elements count: ${reportList.size}")
            val recycler = ReportRecyclerAdapter(reportList)
            reportsListRecycler.adapter = recycler
        } else {
            Log.d(TAG, "View not initialized")
        }
    }
}
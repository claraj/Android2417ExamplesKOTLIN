package com.example.auroraforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.graphics.vector.*

private const val TAG = "CHART FRAGMENT"

class ChartFragment : Fragment() {

    private lateinit var anyChartView: AnyChartView
    private lateinit var chartProgressBar: ProgressBar
    private val auroraViewModel: AuroraViewModel by lazy {
        ViewModelProvider(this).get(AuroraViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChartFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        // Locate the chart and progress bar in this view
        anyChartView = view.findViewById(R.id.chart) as AnyChartView
        chartProgressBar = view.findViewById(R.id.chart_progress)

        registerObserver()

        return view
    }


    private fun registerObserver() {

        val reportsObserver: Observer<ReportWrapper> = Observer { reportWrapper ->
            if (reportWrapper.reports != null) {
                drawChart(reportWrapper.reports)}
            else if (reportWrapper.error != null) {
                ViewUtil.showError(requireContext(),"Error fetching aurora data")
            }
        }

        auroraViewModel.reports.observe(viewLifecycleOwner, reportsObserver)

    }

    private fun drawChart(reports: List<Report>) {

        if (reports.isNotEmpty()) {

            // Create the chart. A column chart, AKA bar chart
            val columnChart = AnyChart.column()

            // Convert the list of reports into ValueDataEntry objects to be used by the chart library
            val reportData = reports.map { report ->
                ValueDataEntry(report.stringDate, report.kp)
            }

            // Assign data to the chart
            columnChart.data(reportData)

            // Label the x and y axis.  There can be many axes in a chart
            // so need to specify modifying the first x and first y axis.
            columnChart.xAxis(0).title("Date")
            columnChart.yAxis(0).title("KP")

            // KP typically ranges from 0 to 9 unless there's some wild storm
            // Set the chart to always have a max of 9 so the bars are always
            // relative to the maximum possible.
            columnChart.yScale().maximum(9)

            // Series is a set of data displayed on the chart. There's only one on this
            // chart, but a chart can potentially display many series. The series is
            // created from the reportDate generated earlier.
            val series = columnChart.getSeries(0)

            // Name the series, shown when clicking on a bar
            series.name("KP value")

            // Color the chart with a gradient green -> yellow -> red
            val height = anyChartView.height
            val width = anyChartView.width
            // The gradient area, todo
            val rect = Rect("anychart.math.rect(0, 0, 600, 600)")

            val gradientFill = LinearGradientFill(
                90,
                "['.1 green', '.4 yellow', '.7 red']",
                rect,
                1)

            series.color(gradientFill)

            // Connect the progress bar to the chart, so the progress bar - actually a spinner
            // is displayed while the chart is loading, and then is replaced with the chart
            // once the chart is ready.
            anyChartView.setProgressBar(chartProgressBar)

            // Set the chart view's chart.
            anyChartView.setChart(columnChart)

        }
    }
}
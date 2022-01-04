package com.example.auroraforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.graphics.vector.*
import com.anychart.palettes.RangeColors
import java.util.Collections.max

private const val TAG = "CHART FRAGMENT"

class ChartFragment : Fragment() {

    var reportList: List<Report> = listOf()
    lateinit var anyChartView: AnyChartView
    lateinit var chartProgressBar: ProgressBar
//    private lateinit var view: View

    fun drawChart() {

        if (reportList.isNotEmpty() && this::anyChartView.isInitialized) {
            val columnChart = AnyChart.column()

            val reportData = reportList.map { rep ->
                ValueDataEntry(rep.stringDate, rep.kp)
            }

            val maxKp = reportList.maxOf { rep -> rep.kp}
            Log.d(TAG, "Max KP is $maxKp")

            columnChart.data(reportData)

          //  val rangeColors = RangeColors("#446622")
//            columnChart.palette(rangeColors)

            val series = columnChart.getSeries(0)
//            val fill = SolidFill("440044", 1)

            val height = anyChartView.height
            val width = anyChartView.width
//            val rect = Rect("anychart.math.rect(5, 0, ${width}, ${height})")
            val rect = Rect("anychart.math.rect(0, 0, 600, 600)") // todo
            val gradientFill = LinearGradientFill(
                90,
                "['.1 green', '.4 yellow', '.7 red']",
                rect,
                1)

            series.color(gradientFill)


            val yMax = Math.max(9, maxKp )

            columnChart.yScale().maximum(yMax)

            anyChartView.setProgressBar(chartProgressBar)
            anyChartView.setChart(columnChart)

            Log.d(TAG, "Setting chart  data to ${reportData}")
        }

    }

    fun updateData(data: List<Report>){

        Log.d(TAG, "Update the chart data ${data.size}")
        this.reportList = data
        drawChart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView")
        val v = inflater.inflate(R.layout.fragment_chart, container, false)

         anyChartView = v.findViewById(R.id.chart) as AnyChartView
        chartProgressBar = v.findViewById(R.id.chart_progress)
        drawChart()
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
          * @return A new instance of fragment ChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ChartFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
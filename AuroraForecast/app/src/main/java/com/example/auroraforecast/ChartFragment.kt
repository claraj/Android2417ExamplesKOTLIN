package com.example.auroraforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.palettes.RangeColors

private const val TAG = "CHART FRAGMENT"

class ChartFragment : Fragment() {

    var reportList: List<Report> = listOf()
    lateinit var anyChartView: AnyChartView
//    private lateinit var view: View

    fun drawChart() {

        if (reportList.isNotEmpty() && this::anyChartView.isInitialized) {
            val columnChart = AnyChart.column()

            val reportData = reportList.map { rep ->
                ValueDataEntry(rep.stringDate, rep.kp)
            }


            columnChart.data(reportData)

          //  val rangeColors = RangeColors("#446622")
//            columnChart.palette(rangeColors)
            anyChartView.setChart(columnChart)

            Log.d(TAG, "SEtting chart  data to ${reportData}")
        }

    }

    fun updateData(data: List<Report>){

        Log.d(TAG, "Update the chart data ${data.size}")
        this.reportList = data
        drawChart()




     //   } else {
        //   Log.d(TAG, "No view yet!")
      //  }
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
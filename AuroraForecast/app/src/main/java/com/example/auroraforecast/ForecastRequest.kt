package com.example.auroraforecast

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError

import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ForecastRequest(context: Context) {

    private val url = "https://services.swpc.noaa.gov/products/noaa-planetary-k-index-forecast.json"
    private var queue: RequestQueue = Volley.newRequestQueue(context)
    private val TAG = "FORECAST REQUEST"

    fun requestAurora(success: (data: List<Report>) -> Unit, failure: (error: VolleyError) -> Unit ) {

        Log.d(TAG, "Making request")

        val jsonForecast = JsonArrayRequest(Request.Method.GET, url, null,
            {

                val reports: MutableList<Report> = mutableListOf()

//                The first item is a header, so skip and start at 1
                for (r in 1 until it.length()) {
                    val reportArray = it[r] as JSONArray
//                    Log.d(TAG, reportArray.toString())
                    val date = reportArray[0] as String
                    val kp =  reportArray[1] as String
                    val kpInt = kp.toInt()
                    val status = reportArray[2] as String
                    val report = Report(date, kpInt, status)
                    reports.add(report)
                }

            for (x in 0 until it.length() ) {
//                Log.d(TAG, it[x].toString())
            }

                success(reports)
            },

            {
                Log.e(TAG, it.toString())
                failure(it)

            })

        this.queue.add(jsonForecast)

    }
}
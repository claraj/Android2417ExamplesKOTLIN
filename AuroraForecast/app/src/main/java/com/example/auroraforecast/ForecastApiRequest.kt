package com.example.auroraforecast

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError

import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

private const val TAG = "FORECAST REQUEST"

class ForecastApiRequest(context: Context) {

    private val url = "https://services.swpc.noaa.gov/products/noaa-planetary-k-index-forecast.json"
    private var queue: RequestQueue = Volley.newRequestQueue(context)

    fun requestAurora(success: (data: List<Report>) -> Unit, failure: (error: VolleyError) -> Unit ) {

        Log.d(TAG, "Making request")

        val jsonForecast = JsonArrayRequest(Request.Method.GET, url, null,
            { jsonArray ->

                val reports: MutableList<Report> = mutableListOf()
                Log.d(TAG, "Got response, processing")

//                The first item is a header, so skip and start at 1
                for (r in 1 until jsonArray.length()) {
                    val reportArray = jsonArray[r] as JSONArray
//                    Log.d(TAG, reportArray.toString())
                    val date = reportArray[0] as String
                    val kp =  reportArray[1] as String
                    val kpInt = kp.toInt()
                    val status = reportArray[2] as String
                    val report = Report(date, kpInt, status)
                    reports.add(report)
                }

                Log.d(TAG, "Made request, returning list of reports")
                success(reports)
            },

            { error ->
                Log.e(TAG, "Error making request to API", error)
                failure(error)

            })

        this.queue.add(jsonForecast)

    }
}
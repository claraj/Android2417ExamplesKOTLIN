package com.example.auroraforecast


import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError

import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

private const val TAG = "FORECAST API REQUEST"

class ForecastApiRequest {

    private var queue = Volley.newRequestQueue(App.context)
    private val url = "https://services.swpc.noaa.gov/products/noaa-planetary-k-index-forecast.json"

    fun requestAurora(
        success: (data: List<Report>) -> Unit,
        failure: (error: VolleyError) -> Unit
    ) {

        Log.d(TAG, "Making request to NOAA API")

        val jsonForecast = JsonArrayRequest(Request.Method.GET, url, null,
            { jsonArray ->
                if (jsonArray.length() < 1) {
                    failure(VolleyError("No data returned from NOAA API"))
                } else {
                    try {
                        val reports = parseApiResponse(jsonArray)
                        success(reports)  // Don't forget to call the success function!
                    } catch (e: Exception) {  // Catch JSON processing errors, call failure function
                        Log.e(TAG, "Error processing JSON response", e)
                        failure(VolleyError("Error processing NOAA JSON because", e))
                    }
                }
            },

            { error ->
                Log.e(TAG, "Error making request to API because $error", error)
                // Log.e(Tag, msg, error) logs a stack trace for the error, with one exception,
                // UnknownHostException errors don't print stack traces, so include the error message in the log message.
                failure(error)
            })

        queue.add(jsonForecast)  // Don't forget!

    }

        private fun parseApiResponse(jsonArray: JSONArray): List<Report> {

            // Loop over JSON Array, extract date, KP and observed/estimated/predicted status
            // for each element.  The array elements are arrays themselves, in the format
            //
            // [ "2021-12-28 03:00:00",      <-- date and time, in UTC
            //   "3",                        <-- KP
            //   "observed",                 <-- status - Observed, estimated, predicted
            //   null                        <-- NOAA Scale, for reporting storms(?). Usually null. (https://www.swpc.noaa.gov/noaa-scales-explanation?)
            // ],

            val reports: MutableList<Report> = mutableListOf()

            // The first element is a header, so skip this and start at 1.
            for (r in 1 until jsonArray.length()) {
                val reportArray = jsonArray[r] as JSONArray
                val date = reportArray[0] as String
                val kp = reportArray[1] as String
                val kpInt = kp.toInt()
                val status = reportArray[2] as String

                // NOAA Scale is typically null unless there's a solar storm.
                // Assume null, if not, read the value as a String
                var noaaScale: String? = null
                if (reportArray[3] != JSONObject.NULL) {   // JSONObject.NULL used to represent null values in JSON. Comparing to null does not work.
                    noaaScale =
                        reportArray[3] as String   // TODO what does this field actually contain? There's no solar storm currently and can't find any info in NOAA's documentation.
                }
                val report = Report(date, kpInt, status, noaaScale)
                reports.add(report)
            }

            return reports
        }
    }
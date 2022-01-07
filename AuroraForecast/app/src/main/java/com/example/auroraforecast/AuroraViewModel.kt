package com.example.auroraforecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "AURORA VIEW MODEL"

data class ReportWrapper(val reports: List<Report>?, val error: Throwable?)

class AuroraViewModel: ViewModel() {

    // Uses a lazy initialization function. The lambda function
    // is invoked the first time the reports property is accessed.
    val reports: MutableLiveData<ReportWrapper> by lazy {
        // The also scope function is passed the new MutableLiveData as an
        // argument, and returns that MutableLiveData object.
        MutableLiveData<ReportWrapper>().also {
            fetchReports()
        }
    }

    private fun fetchReports() {
        ForecastApiRequest().requestAurora(
            { reports ->
                this.reports.postValue(ReportWrapper(reports, null))
            },
            { error ->
                this.reports.postValue(ReportWrapper(null, error))
            })
    }
}
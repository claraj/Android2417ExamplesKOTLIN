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
        // A convenient way to perform operations on an object.
        MutableLiveData<ReportWrapper>().also {
            fetchReports()
        }
    }


    // Compare to the version without also. The MutableLiveData must be initialized
    // before the fetchReports function is called, since fetchReports updates the
    // MutableLive data. This is the more "classic" version of the above.
//        val reports: MutableLiveData<ReportWrapper> by lazy {
//            val mutableLiveData =  MutableLiveData<ReportWrapper>()
//            fetchReports()
//            // If the inferred return type of the lambda is not Unit,
//            // the last (or possibly single) expression inside the lambda body
//            // is treated as the return value. https://kotlinlang.org/docs/lambdas.html#lambda-expression-syntax
//            mutableLiveData  // the return value
//        }



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
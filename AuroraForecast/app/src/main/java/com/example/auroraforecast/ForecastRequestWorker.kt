package com.example.auroraforecast
import android.content.Context
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import org.json.JSONArray

//class ForecastRequestWorker(val context: Context, workerParameters: WorkerParameters):
//    ListenableWorker(context, workerParameters) {
//
//        override fun startWork(): ListenableFuture<Result> {
//
//            return CallbackToFutureAdapter.getFuture( completer ->
//                val callback = object: Cal
//
//            // make request
//
////            ForecastRequest(context).requestAurora( {
////
////            }, {
////
////            } )
////
////            //return Result.success()
////return ListenableFuture
////        }


//}

class ForecastRequestWorker(val context: Context, workerParameters: WorkerParameters):
    Worker(context, workerParameters) {

    val TAG = "ForecastRequestWorker"


    override fun doWork():  Result {

        Log.d(TAG, "Do work starting")

            ForecastRequest(context).requestAurora( {

               Log.d(TAG, "The worker called the api successfully")
               notifyIfAuroraLikely(it)

            }, {
                Log.e(TAG, "There was an errors calling the API", it)
            } )

        return Result.success()
    }

    private fun notifyIfAuroraLikely(reports: List<Report>) {

        val futureReports = reports.filter { it.status != "observed" }
        val interestingReports = futureReports.filter { it.kp > 3 }


        if (interestingReports.isNotEmpty()) {

            val firstReport = interestingReports.first()

            val message = "On ${firstReport.date} the KP is ${firstReport.status} to be ${firstReport.kp}"
            Log.d(TAG, "An aurora seems likely, showing notification $message")
            Notifications(context).showNotification(message)
        }

        else {
            Log.d(TAG, "An aurora does not seem likely.")
        }
    }

}
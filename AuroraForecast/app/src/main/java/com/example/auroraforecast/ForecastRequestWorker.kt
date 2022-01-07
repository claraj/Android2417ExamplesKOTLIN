package com.example.auroraforecast
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val MIN_INTEREST_KP = 4
private const val TAG = "FORECAST REQUEST WORKER"

class ForecastRequestWorker(val context: Context, workerParameters: WorkerParameters):
    Worker(context, workerParameters) {

    override fun doWork():  Result {

        Log.d(TAG, "Do work starting")

            ForecastApiRequest().requestAurora(
                { reports ->
                    Log.d(TAG, "The worker called the api successfully")
                    notifyIfAuroraLikely(reports)
                },
                { error ->
                    Log.e(TAG, "There was an error calling the API", error)
                }
            )

        return Result.success() // TODO revisit this
        //  TODO replace this with a worker that can report success/failure for
        //        the asynchronous API call
    }

    private fun notifyIfAuroraLikely(reports: List<Report>) {

        // Filter out reports from the past
        val futureReports = reports.filter { it.status == "predicted" }

        // Filter out reports with low KP
        val interestingReports = futureReports.filter { it.kp > MIN_INTEREST_KP }

        Log.d(TAG, "${reports.size} reports")
        Log.d(TAG, "${futureReports.size} future reports")
        Log.d(TAG, "${interestingReports.size} interesting reports")

        if (interestingReports.isNotEmpty()) {
            val firstReport = interestingReports.first()
            val message = "On ${firstReport.stringDate} the KP is ${firstReport.status} to be ${firstReport.kp}"
            Log.d(TAG, "An aurora seems likely, showing notification $message")
            Notifications(context).showNotification(message)
        }

        else {
            Log.d(TAG, "Aurora does not seem likely. No notification will be sent.")
        }
    }

}
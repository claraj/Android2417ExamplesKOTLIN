package com.example.auroraforecast

import android.content.Context
import android.widget.Toast

object ViewUtil {

    // a single-expression function
    fun showError(context: Context, message: String) =
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

}
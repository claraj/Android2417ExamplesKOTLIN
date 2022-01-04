URL: https://services.swpc.noaa.gov/products/noaa-planetary-k-index-forecast.json

Notes on setting up the app

Add Volley to App's build.gradle   https://developer.android.com/training/volley
Add Internet permission to manifest

WorkManager to schedule tasks
https://developer.android.com/topic/libraries/architecture/workmanager

New string channel_name

https://www.flaticon.com/free-icon/aurora_721146

Aurora Icon from <div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

Add future thing
implementation "androidx.concurrent:concurrent-futures:1.1.0"
    // Kotlin
    implementation "androidx.concurrent:concurrent-futures-ktx:1.1.0"

Charts - add

  maven { url 'https://jitpack.io' }

to settings.gradle

Chart - write the XML for the chart page

Issues and TODOs

* use a listenable worker instead of a worker to handle success/failure
* fix chart gradient
* better message for user if failure contacting API

Kotlin co-routines?

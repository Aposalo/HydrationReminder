package com.example.android.background.sync

import android.app.IntentService
import android.content.Intent

/*https://developer.android.com/reference/kotlin/android/app/IntentService*/

/*
IntentService is subject to all the background execution limits imposed with Android 8.0 (API level 26).
Consider using androidx.work.WorkManager or androidx.core.app.JobIntentService, which uses jobs instead
of services when running on Android 8.0 or higher.
*/

public class WaterReminderIntentService() : IntentService("WaterReminderIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val action = intent!!.action
        ReminderTasks.executeTask(this, action!!)
    }
}

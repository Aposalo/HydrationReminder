package com.example.android.background.sync

import android.app.IntentService
import android.content.Intent

class WaterReminderIntentService : IntentService("WaterReminderIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val action = intent!!.action
        ReminderTasks.executeTask(this, action!!)
    }
}

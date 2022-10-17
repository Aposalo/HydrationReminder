package com.example.android.background.sync

import android.content.Context
import com.example.android.background.utilities.PreferenceUtilities

class ReminderTasks {

    companion object {
        const val ACTION_INCREMENT_WATER_COUNT:String = "increment-water-count"

        private fun incrementWaterCount(context: Context) {
            PreferenceUtilities.incrementWaterCount(context)
        }

        fun executeTask(context: Context, action: String) {
            if (action == ACTION_INCREMENT_WATER_COUNT)
                incrementWaterCount(context)
        }
    }
}
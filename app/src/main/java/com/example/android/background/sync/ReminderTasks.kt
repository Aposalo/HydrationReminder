package com.example.android.background.sync

import android.content.Context
import com.example.android.background.utilities.PreferenceUtilities

public class ReminderTasks {

    companion object {
        public const val ACTION_INCREMENT_WATER_COUNT:String = "increment-water-count";

        public fun incrementWaterCount(context: Context) {
            PreferenceUtilities.incrementWaterCount(context)
        }

        public fun executeTask(context: Context, action: String) {
            if (action == ACTION_INCREMENT_WATER_COUNT)
                incrementWaterCount(context)
        }
    }
}
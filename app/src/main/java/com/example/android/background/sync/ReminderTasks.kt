package com.example.android.background.sync

import android.content.Context
import com.example.android.background.utilities.NotificationUtils
import com.example.android.background.utilities.PreferenceUtilities

class ReminderTasks {

    companion object {

        const val ACTION_INCREMENT_WATER_COUNT: String = "increment-water-count"

        const val ACTION_DISMISS_NOTIFICATION = "dismiss-notification"

        private fun incrementWaterCount(context: Context) {
            PreferenceUtilities.incrementWaterCount(context)
            NotificationUtils.clearAllNotifications(context)
        }

        fun executeTask(context: Context, action: String) {

            when (action) {
                ACTION_INCREMENT_WATER_COUNT -> incrementWaterCount(context)
                ACTION_DISMISS_NOTIFICATION -> NotificationUtils.clearAllNotifications(context)
            }
        }
    }
}
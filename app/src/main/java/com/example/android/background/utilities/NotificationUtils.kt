package com.example.android.background.utilities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.android.background.MainActivity
import com.example.android.background.R
import com.example.android.background.sync.ReminderTasks
import com.example.android.background.sync.WaterReminderIntentService

/**
 * Utility class for creating hydration notifications
 */
class NotificationUtils {

    companion object {

        private const val WATER_REMINDER_NOTIFICATION_ID = 1138

        private const val WATER_REMINDER_PENDING_INTENT_ID = 3417

        private const val WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel"

        private const val ACTION_IGNORE_PENDING_INTENT_ID = 14

        private const val ACTION_DRINK_PENDING_INTENT_ID = 1

        fun clearAllNotifications(context: Context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        @JvmStatic
        fun remindUserBecauseCharging(context: Context) {

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mChannel = NotificationChannel(
                    WATER_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(mChannel)
            }

            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_drink_notification)
                    .setLargeIcon(largeIcon(context))
                    .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                    .setContentText(context.getString(R.string.charging_reminder_notification_body))
                    .setStyle(
                        NotificationCompat.BigTextStyle().bigText(
                            context.getString(R.string.charging_reminder_notification_body)
                        )
                    )
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .addAction(drinkWaterAction(context))
                    .addAction(ignoreReminderAction(context))
                    .setContentIntent(contentIntent(context))
                    .setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH

            notificationManager.notify(
                WATER_REMINDER_NOTIFICATION_ID,
                notificationBuilder.build()
            )
        }

        private fun contentIntent(context: Context) : PendingIntent {
            val startActivityIntent = Intent(context, MainActivity::class.java)

            return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        private fun largeIcon(context: Context): Bitmap? {
            val res = context.resources
            return BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px)
        }

        private fun ignoreReminderAction(context: Context): NotificationCompat.Action {

            val ignoreReminderIntent =
                Intent(context, WaterReminderIntentService::class.java)

            ignoreReminderIntent.action = ReminderTasks.ACTION_DISMISS_NOTIFICATION

            val ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Action(
                R.drawable.ic_cancel_black_24px,
                "No, thanks.",
                ignoreReminderPendingIntent
            )
        }

        private fun drinkWaterAction(context: Context): NotificationCompat.Action {

            val incrementWaterCountIntent =
                Intent(context, WaterReminderIntentService::class.java)

            incrementWaterCountIntent.action = ReminderTasks.ACTION_INCREMENT_WATER_COUNT

            val incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Action(
                R.drawable.ic_local_drink_black_24px,
                "I did it!",
                incrementWaterPendingIntent
            )
        }

    }
}
package com.example.healthylifestyleapp.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.UserProfileActivity


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION = "alarm.action"

        enum class Action {
            SLEEP, WATER, FOOD
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {
            if (intent.hasExtra(ACTION) && intent.getStringExtra(ACTION) != null) {
                val notificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val builder = NotificationCompat.Builder(context, "111")
                    .setSmallIcon(R.drawable.ic_notification)
                    //example for large icon
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.ic_notification
                        )
                    )
                    .setContentTitle("Reminder")
                    .setContentText(intent.getStringExtra(ACTION))
                    .setOngoing(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                val i = Intent(context, UserProfileActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    i,
                    PendingIntent.FLAG_ONE_SHOT
                )
                // example for blinking LED
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelId = "111"
                    val channel = NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    NotificationManagerCompat.from(context).createNotificationChannel(channel)
                }
                builder.setContentIntent(pendingIntent)
//                notificationManager.notify(12345, builder.build())
                NotificationManagerCompat.from(context).notify(12345, builder.build())
            }
        } else {

        }
    }
}
package com.application.flickr.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.application.flickr.R
import com.application.flickr.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Harsh Jain on 22/04/19.
 */

class FireBaseMessagingService : FirebaseMessagingService() {
    private var mSetUp = false
    private lateinit var notificationManager: NotificationManager

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!mSetUp) {
                setUp(applicationContext)
                mSetUp = true
            }
        }

        if (message != null) {
            val pushPayload = message.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                postNotification(applicationContext, pushPayload)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun postNotification(context: Context, message: MutableMap<String, String>) {
        val builder = Notification.Builder(context)

        builder.setContentTitle("${message["gcm_title"]}")
            .setContentText("${message["gcm_alert"]}")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setCategory(Notification.CATEGORY_STATUS)
            .setGroup("Flickr")
            .setSortKey("1")
            .setChannelId(LEVEL[3])
            .setColor(ContextCompat.getColor(context, android.R.color.white))
            .setAutoCancel(true)

        val intent: Intent = MainActivity.notificationIntent(this, message["search_term"]!!)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        builder.setContentIntent(pendingIntent)

        notificationManager.notify(0, builder.build());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUp(context: Context) {
        val lowChannel = NotificationChannel(
            LEVEL[0],
            "low level notification", IMPORTANCE_LEVEL[0]
        )
        val defaultChannel = NotificationChannel(
            LEVEL[1],
            "default level notification", IMPORTANCE_LEVEL[1]
        )
        val highChannel = NotificationChannel(
            LEVEL[2],
            "high level notification", IMPORTANCE_LEVEL[2]
        )
        val maxChannel = NotificationChannel(
            LEVEL[3],
            "max level notification", IMPORTANCE_LEVEL[3]
        )

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(lowChannel)
        notificationManager.createNotificationChannel(defaultChannel)
        notificationManager.createNotificationChannel(highChannel)
        notificationManager.createNotificationChannel(maxChannel)
    }

    companion object {
        private val TAG = "FireBaseMessagingService"
        private val DEBUG = true

        private val PATH = "notifications"
        val LEVEL = arrayOf("low", "default", "high", "max")
        val IMPORTANCE_LEVEL = intArrayOf(
            NotificationManager.IMPORTANCE_LOW,
            NotificationManager.IMPORTANCE_DEFAULT,
            NotificationManager.IMPORTANCE_HIGH,
            NotificationManager.IMPORTANCE_MAX
        )
    }
}
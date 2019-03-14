package com.prince.smsreader.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.prince.smsreader.R
import com.prince.smsreader.ui.SMSActivity
import javax.inject.Inject
import javax.inject.Named

class NotificationHelper @Inject constructor(@Named("Application Context") private val context: Context) {

    private var mNotificationManager: NotificationManager? = null
    private var mBuilder: NotificationCompat.Builder? = null

    fun createNotification(sender: String, message: String, time: String) {
        val intent = Intent(context, SMSActivity::class.java)
        intent.putExtra("message_time", time)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        mBuilder = NotificationCompat.Builder(context)
        mBuilder?.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder?.apply {
            setContentTitle(sender)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)
        }

        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "SMS_NOTIFICATION", importance)
            mBuilder?.setChannelId(CHANNEL_ID)
            mNotificationManager?.createNotificationChannel(notificationChannel)
        }
        mNotificationManager?.notify(0, mBuilder?.build())
    }

    companion object {
        private val CHANNEL_ID = "101"
    }
}
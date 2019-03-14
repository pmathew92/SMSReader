package com.prince.smsreader.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.prince.smsreader.notification.NotificationHelper
import dagger.android.AndroidInjection
import javax.inject.Inject


class SMSReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)

        val data = intent?.getExtras()
        val pdus = data!!.get("pdus") as Array<*>
        for (i in pdus.indices) {
            val smsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val sender = smsMessage.displayOriginatingAddress
            val time = smsMessage.timestampMillis.toString()
            val messageBody = smsMessage.messageBody
            try {
                messageBody?.let {
                    notificationHelper.createNotification(sender, it, time)
                }

            } catch (e: Exception) {
                Log.e("SMSReceiver", "Exception reading message " + e.localizedMessage);
            }

        }
    }
}
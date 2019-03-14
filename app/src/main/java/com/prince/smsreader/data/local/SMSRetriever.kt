package com.prince.smsreader.data.local

import android.content.Context
import android.provider.Telephony
import android.util.Log
import com.prince.smsreader.model.Messages
import com.prince.smsreader.model.SMSMessages
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList


class SMSRetriever @Inject constructor(@Named("Application Context") val context: Context) {


    fun getAllSms(smsTime: String?): List<SMSMessages> {
        val date = Date(System.currentTimeMillis() - 2 * 24 * 3600 * 1000).time
        val calendar = Calendar.getInstance()
        val current = Calendar.getInstance()

        val zerohrList = ArrayList<Messages>()
        val onehrList = ArrayList<Messages>()
        val twohrList = ArrayList<Messages>()
        val threehrList = ArrayList<Messages>()
        val sixhrList = ArrayList<Messages>()
        val twelvehrList = ArrayList<Messages>()
        val onedayList = ArrayList<Messages>()

        val smsList = ArrayList<SMSMessages>()

        val contentResolver = context.contentResolver

        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms._ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.DATE_SENT
            ),
            Telephony.Sms.DATE + ">?",
            arrayOf(date.toString()),
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        )

        val totalSMS = cursor?.count ?: 0

        var smsId: String?
        var smsDate: String?
        var number: String?
        var body: String?
        var smsSentDate: String?

        if (totalSMS > 0) {
            while (cursor != null && cursor.moveToNext()) {
                smsId = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms._ID))
                smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                smsSentDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE_SENT))
                number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))

                calendar.timeInMillis = smsDate.toLong()

                if (current.timeInMillis - calendar.timeInMillis < HOUR_MILLIS) {
                    Log.d("Retriever", "SMS time" + smsTime)
                    Log.d("Retriever", "SMS Date" + smsSentDate)
                    if (smsSentDate.equals(smsTime)) {
                        zerohrList.add(Messages(smsId, smsDate, number, body, true))
                    } else {
                        zerohrList.add(Messages(smsId, smsDate, number, body))
                    }

                } else if (current.timeInMillis - calendar.timeInMillis < 2 * HOUR_MILLIS) {
                    onehrList.add(Messages(smsId, smsDate, number, body))
                } else if (current.timeInMillis - calendar.timeInMillis < 3 * HOUR_MILLIS) {
                    twohrList.add(Messages(smsId, smsDate, number, body))
                } else if (current.timeInMillis - calendar.timeInMillis < 6 * HOUR_MILLIS) {
                    threehrList.add(Messages(smsId, smsDate, number, body))
                } else if (current.timeInMillis - calendar.timeInMillis < 12 * HOUR_MILLIS) {
                    sixhrList.add(Messages(smsId, smsDate, number, body))
                } else if (current.timeInMillis - calendar.timeInMillis < 24 * HOUR_MILLIS) {
                    twelvehrList.add(Messages(smsId, smsDate, number, body))
                } else {
                    onedayList.add(Messages(smsId, smsDate, number, body))
                }
            }

            if (!zerohrList.isEmpty()) smsList.add(SMSMessages("0 hours ago", zerohrList))
            if (!onehrList.isEmpty()) smsList.add(SMSMessages("1 hours ago", onehrList))
            if (!twohrList.isEmpty()) smsList.add(SMSMessages("2 hours ago", twohrList))
            if (!threehrList.isEmpty()) smsList.add(SMSMessages("3 hours ago", threehrList))
            if (!sixhrList.isEmpty()) smsList.add(SMSMessages("6 hours ago", sixhrList))
            if (!twelvehrList.isEmpty()) smsList.add(SMSMessages("12 hours ago", twelvehrList))
            if (!onedayList.isEmpty()) smsList.add(SMSMessages("1 day ago", onedayList))
        }

        cursor?.close()
        return smsList
    }


    companion object {
        val HOUR_MILLIS = 3600000
    }
}
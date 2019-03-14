package com.prince.smsreader.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prince.smsreader.data.local.SMSRetriever
import com.prince.smsreader.model.SMSMessages
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SMSRepository @Inject constructor(private val retriever: SMSRetriever) {
    var smsMessages: MutableLiveData<List<SMSMessages>> = MutableLiveData()

    fun getInboxMessages(smsTime: String?): LiveData<List<SMSMessages>> {
        smsMessages.value = retriever.getAllSms(smsTime)
        return smsMessages
    }
}
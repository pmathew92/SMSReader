package com.prince.smsreader.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.prince.smsreader.data.repository.SMSRepository
import com.prince.smsreader.model.SMSMessages

class SMSViewModel(private val repository: SMSRepository) : ViewModel() {

    val messageTime: MutableLiveData<String> = MutableLiveData()

    val messageList = Transformations.switchMap(messageTime) {
        repository.getInboxMessages(it)
    }

    fun getSMSMessages(): LiveData<List<SMSMessages>> {
        return messageList
    }


    fun setMessageTime(time: String?) {
        messageTime.value = time
    }
}
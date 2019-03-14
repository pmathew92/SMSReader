package com.prince.smsreader.ui

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.smsreader.data.repository.SMSRepository
import javax.inject.Inject

class SMSViewModelFactory @Inject constructor(private val repository: SMSRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @NonNull
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //noinspection unchecked
        return SMSViewModel(repository) as T
    }
}
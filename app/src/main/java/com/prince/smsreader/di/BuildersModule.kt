package com.prince.smsreader.di

import com.prince.smsreader.receivers.SMSReceiver
import com.prince.smsreader.ui.SMSActivity
import com.prince.smsreader.ui.SMSActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [SMSActivityModule::class])
    abstract fun bindSMSActivity(): SMSActivity


    @ContributesAndroidInjector()
    abstract fun bindSMSReceiver(): SMSReceiver
}
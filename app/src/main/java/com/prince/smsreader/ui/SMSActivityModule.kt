package com.prince.smsreader.ui

import dagger.Module
import dagger.Provides

@Module
class SMSActivityModule {

    @Provides
    fun provideUserAdapter(context: SMSActivity): SMSAdapter {
        return SMSAdapter(context)
    }
}
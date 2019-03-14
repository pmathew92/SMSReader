package com.prince.smsreader.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @Named("Application Context")
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }
}
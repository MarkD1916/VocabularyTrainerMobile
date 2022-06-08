package com.example.vocabularytrainer

import android.app.Application
import com.androiddevs.ktornoteapp.data.remote.interceptors.NetworkMonitor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VocabularyTrainerApp:Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkMonitor(this).startNetworkCallback()
    }

    override fun onTerminate() {
        super.onTerminate()
        NetworkMonitor(this).stopNetworkCallback()
    }
}
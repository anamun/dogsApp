package com.example.dogsapp.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class DogsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
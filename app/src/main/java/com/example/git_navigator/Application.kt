package com.example.git_navigator

import dagger.hilt.android.HiltAndroidApp
import android.app.Application

@HiltAndroidApp
open class Application: Application(){
    override fun onCreate() {
        super.onCreate()

    }
}
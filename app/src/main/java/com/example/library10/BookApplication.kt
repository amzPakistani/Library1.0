package com.example.library10

import android.app.Application
import com.example.library10.data.AppContainer
import com.example.library10.data.DefaultAppContainer

class BookApplication:Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
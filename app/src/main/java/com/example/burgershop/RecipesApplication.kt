package com.example.burgershop

import android.app.Application
import com.example.burgershop.di.AppContainer

class RecipesApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
package com.example.burgershop

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {
    var executorService: ExecutorService = Executors.newFixedThreadPool(4)
}
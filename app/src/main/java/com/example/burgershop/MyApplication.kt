package com.example.burgershop

import android.app.Application
import java.util.concurrent.Executors

class MyApplication : Application() {
    var executorService = Executors.newFixedThreadPool(4)
}
package ru.vs.control

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        println(HelloProvider.getHello())
    }
}
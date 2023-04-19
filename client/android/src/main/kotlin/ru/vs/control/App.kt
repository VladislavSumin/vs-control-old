package ru.vs.control

import android.app.Application
import android.util.Log
import org.kodein.di.DI
import org.kodein.di.DIAware

class App : Application(), DIAware {
    override val di: DI = DI.lazy { }

    override fun onCreate() {
        super.onCreate()
        Log.d("AAAAAA", "onCreate: ${HelloProvider.getHello()}")
    }
}

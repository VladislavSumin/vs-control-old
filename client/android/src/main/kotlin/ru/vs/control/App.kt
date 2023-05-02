package ru.vs.control

import android.app.Application
import android.content.Context
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules

class App : Application(), DIAware {
    override val di: DI = DI.lazy {
        bindSingleton<Context> { this@App }
        importOnce(Modules.clientCommon())
    }
}

package ru.vs.control.domain

import ru.vs.control.BuildConfig
import ru.vs.core.analytic.sentry.SentryInitializer

interface SentryInteractor {
    fun init()
}

class SentryInteractorImpl(
    private val sentryInitializer: SentryInitializer,
) : SentryInteractor {
    override fun init() {
        sentryInitializer.init(
            dsn = BuildConfig.sentryToken
        )
    }
}

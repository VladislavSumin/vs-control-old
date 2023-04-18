package ru.vs.control.domain

import ru.vs.control.BuildConfig


/**
 * Provides common information about server
 */
interface AboutServerInteractor {
    /**
     * Get current server version
     * @return - version
     */
    suspend fun getVersion(): String
}

class AboutServerInteractorImpl : AboutServerInteractor {
    override suspend fun getVersion(): String = BuildConfig.version
}

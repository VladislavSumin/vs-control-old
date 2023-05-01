package ru.vs.rsub

/**
 * Creates [RSubConnection]
 */
interface RSubConnector {
    suspend fun connect(): RSubConnection
}

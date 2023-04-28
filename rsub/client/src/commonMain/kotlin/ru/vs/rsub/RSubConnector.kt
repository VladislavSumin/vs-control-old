package ru.vs.rsub

interface RSubConnector {
    suspend fun connect(): RSubConnection
}

package ru.vs.control.repository

import org.kodein.di.DirectDI

internal interface DatabaseFactory {
    fun createDatabase(): Database
}

internal expect fun DirectDI.createDatabaseFactory(): DatabaseFactory

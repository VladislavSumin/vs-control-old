package ru.vs.control.repository

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.kodein.di.DirectDI
import ru.vs.core.di.i

private class AndroidDatabaseFactoryImpl(private val context: Context) : DatabaseFactory {
    override fun createDatabase(): Database {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "database.db")
        return Database(driver)
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseFactory {
    return AndroidDatabaseFactoryImpl(i())
}

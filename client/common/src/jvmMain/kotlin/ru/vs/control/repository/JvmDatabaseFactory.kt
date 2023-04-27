package ru.vs.control.repository

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.kodein.di.DirectDI

private class JvmDatabaseFactoryImpl : DatabaseFactory {
    override fun createDatabase(): Database {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        return Database(driver)
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseFactory {
    return JvmDatabaseFactoryImpl()
}

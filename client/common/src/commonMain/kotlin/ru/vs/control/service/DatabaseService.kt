package ru.vs.control.service

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import ru.vs.control.repository.Database
import ru.vs.control.servers.repository.ServerRecordQueries
import ru.vs.control.servers.service.ServerQueriesProvider
import ru.vs.core.database.AbstractDatabaseService
import ru.vs.core.database.DatabaseDriverFactory

class DatabaseService(factory: DatabaseDriverFactory) :
    AbstractDatabaseService<Database>(factory),
    ServerQueriesProvider {

    override val schema: SqlSchema = Database.Schema
    override fun createDatabaseFromDriver(driver: SqlDriver): Database = Database(driver)

    override suspend fun getServerQueries(): ServerRecordQueries = getDatabase().serverRecordQueries
}

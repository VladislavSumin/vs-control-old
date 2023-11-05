package ru.vs.control.services.domain

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.coroutineScope
import ru.vs.control.id.Id

/**
 * Service is "plugin" for server.
 */
interface Service {
    /**
     * Service id, must be unique.
     * Use this id as first part of entity composite id
     */
    val id: Id

    /**
     * Run all service tasks here, when caller coroutine scope will cancel - stop any service operations
     * You may use [coroutineScope] to combine internal jobs.
     * You may use [NonCancellable] context to gracefully complete internal jobs
     */
    suspend fun run()
}

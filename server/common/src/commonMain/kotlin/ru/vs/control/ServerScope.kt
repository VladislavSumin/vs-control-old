package ru.vs.control

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class ServerScope(
    coroutineContext: CoroutineContext = CoroutineName("server-scope"),
) : CoroutineScope by CoroutineScope(coroutineContext) {

    /**
     * Block caller thread until scope will cancelled
     */
    fun blockingAwait() = runBlocking {
        this@ServerScope.coroutineContext.job.join()
    }
}

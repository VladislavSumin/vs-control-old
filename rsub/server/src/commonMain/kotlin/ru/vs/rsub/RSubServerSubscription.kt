package ru.vs.rsub

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KType
import kotlin.reflect.typeOf

sealed interface RSubServerSubscription {
    val type: KType

    interface SuspendSub<T> : RSubServerSubscription {
        suspend fun get(): T
    }

    interface FlowSub<T> : RSubServerSubscription {
        fun get(): Flow<T>
    }

    companion object {
        inline fun <reified T> createSuspend(crossinline method: suspend () -> T): SuspendSub<T> {
            return object : SuspendSub<T> {
                override val type: KType = typeOf<T>()

                override suspend fun get(): T {
                    return method()
                }
            }
        }

        inline fun <reified T> createFlow(crossinline flow: () -> Flow<T>): FlowSub<T> {
            return object : FlowSub<T> {
                override val type: KType = typeOf<T>()

                override fun get(): Flow<T> {
                    return flow()
                }
            }
        }
    }
}

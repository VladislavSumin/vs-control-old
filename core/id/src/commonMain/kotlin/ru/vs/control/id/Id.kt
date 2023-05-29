package ru.vs.control.id

import kotlinx.serialization.Serializable

@Suppress("SERIALIZER_TYPE_INCOMPATIBLE")
@Serializable(IdSerializer::class)
sealed interface Id {
    val rawId: String

    @Serializable(IdSerializer::class)
    interface SimpleId : Id {
        companion object {
            operator fun invoke(rawId: String): SimpleId = SimpleIdImpl(rawId)
        }
    }

    @Serializable(IdSerializer::class)
    interface CompositeId : Id {
        val parts: List<SimpleId>
    }

    @Serializable(IdSerializer::class)
    interface DoubleId : CompositeId {
        val firstPart: SimpleId
        val secondPart: SimpleId

        companion object {
            operator fun invoke(firstPart: SimpleId, secondPart: SimpleId): DoubleId =
                DoubleIdImpl(firstPart, secondPart)
        }
    }

    companion object {
        operator fun invoke(rawId: String): Id = IdFactory.createId(rawId)
        operator fun invoke(vararg ids: Id): Id = IdFactory.createId(ids.asIterable())
    }
}

package ru.vs.control.id

import kotlinx.serialization.Serializable

@Serializable(CompositeIdSerializer::class)
interface CompositeId : Comparable<CompositeId> {
    val firstPart: Id
    val secondPart: Id

    val rawId: String

    companion object {
        operator fun invoke(firstPart: Id, secondPart: Id): CompositeId = CompositeIdImpl(firstPart, secondPart)
        operator fun invoke(rawCompositeId: String): CompositeId = CompositeIdImpl.fromRawCompositeId(rawCompositeId)
    }
}

internal class CompositeIdImpl(
    override val firstPart: Id,
    override val secondPart: Id
) : CompositeId {

    override val rawId: String = "${firstPart.rawId}#${secondPart.rawId}"

    override fun compareTo(other: CompositeId): Int {
        return rawId.compareTo(other.rawId)
    }

    override fun toString(): String {
        return "CompositeId($rawId)"
    }

    companion object {
        fun fromRawCompositeId(rawCompositeId: String): CompositeId {
            val rawIds = rawCompositeId.split("#", limit = 2)
            check(rawIds.size == 2) { "Incorrect rawCompositeId=$rawCompositeId" }
            val (firstPart, secondPart) = rawIds.map { Id(it) }
            return CompositeIdImpl(firstPart, secondPart)
        }
    }
}

package ru.vs.control.id

internal class DoubleIdImpl(
    override val firstPart: Id.SimpleId,
    override val secondPart: Id.SimpleId
) : Id.DoubleId {
    override val rawId: String by lazy { "${firstPart.rawId}#${secondPart.rawId}" }
    override val parts: List<Id.SimpleId> by lazy { listOf(firstPart, secondPart) }
}

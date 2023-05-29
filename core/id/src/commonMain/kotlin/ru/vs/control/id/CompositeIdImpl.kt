package ru.vs.control.id

internal class CompositeIdImpl(override val parts: List<Id.SimpleId>) : Id.CompositeId {
    init {
        // We use SimpleId for one part and DoubleId for two parts
        check(parts.size > 2) { "Incorrect parts count" }
    }

    override val rawId: String by lazy { parts.joinToString(separator = "#") { it.rawId } }
}

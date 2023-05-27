package ru.vs.control.id

import kotlinx.serialization.Serializable

@Serializable(IdSerializer::class)
interface Id {
    val rawId: String

    companion object {
        operator fun invoke(rawId: String): Id = IdImpl(rawId)
    }
}

internal class IdImpl(override val rawId: String) : Id {

    init {
        check(ID_VERIFICATION_REGEXP.matches(rawId)) { "Incorrect id format" }
    }

    override fun toString(): String {
        return "Id($rawId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as IdImpl

        return rawId == other.rawId
    }

    override fun hashCode(): Int {
        return rawId.hashCode()
    }

    companion object {
        private const val ID_PART_VERIFICATION_REGEXP = "([a-z0-9]+(_[a-z0-9]+)*)"
        private val ID_VERIFICATION_REGEXP =
            "^$ID_PART_VERIFICATION_REGEXP(/$ID_PART_VERIFICATION_REGEXP.)*\$".toRegex()
    }
}

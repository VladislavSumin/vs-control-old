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

    companion object {
        private const val ID_PART_VERIFICATION_REGEXP = "([a-z]+(_[a-z]+)*)"
        private val ID_VERIFICATION_REGEXP =
            "^$ID_PART_VERIFICATION_REGEXP(/$ID_PART_VERIFICATION_REGEXP.)*\$".toRegex()
    }
}

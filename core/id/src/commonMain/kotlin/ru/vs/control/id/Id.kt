package ru.vs.control.id

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(IdSerializer::class)
interface Id {
    val rawId: String

    companion object {
        operator fun invoke(rawId: String): Id = IdImpl(rawId)
    }
}

internal class IdImpl(override val rawId: String) : Id

internal object IdSerializer : KSerializer<Id> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("id", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Id {
        val rawId = decoder.decodeString()
        return Id(rawId)
    }

    override fun serialize(encoder: Encoder, value: Id) {
        encoder.encodeString(value.rawId)
    }
}

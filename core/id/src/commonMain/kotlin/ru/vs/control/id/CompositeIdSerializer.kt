package ru.vs.control.id

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object CompositeIdSerializer : KSerializer<CompositeId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "ru.vs.control.id.CompositeId", PrimitiveKind.STRING
    )

    override fun deserialize(decoder: Decoder): CompositeId {
        val rawId = decoder.decodeString()
        return CompositeId(rawId)
    }

    override fun serialize(encoder: Encoder, value: CompositeId) {
        encoder.encodeString(value.rawId)
    }
}

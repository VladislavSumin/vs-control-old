package ru.vs.rsub

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed class RSubMessage {
    abstract val id: Int

    @Serializable
    @SerialName("subscribe")
    data class Subscribe(override val id: Int, val interfaceName: String, val functionName: String) : RSubMessage()

    @Serializable
    @SerialName("unsubscribe")
    data class Unsubscribe(override val id: Int) : RSubMessage()

    @Serializable
    @SerialName("data")
    data class Data(override val id: Int, val data: JsonElement) : RSubMessage()

    @Serializable
    @SerialName("flow_complete")
    data class FlowComplete(override val id: Int) : RSubMessage()

    @Serializable
    @SerialName("error")
    data class Error(override val id: Int) : RSubMessage()
}

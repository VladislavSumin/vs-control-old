package ru.vs.control.about_server.rsub

import kotlinx.serialization.Serializable

@Serializable
data class ServerInfoDto(val version: String)

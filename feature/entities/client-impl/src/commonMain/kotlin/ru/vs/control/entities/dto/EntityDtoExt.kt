package ru.vs.control.entities.dto

import ru.vs.control.entities.domain.Entity
import ru.vs.control.servers.domain.Server

fun EntityDto.toEntity(server: Server) = Entity(server, id, primaryState)
fun Collection<EntityDto>.toEntity(server: Server) = map { it.toEntity(server) }

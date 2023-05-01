package ru.vs.control.about_server.rsub

import ru.vs.rsub.RSubInterface

@RSubInterface
interface AboutServerRSub {
    suspend fun getServerInfo(): ServerInfoDto
}

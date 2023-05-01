package ru.vs.control.about_server.rsub

internal class AboutServerRSubImpl : AboutServerRSub {
    override suspend fun getServerInfo(): ServerInfoDto {
        return ServerInfoDto("1.2.3")
    }
}

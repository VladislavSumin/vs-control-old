package ru.vs.control.servers_connection.rsub

import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.control.service_cams_netsurv.rsub.NetsurvCamsRsub
import ru.vs.rsub.RSubClient

@RSubClient
internal interface ServerRSubClient {
    val aboutServer: AboutServerRSub
    val entities: EntitiesRsub
    val netsurvCams: NetsurvCamsRsub
}

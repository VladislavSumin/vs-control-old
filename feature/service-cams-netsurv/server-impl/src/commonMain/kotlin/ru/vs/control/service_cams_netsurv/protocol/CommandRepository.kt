package ru.vs.control.service_cams_netsurv.protocol

import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val HEXADECIMAL = 16

// TODO rewrite this
// Искал медь а нашел золото: https://github.com/NeiroNx/python-dvr/blob/master/asyncio_dvrip.py#L525
internal object CommandRepository {

    private val json = Json { encodeDefaults = true }

    // TODO add password hash calculation
    // from here https://github.com/kinsi55/node_dvripclient/blob/master/lib/dvripclient.js
    // 		// Absolutely stupid custom password "hashing". Special thanks to https://github.com/tothi/pwn-hisilicon-dvr#password-hash-function
    // 		// There isnt really any protection involved with this... An attacker can just as well sniff the hash and use that to authenticate.
    // 		// By checking out the Github link you should come to the conclusion that any device of this kind should *never* be directly
    // 		// exposed to the internet anways.
    // 		if(UseHash) {
    // 			const pw_md5 = createHash("md5").update(Password).digest();
    // 			let HashBuilder = "";
    //
    // 			for(let i = 0; i < 8; i++) {
    // 				let n = (pw_md5[2 * i] + pw_md5[2 * i + 1]) % 62;
    // 				if(n > 9)
    // 					n += (n > 35) ? 13 : 7;
    //
    // 				HashBuilder += String.fromCharCode(n + 48);
    // 			}
    //
    // 			Password = HashBuilder;
    // 		}
    fun auth() = compile(
        commandCode = CommandCode.LOGIN_REQ,
        sessionID = 0,
        data = AuthRequestDto(),
    )

    fun keepAlive(sessionID: Int) = compile(
        commandCode = CommandCode.KEEPALIVE_REQ,
        sessionID = sessionID,
        data = KeepAliveRequestDto(sessionID = sessionID.toHexadecimalString()),
    )

    fun alarmStart(sessionID: Int) = compile(
        commandCode = CommandCode.GUARD_REQ,
        sessionID = sessionID,
        data = AlarmStartRequest(sessionID = sessionID.toHexadecimalString()),
    )

    fun monitorClaim(sessionID: Int) = compile(
        commandCode = CommandCode.MONITOR_CLAIM,
        sessionID = sessionID,
        data = MonitorClaimRequest(
            opMonitor = OpMonitorRequest(action = "Claim"),
            sessionID = sessionID.toHexadecimalString(),
        ),
    )

    fun monitorStart(sessionID: Int) = compile(
        commandCode = CommandCode.MONITOR_REQ,
        sessionID = sessionID,
        data = MonitorStartRequest(
            opMonitor = OpMonitorRequest(action = "Start"),
            sessionID = sessionID.toHexadecimalString(),
        ),
    )

//    fun monitorStop(sessionID: Int) = compile(
//        commandCode = CommandCode.MONITOR_REQ,
//        sessionID = sessionID,
//        data = MonitorClaimRequest(
//            opMonitor = OpMonitorRequest(action = "Stop"),
//            sessionID = sessionID.toHexadecimalString(),
//        ),
//    )

    private inline fun <reified T> compile(commandCode: CommandCode, sessionID: Int, data: T): Msg {
        return newInstance(commandCode, json.encodeToString(data).toByteArray(), sessionID)
    }

    private fun newInstance(messageId: CommandCode, data: ByteArray, sessionId: Int = 0) =
        Msg(
            messageId = messageId,
            dataLength = data.size,
            data = data,
            sessionId = sessionId
        )

    private fun Int.toHexadecimalString() = "0x${toString(HEXADECIMAL)}"

    @Serializable
    private data class AuthRequestDto(
        @SerialName("EncryptType")
        val encryptType: String = "MD5",
        @SerialName("LoginType")
        val loginType: String = "DVRIP-Web",
        @SerialName("PassWord")
        val password: String = PasswordUtils.hash(""),
        @SerialName("UserName")
        val username: String = "admin",
    )

    @Serializable
    private data class KeepAliveRequestDto(
        @SerialName("Name")
        val name: String = "KeepAlive",
        @SerialName("SessionID")
        val sessionID: String,
    )

    @Serializable
    private data class AlarmStartRequest(
        @SerialName("SessionID")
        val sessionID: String,
    )

    @Serializable
    private data class MonitorClaimRequest(
        @SerialName("Name")
        val name: String = "OPMonitor",
        @SerialName("OPMonitor")
        val opMonitor: OpMonitorRequest,
        @SerialName("SessionID")
        val sessionID: String,
    )

    @Serializable
    private data class MonitorStartRequest(
        @SerialName("OPMonitor")
        val opMonitor: OpMonitorRequest,
        @SerialName("SessionID")
        val sessionID: String,
    )

    @Serializable
    private data class OpMonitorRequest(
        @SerialName("Parameter")
        val parameters: Parameters = Parameters(),
        @SerialName("Action")
        val action: String,
    ) {
        @Serializable
        data class Parameters(
            @SerialName("Channel")
            val channel: Int = 0,
            @SerialName("CombinMode")
            val combinMode: String = "NONE",
            @SerialName("StreamType")
            val streamType: String = "Main",
            @SerialName("TransMode")
            val transMode: String = "TCP",
        )
    }
}

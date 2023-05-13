package ru.vs.control.service_cams_netsurv.protocol

import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val HEXADECIMAL = 16

// TODO rewrite this
internal object CommandRepository {

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
        CommandCode.LOGIN_REQ, 0,
        "EncryptType" to "MD5",
        "LoginType" to "DVRIP-Web",
        "PassWord" to "tlJwpbo6",
        "UserName" to "admin"
    )

    fun keepAlive(sessionID: Int) = compile(
        CommandCode.KEEPALIVE_REQ, sessionID,
        "Name" to "KeepAlive",
        "SessionID" to "0x${sessionID.toString(HEXADECIMAL)}"
    )
//
//    fun monitorClaim(sessionID: Int) = compile(
//        CommandCode.MONITOR_CLAIM, sessionID,
//        "Name" to "OPMonitor",
//        "OPMonitor" to mapOf(
//            "Action" to "Claim",
//            "Parameter" to mapOf(
//                "Channel" to 0,
//                "CombinMode" to "NONE",
//                "StreamType" to "Main",
//                "TransMode" to "TCP"
//            )
//        ),
//        "SessionID" to "0x%X".format(sessionID)
//    )
//
//    fun monitorStart(sessionID: Int) = compile(
//        CommandCode.MONITOR_REQ, sessionID,
//        "OPMonitor" to mapOf(
//            "Action" to "Start",
//            "Parameter" to mapOf(
//                "Channel" to 0,
//                "CombinMode" to "NONE",
//                "StreamType" to "Main",
//                "TransMode" to "TCP"
//            )
//        ),
//        "SessionID" to "0x%X".format(sessionID)
//    )
//
//    fun monitorStop(sessionID: Int) = compile(
//        CommandCode.MONITOR_REQ, sessionID,
//        "OPMonitor" to mapOf(
//            "Action" to "Stop",
//            "Parameter" to mapOf(
//                "Channel" to 0,
//                "CombinMode" to "NONE",
//                "StreamType" to "Main",
//                "TransMode" to "TCP"
//            )
//        ),
//        "SessionID" to "0x%X".format(sessionID)
//    )
//
    fun alarmStart(sessionID: Int) = compile(
        CommandCode.GUARD_REQ, sessionID,
        "SessionID" to "0x${sessionID.toString(HEXADECIMAL)}"
    )

    private fun compile(commandCode: CommandCode, sessionID: Int, vararg pairs: Pair<String, String>) =
        compile(commandCode, sessionID, pairs.toMap())

    private fun compile(commandCode: CommandCode, sessionID: Int, map: Map<String, String>) =
        newInstance(commandCode, map.toJsonString().toByteArray(), sessionID)

    private fun newInstance(messageId: CommandCode, data: ByteArray, sessionId: Int = 0) =
        Msg(
            messageId = messageId,
            dataLength = data.size,
            data = data,
            sessionId = sessionId
        )

    private fun Map<String, String>.toJsonString(): String = Json.encodeToString(this)
}

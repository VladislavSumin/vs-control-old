package ru.vs.control.service_cams_netsurv.protocol

/**
 * Command codes list
 * Used as part of [Msg]
 */
@Suppress("MagicNumber")
internal enum class CommandCode(val code: Int) {
    NULL(0),

    /**
     * Client >>> Camera
     * Request authorization. This command must be first command in connection
     */
    LOGIN_REQ(1000),

    /**
     * Client <<< Camera
     * Acknowledgement for [LOGIN_REQ]
     */
    LOGIN_RSP(1001),

    KEEPALIVE_REQ(1006),
    KEEPALIVE_RSP(1007),

    // SYSINFO_REQ(1020),
    // SYSINFO_RSP(1021),

    MONITOR_REQ(1410),
    MONITOR_RSP(1411),
    MONITOR_DATA(1412),
    MONITOR_CLAIM(1413),
    MONITOR_CLAIM_RSP(1414),

    /**
     * Client >>> Camera
     * Request enable sending ALARMS
     */
    GUARD_REQ(1500),

    /**
     * Client >>> Camera
     * Request enable sending ALARMS
     */
    GUARD_RSP(1501),

    // UNGUARD_REQ(1502),
    // UNGUARD_RSP(1503),

    /**
     * Client <<< Camera
     * Notify about alarm event
     */
    ALARM_REQ(1504),

    /**
     * Client ??? Camera
     * ??? maybe we must send respond when receiving [ALARM_REQ], but all work without this action
     */
    // ALARM_RSP(1505),

    // NET_ALARM_RSP(1506),
    // NET_ALARM_RSP2(1507),
    // ALARM_CENTER_MSG_REQ(1508),

    ;

    companion object {
        fun fromCode(code: Int): CommandCode {
            return CommandCode.values().find { it.code == code } ?: throw CommandCodeNotFoundException(code)
        }
    }

    class CommandCodeNotFoundException(code: Int) : Exception("Unknown command code $code")
}

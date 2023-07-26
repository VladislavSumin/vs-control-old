package ru.vs.control.service_cams_netsurv.protocol

import okio.ByteString.Companion.encodeUtf8

object PasswordUtils {
    @Suppress("MagicNumber")
    fun hash(password: String): String {
        val passwordHash = password.encodeUtf8().md5()
        val result = StringBuilder()
        for (i in 0 until 8) {
            var n = (passwordHash[2 * i].toUByte().toInt() + passwordHash[2 * i + 1].toUByte().toInt()) % 62
            if (n > 9) n += if (n > 35) 13 else 7
            result.append(Char(n + 48))
        }
        return result.toString()
    }
}

package ru.vs.control.service_cams_netsurv.protocol

import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordUtilsTest {
    @Test
    fun emptyPassword() {
        val hash = PasswordUtils.hash("")
        assertEquals("tlJwpbo6", hash)
    }
}

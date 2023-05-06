package ru.vs.control.id

import kotlin.test.Test
import kotlin.test.assertEquals

class IdTest {
    @Test
    fun createWithCorrectId() {
        Id(TEST_RAW_ID)
    }

    @Test
    fun rawIdEqualsCreationId() {
        val id = Id(TEST_RAW_ID)
        assertEquals(TEST_RAW_ID, id.rawId)
    }

    companion object {
        private const val TEST_RAW_ID = "test/id/with/same/path"
    }
}

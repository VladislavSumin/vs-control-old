package ru.vs.control.id

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class IdTest {
    @Test
    fun createWithCorrectSimpleId() {
        Id(TEST_RAW_ID_SIMPLE)
    }

    @Test
    fun createWithCorrectPartsId() {
        Id(TEST_RAW_ID_WITH_PARTS)
    }

    @Test
    fun createWithCorrectComplexId() {
        Id(TEST_RAW_ID_COMPLEX)
    }

    @Test
    fun createWithIncorrectIdEmptyString() {
        assertFailsWith<IllegalStateException> {
            Id("")
        }
    }

    @Test
    fun createWithIncorrectIdStartAtSlash() {
        assertFailsWith<IllegalStateException> {
            Id("/a/b/c")
        }
    }

    @Test
    fun createWithIncorrectIdEndAtSlash() {
        assertFailsWith<IllegalStateException> {
            Id("a/b/c/")
        }
    }

    @Test
    fun createWithIncorrectIdContainsSpace() {
        assertFailsWith<IllegalStateException> {
            Id("a a/b")
        }
    }

    @Test
    fun createWithIncorrectIdContainsUpperRegister() {
        assertFailsWith<IllegalStateException> {
            Id("aA")
        }
    }

    @Test
    fun rawIdEqualsCreationId() {
        val id = Id(TEST_RAW_ID_SIMPLE)
        assertEquals(TEST_RAW_ID_SIMPLE, id.rawId)
    }

    companion object {
        private const val TEST_RAW_ID_SIMPLE = "test_id"
        private const val TEST_RAW_ID_WITH_PARTS = "test/id/with/same/path"
        private const val TEST_RAW_ID_COMPLEX = "test/id/with_same_path"
    }
}

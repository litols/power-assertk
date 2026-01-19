package com.litols.power.assertk.assertions

import com.litols.power.assertk.assertThat
import com.litols.power.assertk.throwableString
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ThrowableTest {
    // message tests
    @Test
    fun message_returns_assert_on_message() {
        val exception = RuntimeException("test message")
        assertThat(exception).message().isEqualTo("test message")
    }

    @Test
    fun message_returns_null_when_no_message() {
        val exception = RuntimeException()
        assertThat(exception).message().isNull()
    }

    // hasMessage tests
    @Test
    fun hasMessage_succeeds_when_message_matches() {
        val exception = RuntimeException("test message")
        assertThat(exception).hasMessage("test message")
    }

    @Test
    fun hasMessage_succeeds_when_both_null() {
        val exception = RuntimeException()
        assertThat(exception).hasMessage(null)
    }

    @Test
    fun hasMessage_fails_when_message_differs() {
        val exception = RuntimeException("actual")
        val expected = "expected"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasMessage(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasMessage(expected)
            |          |                     |
            |          |                     expected
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // messageContains tests
    @Test
    fun messageContains_succeeds_when_text_found() {
        val exception = RuntimeException("this is a test message")
        assertThat(exception).messageContains("test")
    }

    @Test
    fun messageContains_fails_when_text_not_found() {
        val exception = RuntimeException("this is a test message")
        val text = "missing"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).messageContains(text)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).messageContains(text)
            |          |                          |
            |          |                          missing
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun messageContains_fails_when_message_is_null() {
        val exception = RuntimeException()
        val text = "test"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).messageContains(text)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).messageContains(text)
            |          |                          |
            |          |                          test
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // cause tests
    @Test
    fun cause_returns_assert_on_cause() {
        val cause = IllegalArgumentException("cause message")
        val exception = RuntimeException("wrapper", cause)
        assertThat(exception).cause().isNotNull()
    }

    @Test
    fun cause_returns_null_when_no_cause() {
        val exception = RuntimeException("no cause")
        assertThat(exception).cause().isNull()
    }

    // hasCause tests
    @Test
    fun hasCause_succeeds_when_cause_matches() {
        val cause = IllegalArgumentException("cause message")
        val exception = RuntimeException("wrapper", cause)
        assertThat(exception).hasCause(IllegalArgumentException("cause message"))
    }

    @Test
    fun hasCause_fails_when_no_cause() {
        val exception = RuntimeException("no cause")
        val expected = IllegalArgumentException("expected")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasCause(expected)
            |          |                   |
            |          |                   ${throwableString(expected)}
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasCause_fails_when_type_differs() {
        val cause = IllegalArgumentException("message")
        val exception = RuntimeException("wrapper", cause)
        val expected = IllegalStateException("message")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasCause(expected)
            |          |                   |
            |          |                   ${throwableString(expected)}
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasCause_fails_when_message_differs() {
        val cause = IllegalArgumentException("actual")
        val exception = RuntimeException("wrapper", cause)
        val expected = IllegalArgumentException("expected")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasCause(expected)
            |          |                   |
            |          |                   ${throwableString(expected)}
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasNoCause tests
    @Test
    fun hasNoCause_succeeds_when_no_cause() {
        val exception = RuntimeException("no cause")
        assertThat(exception).hasNoCause()
    }

    @Test
    fun hasNoCause_fails_when_cause_exists() {
        val cause = IllegalArgumentException("cause")
        val exception = RuntimeException("wrapper", cause)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasNoCause()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasNoCause()
            |          |
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // rootCause tests
    @Test
    fun rootCause_returns_assert_on_root_cause() {
        val root = IllegalArgumentException("root")
        val middle = IllegalStateException("middle", root)
        val exception = RuntimeException("top", middle)
        assertThat(exception).rootCause().hasMessage("root")
    }

    @Test
    fun rootCause_fails_when_no_cause() {
        val exception = RuntimeException("no cause")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).rootCause()
            }
        assertTrue(error.message!!.contains("expected to have a root cause"))
    }

    // hasRootCause tests
    @Test
    fun hasRootCause_succeeds_when_root_cause_matches() {
        val root = IllegalArgumentException("root message")
        val middle = IllegalStateException("middle", root)
        val exception = RuntimeException("top", middle)
        assertThat(exception).hasRootCause(IllegalArgumentException("root message"))
    }

    @Test
    fun hasRootCause_fails_when_no_cause() {
        val exception = RuntimeException("no cause")
        val expected = IllegalArgumentException("expected")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasRootCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasRootCause(expected)
            |          |                       |
            |          |                       ${throwableString(expected)}
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasRootCause_fails_when_type_differs() {
        val root = IllegalArgumentException("message")
        val exception = RuntimeException("wrapper", root)
        val expected = IllegalStateException("message")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasRootCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasRootCause(expected)
            |          |                       |
            |          |                       ${throwableString(expected)}
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasRootCause_fails_when_message_differs() {
        val root = IllegalArgumentException("actual")
        val exception = RuntimeException("wrapper", root)
        val expected = IllegalArgumentException("expected")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasRootCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(exception).hasRootCause(expected)
            |          |                       |
            |          |                       ${throwableString(expected)}
            |          ${throwableString(exception)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // Property chain tests
    @Test
    fun hasMessage_shows_power_assert_with_property_chain() {
        data class ErrorInfo(
            val exception: Throwable,
        )

        val info = ErrorInfo(RuntimeException("actual"))
        val expected = "expected"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(info.exception).hasMessage(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(info.exception).hasMessage(expected)
            |          |    |                     |
            |          |    |                     expected
            |          |    ${throwableString(info.exception)}
            |          ErrorInfo(exception=${throwableString(info.exception)})
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasCause_shows_power_assert_with_property_chain() {
        data class ErrorInfo(
            val exception: Throwable,
        )

        val cause = IllegalArgumentException("cause")
        val info = ErrorInfo(RuntimeException("wrapper", cause))
        val expected = IllegalStateException("expected")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(info.exception).hasCause(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(info.exception).hasCause(expected)
            |          |    |                   |
            |          |    |                   ${throwableString(expected)}
            |          |    ${throwableString(info.exception)}
            |          ErrorInfo(exception=${throwableString(info.exception)})
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // Chaining tests
    @Test
    fun can_chain_through_cause() {
        val cause = IllegalArgumentException("cause message")
        val exception = RuntimeException("wrapper", cause)
        assertThat(exception)
            .cause()
            .isNotNull()
            .message()
            .isEqualTo("cause message")
    }

    @Test
    fun can_chain_through_message() {
        val exception = RuntimeException("test message")
        assertThat(exception).message().isNotNull().hasLength(12)
    }
}

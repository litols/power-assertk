package powerassertk

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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasMessage("expected")
            }
        assertTrue(error.message!!.contains("hasMessage"))
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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).messageContains("missing")
            }
        assertTrue(error.message!!.contains("messageContains"))
    }

    @Test
    fun messageContains_fails_when_message_is_null() {
        val exception = RuntimeException()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).messageContains("test")
            }
        assertTrue(error.message!!.contains("messageContains"))
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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasCause(IllegalArgumentException("expected"))
            }
        assertTrue(error.message!!.contains("hasCause"))
    }

    @Test
    fun hasCause_fails_when_type_differs() {
        val cause = IllegalArgumentException("message")
        val exception = RuntimeException("wrapper", cause)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasCause(IllegalStateException("message"))
            }
        assertTrue(error.message!!.contains("hasCause"))
    }

    @Test
    fun hasCause_fails_when_message_differs() {
        val cause = IllegalArgumentException("actual")
        val exception = RuntimeException("wrapper", cause)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasCause(IllegalArgumentException("expected"))
            }
        assertTrue(error.message!!.contains("hasCause"))
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
        assertTrue(error.message!!.contains("hasNoCause"))
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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasRootCause(IllegalArgumentException("expected"))
            }
        assertTrue(error.message!!.contains("hasRootCause"))
    }

    @Test
    fun hasRootCause_fails_when_type_differs() {
        val root = IllegalArgumentException("message")
        val exception = RuntimeException("wrapper", root)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasRootCause(IllegalStateException("message"))
            }
        assertTrue(error.message!!.contains("hasRootCause"))
    }

    @Test
    fun hasRootCause_fails_when_message_differs() {
        val root = IllegalArgumentException("actual")
        val exception = RuntimeException("wrapper", root)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(exception).hasRootCause(IllegalArgumentException("expected"))
            }
        assertTrue(error.message!!.contains("hasRootCause"))
    }

    // Chaining tests
    @Test
    fun can_chain_through_cause() {
        val cause = IllegalArgumentException("cause message")
        val exception = RuntimeException("wrapper", cause)
        assertThat(exception).cause().isNotNull().message().isEqualTo("cause message")
    }

    @Test
    fun can_chain_through_message() {
        val exception = RuntimeException("test message")
        assertThat(exception).message().isNotNull().hasLength(12)
    }
}

package powerassertk

import kotlin.test.Test

class ThrowableTestDebug {
    @Test
    fun debug_hasMessage() {
        val exception = RuntimeException("actual")
        val expected = "expected"

        try {
            assertThat(exception).hasMessage(expected)
        } catch (e: AssertionError) {
            println("=== hasMessage output ===")
            println(e.message)
            println("=========================")
            throw e
        }
    }

    @Test
    fun debug_messageContains() {
        val exception = RuntimeException("this is a test message")
        val text = "missing"

        try {
            assertThat(exception).messageContains(text)
        } catch (e: AssertionError) {
            println("=== messageContains output ===")
            println(e.message)
            println("==============================")
            throw e
        }
    }

    @Test
    fun debug_messageContains_null() {
        val exception = RuntimeException()
        val text = "test"

        try {
            assertThat(exception).messageContains(text)
        } catch (e: AssertionError) {
            println("=== messageContains null output ===")
            println(e.message)
            println("===================================")
            throw e
        }
    }

    @Test
    fun debug_hasCause_no_cause() {
        val exception = RuntimeException("no cause")
        val expected = IllegalArgumentException("expected")

        try {
            assertThat(exception).hasCause(expected)
        } catch (e: AssertionError) {
            println("=== hasCause no cause output ===")
            println(e.message)
            println("================================")
            throw e
        }
    }

    @Test
    fun debug_hasCause_type_differs() {
        val cause = IllegalArgumentException("message")
        val exception = RuntimeException("wrapper", cause)
        val expected = IllegalStateException("message")

        try {
            assertThat(exception).hasCause(expected)
        } catch (e: AssertionError) {
            println("=== hasCause type differs output ===")
            println(e.message)
            println("====================================")
            throw e
        }
    }

    @Test
    fun debug_hasCause_message_differs() {
        val cause = IllegalArgumentException("actual")
        val exception = RuntimeException("wrapper", cause)
        val expected = IllegalArgumentException("expected")

        try {
            assertThat(exception).hasCause(expected)
        } catch (e: AssertionError) {
            println("=== hasCause message differs output ===")
            println(e.message)
            println("=======================================")
            throw e
        }
    }

    @Test
    fun debug_hasNoCause() {
        val cause = IllegalArgumentException("cause")
        val exception = RuntimeException("wrapper", cause)

        try {
            assertThat(exception).hasNoCause()
        } catch (e: AssertionError) {
            println("=== hasNoCause output ===")
            println(e.message)
            println("=========================")
            throw e
        }
    }

    @Test
    fun debug_hasRootCause_no_cause() {
        val exception = RuntimeException("no cause")
        val expected = IllegalArgumentException("expected")

        try {
            assertThat(exception).hasRootCause(expected)
        } catch (e: AssertionError) {
            println("=== hasRootCause no cause output ===")
            println(e.message)
            println("====================================")
            throw e
        }
    }

    @Test
    fun debug_hasRootCause_type_differs() {
        val root = IllegalArgumentException("message")
        val exception = RuntimeException("wrapper", root)
        val expected = IllegalStateException("message")

        try {
            assertThat(exception).hasRootCause(expected)
        } catch (e: AssertionError) {
            println("=== hasRootCause type differs output ===")
            println(e.message)
            println("========================================")
            throw e
        }
    }

    @Test
    fun debug_hasRootCause_message_differs() {
        val root = IllegalArgumentException("actual")
        val exception = RuntimeException("wrapper", root)
        val expected = IllegalArgumentException("expected")

        try {
            assertThat(exception).hasRootCause(expected)
        } catch (e: AssertionError) {
            println("=== hasRootCause message differs output ===")
            println(e.message)
            println("===========================================")
            throw e
        }
    }

    @Test
    fun debug_property_chain() {
        data class ErrorInfo(val exception: Throwable)
        val info = ErrorInfo(RuntimeException("actual"))
        val expected = "expected"

        try {
            assertThat(info.exception).hasMessage(expected)
        } catch (e: AssertionError) {
            println("=== property chain output ===")
            println(e.message)
            println("=============================")
            throw e
        }
    }
}

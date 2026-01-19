package com.litols.power.assertk.assertions

import com.litols.power.assertk.assertThat
import kotlin.test.Test
import kotlin.test.assertTrue

class ThrowableJvmTest {
    // stackTrace tests
    @Test
    fun stackTrace_returns_stack_trace_as_list() {
        val exception = RuntimeException("test exception")
        val stackTraceAssert = assertThat(exception).stackTrace()
        assertTrue(stackTraceAssert.actual.isNotEmpty())
        assertTrue(stackTraceAssert.actual.any { it.contains("ThrowableJvmTest") })
    }

    @Test
    fun stackTrace_works_with_nested_exceptions() {
        val cause = IllegalArgumentException("cause")
        val exception = RuntimeException("main", cause)
        val stackTraceAssert = assertThat(exception).stackTrace()
        assertTrue(stackTraceAssert.actual.isNotEmpty())
    }

    @Test
    fun stackTrace_can_be_chained() {
        val exception = RuntimeException("test")
        val stackTraceAssert = assertThat(exception).stackTrace()
        // Can chain with List assertions
        assertThat(stackTraceAssert.actual).isNotEmpty()
    }
}

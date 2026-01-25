package com.litols.power.assertk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FailureTest {
    @Test
    fun simpleFailure_throws_immediately() {
        val error =
            assertFailsWith<AssertionError> {
                SimpleFailure.notify(AssertionError("test error"))
            }

        assertEquals("test error", error.message)
    }

    @Test
    fun softFailure_collects_single_error() {
        val softFailure = Failure.soft()

        val error =
            assertFailsWith<AssertionError> {
                softFailure {
                    softFailure.notify(AssertionError("error 1"))
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (1 failures):"))
        assertTrue(message.contains("- error 1"))
    }

    @Test
    fun softFailure_collects_multiple_errors() {
        val softFailure = Failure.soft()

        val error =
            assertFailsWith<AssertionError> {
                softFailure {
                    softFailure.notify(AssertionError("error 1"))
                    softFailure.notify(AssertionError("error 2"))
                    softFailure.notify(AssertionError("error 3"))
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (3 failures):"))
        assertTrue(message.contains("- error 1"))
        assertTrue(message.contains("- error 2"))
        assertTrue(message.contains("- error 3"))
    }

    @Test
    fun softFailure_with_custom_message() {
        val softFailure = Failure.soft("Custom error message")

        val error =
            assertFailsWith<AssertionError> {
                softFailure {
                    softFailure.notify(AssertionError("error 1"))
                    softFailure.notify(AssertionError("error 2"))
                }
            }

        val message = error.message!!
        assertTrue(message.contains("Custom error message (2 failures):"))
        assertTrue(message.contains("- error 1"))
        assertTrue(message.contains("- error 2"))
    }

    @Test
    fun softFailure_does_not_throw_when_no_errors() {
        val softFailure = Failure.soft()

        // Should not throw
        softFailure {
            // No failures
        }
    }

    @Test
    fun failureContext_defaults_to_simpleFailure() {
        val current = FailureContext.current()
        assertEquals(SimpleFailure, current)
    }

    @Test
    fun failureContext_pushAndPop() {
        val softFailure = Failure.soft()

        assertEquals(SimpleFailure, FailureContext.current())

        FailureContext.pushFailure(softFailure)
        assertEquals(softFailure, FailureContext.current())

        FailureContext.popFailure()
        assertEquals(SimpleFailure, FailureContext.current())
    }

    @Test
    fun failureContext_stacking() {
        val soft1 = Failure.soft("soft1")
        val soft2 = Failure.soft("soft2")

        FailureContext.pushFailure(soft1)
        FailureContext.pushFailure(soft2)

        assertEquals(soft2, FailureContext.current())

        FailureContext.popFailure()
        assertEquals(soft1, FailureContext.current())

        FailureContext.popFailure()
        assertEquals(SimpleFailure, FailureContext.current())
    }

    @Test
    fun notifyFailure_uses_current_context() {
        // Default context should throw immediately
        assertFailsWith<AssertionError> {
            notifyFailure(AssertionError("immediate error"))
        }
    }

    @Test
    fun notifyFailure_with_soft_context() {
        val softFailure = Failure.soft()

        val error =
            assertFailsWith<AssertionError> {
                softFailure {
                    notifyFailure(AssertionError("error 1"))
                    notifyFailure(AssertionError("error 2"))
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (2 failures):"))
        assertTrue(message.contains("- error 1"))
        assertTrue(message.contains("- error 2"))
    }

    @Test
    fun softFailure_invoke_manages_context() {
        assertEquals(SimpleFailure, FailureContext.current())

        val softFailure = Failure.soft()

        assertFailsWith<AssertionError> {
            softFailure {
                // Inside the block, context should be softFailure
                assertEquals(softFailure, FailureContext.current())
                notifyFailure(AssertionError("error"))
            }
        }

        // After the block, context should be restored
        assertEquals(SimpleFailure, FailureContext.current())
    }

    @Test
    fun softFailure_close_pops_context() {
        val softFailure = Failure.soft()

        FailureContext.pushFailure(softFailure)
        assertEquals(softFailure, FailureContext.current())

        softFailure.close()
        assertEquals(SimpleFailure, FailureContext.current())
    }

    @Test
    fun nested_softFailures() {
        val outer = Failure.soft("outer")
        val inner = Failure.soft("inner")

        val error =
            assertFailsWith<AssertionError> {
                outer {
                    notifyFailure(AssertionError("outer error 1"))

                    assertFailsWith<AssertionError> {
                        inner {
                            notifyFailure(AssertionError("inner error 1"))
                            notifyFailure(AssertionError("inner error 2"))
                        }
                    }

                    notifyFailure(AssertionError("outer error 2"))
                }
            }

        val message = error.message!!
        assertTrue(message.contains("outer (2 failures):"))
        assertTrue(message.contains("- outer error 1"))
        assertTrue(message.contains("- outer error 2"))
    }
}

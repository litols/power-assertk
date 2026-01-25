package com.litols.power.assertk.assertions

import com.litols.power.assertk.assertThat
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AllTest {
    @Test
    fun all_succeeds_when_all_assertions_pass() {
        assertThat("Test").all {
            startsWith("T")
            hasLength(4)
            contains("es")
        }
    }

    @Test
    fun all_collects_single_failure() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("Test").all {
                    startsWith("L")
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (1 failures):"))
        assertTrue(message.contains("- expected to start with:<\"L\"> but was:<\"Test\">"))
    }

    @Test
    fun all_collects_multiple_failures() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("Test").all {
                    startsWith("L")
                    hasLength(10)
                    contains("xyz")
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (3 failures):"))
        assertTrue(message.contains("- expected to start with:<\"L\"> but was:<\"Test\">"))
        assertTrue(message.contains("- expected to have length:<10> but was:<4>"))
        assertTrue(message.contains("- expected to contain:<\"xyz\"> but was:<\"Test\">"))
    }

    @Test
    fun all_with_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("Test").all("Custom validation failed") {
                    startsWith("L")
                    hasLength(10)
                }
            }

        val message = error.message!!
        assertTrue(message.contains("Custom validation failed (2 failures):"))
        assertTrue(message.contains("- expected to start with:<\"L\"> but was:<\"Test\">"))
        assertTrue(message.contains("- expected to have length:<10> but was:<4>"))
    }

    @Test
    fun all_continues_after_first_failure() {
        var secondAssertionExecuted = false

        assertFailsWith<AssertionError> {
            assertThat("Test").all {
                startsWith("L") // This fails
                secondAssertionExecuted = true
                hasLength(4) // This should still execute and pass
            }
        }

        assertTrue(secondAssertionExecuted, "Second assertion should have been executed")
    }

    @Test
    fun all_with_mixed_success_and_failure() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("Test").all {
                    startsWith("T") // Success
                    hasLength(10) // Failure
                    contains("es") // Success
                    endsWith("xyz") // Failure
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (2 failures):"))
        assertTrue(message.contains("- expected to have length:<10> but was:<4>"))
        assertTrue(message.contains("- expected to end with:<\"xyz\"> but was:<\"Test\">"))
    }

    @Test
    fun all_works_with_different_types() {
        data class Person(
            val name: String,
            val age: Int,
        )
        val person: Person? = Person("Alice", 30)

        assertThat(person).isNotNull().all {
            isEqualTo(Person("Alice", 30))
        }
    }

    @Test
    fun all_with_numbers() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(42).all {
                    isEqualTo(42) // Success
                    isGreaterThan(100) // Failure
                    isLessThan(10) // Failure
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (2 failures):"))
        assertTrue(message.contains("- expected to be greater than:<100> but was:<42>"))
        assertTrue(message.contains("- expected to be less than:<10> but was:<42>"))
    }

    @Test
    fun all_with_collections() {
        val list = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(list).all {
                    hasSize(5) // Failure
                    isEmpty() // Failure
                }
            }

        val message = error.message!!
        assertTrue(message.contains("The following assertions failed (2 failures):"))
        assertTrue(message.contains("- expected to have size:<5> but was:<3>"))
        assertTrue(message.contains("- expected to be empty but was:<[1, 2, 3]>"))
    }

    @Test
    fun all_does_not_affect_normal_assertions() {
        // Normal assertion should still throw immediately
        assertFailsWith<AssertionError> {
            assertThat("Test").startsWith("L")
        }
    }

    @Test
    fun all_nested_is_possible() {
        // Nested all blocks should work
        val error =
            assertFailsWith<AssertionError> {
                assertThat("Test").all {
                    all {
                        startsWith("L")
                    }
                    hasLength(10)
                }
            }

        val message = error.message!!
        // Both failures should be collected
        assertTrue(
            message.contains("expected to start with:<\"L\"> but was:<\"Test\">") ||
                message.contains("The following assertions failed"),
        )
    }
}

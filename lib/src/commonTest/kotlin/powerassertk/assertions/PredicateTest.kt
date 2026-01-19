package powerassertk.assertions

import powerassertk.assertThat
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PredicateTest {
    // matchesPredicate tests
    @Test
    fun matchesPredicate_succeeds_when_predicate_returns_true() {
        assertThat(10).matchesPredicate { it != 5 }
    }

    @Test
    fun matchesPredicate_fails_when_predicate_returns_false() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).matchesPredicate { it == 5 }
            }
        assertTrue(error.message!!.contains("expected to match predicate"))
    }

    @Test
    fun matchesPredicate_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).matchesPredicate({ "Custom: value should be less than 5" }) { it == 5 }
            }
        assertTrue(error.message!!.contains("Custom: value should be less than 5"))
    }

    @Test
    fun matchesPredicate_works_with_complex_objects() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val person = Person("Alice", 30)
        assertThat(person).matchesPredicate { it.age == 30 && it.name.startsWith("A") }
    }

    @Test
    fun matchesPredicate_works_with_strings() {
        assertThat("hello world").matchesPredicate { it.contains("world") }
    }

    @Test
    fun matchesPredicate_works_with_collections() {
        assertThat(listOf(1, 2, 3, 4, 5)).matchesPredicate { it.size == 5 }
    }
}

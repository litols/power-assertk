package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GivenTest {
    @Test
    fun given_provides_access_to_actual_value() {
        val value = "hello"
        var capturedValue: String? = null

        assertThat(value).given { actual ->
            capturedValue = actual
        }

        assertEquals("hello", capturedValue)
    }

    @Test
    fun given_allows_custom_assertions() {
        data class Person(
            val name: String,
            val age: Int,
        )

        fun Assert<Person>.hasAge(expected: Int) =
            given { actual ->
                if (actual.age == expected) return@given
                expected("age:${show(expected)} but was age:${show(actual.age)}")
            }

        val person = Person("Alice", 30)
        assertThat(person).hasAge(30) // Should succeed
    }

    @Test
    fun given_custom_assertion_fails_with_message() {
        data class Person(
            val name: String,
            val age: Int,
        )

        fun Assert<Person>.hasAge(expected: Int) =
            given { actual ->
                if (actual.age == expected) return@given
                expected("age:${show(expected)} but was age:${show(actual.age)}")
            }

        val person = Person("Alice", 30)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(person).hasAge(25)
            }

        assertTrue(error.message!!.contains("age:25 but was age:30"))
    }

    @Test
    fun show_formats_null() {
        assertEquals("null", show(null))
    }

    @Test
    fun show_formats_string() {
        assertEquals("\"hello\"", show("hello"))
    }

    @Test
    fun show_formats_char() {
        assertEquals("'c'", show('c'))
    }

    @Test
    fun show_formats_long() {
        assertEquals("42L", show(42L))
    }

    @Test
    fun show_formats_float() {
        assertEquals("3.14f", show(3.14f))
    }

    @Test
    fun show_formats_double() {
        val result = show(2.718)
        // JS may format doubles differently
        assertTrue(result == "2.718d" || result.startsWith("2.718"))
    }

    @Test
    fun show_formats_other_types() {
        assertEquals("42", show(42))
        assertEquals("true", show(true))

        data class Person(
            val name: String,
        )
        val personStr = show(Person("Alice"))
        // JS and JVM may format data classes slightly differently
        assertTrue(personStr.contains("Person") && personStr.contains("Alice"))
    }

    @Test
    fun expected_throws_assertion_error() {
        val error =
            assertFailsWith<AssertionError> {
                expected("custom error message")
            }

        assertEquals("custom error message", error.message)
    }

    @Test
    fun given_can_be_used_for_complex_validations() {
        data class Person(
            val name: String,
            val age: Int,
        )

        fun Assert<Person>.isAdult() =
            given { actual ->
                if (actual.age >= 18) return@given
                expected("expected adult (age >= 18) but was age:${show(actual.age)}")
            }

        val adult = Person("Alice", 30)
        assertThat(adult).isAdult() // Should succeed

        val child = Person("Bob", 10)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(child).isAdult()
            }

        assertTrue(error.message!!.contains("expected adult (age >= 18) but was age:10"))
    }

    @Test
    fun given_can_access_properties() {
        data class Person(
            val name: String,
            val age: Int,
        )

        fun Assert<Person>.hasName(expected: String) =
            given { actual ->
                if (actual.name == expected) return@given
                expected("name:${show(expected)} but was name:${show(actual.name)}")
            }

        val person = Person("Alice", 30)
        assertThat(person).hasName("Alice") // Should succeed

        val error =
            assertFailsWith<AssertionError> {
                assertThat(person).hasName("Bob")
            }

        assertEquals("name:\"Bob\" but was name:\"Alice\"", error.message)
    }
}

package powerassertk

import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OptionalTest {
    // isPresent tests
    @Test
    fun isPresent_succeeds_when_optional_has_value() {
        val optional = Optional.of("test")
        val valueAssert = assertThat(optional).isPresent()
        assertTrue(valueAssert.actual == "test")
    }

    @Test
    fun isPresent_fails_when_optional_is_empty() {
        val optional = Optional.empty<String>()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(optional).isPresent()
            }
        assertTrue(error.message!!.contains("expected to be present"))
    }

    // isEmpty tests
    @Test
    fun isEmpty_succeeds_when_optional_is_empty() {
        val optional = Optional.empty<String>()
        assertThat(optional).isEmpty()
    }

    @Test
    fun isEmpty_fails_when_optional_has_value() {
        data class OptionalContainer(
            val optional: Optional<String>,
        )
        val container = OptionalContainer(Optional.of("test"))

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.optional).isEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(container.optional).isEmpty()
            |          |         |
            |          |         Optional[test]
            |          OptionalContainer(optional=Optional[test])
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isEmpty_supports_custom_message() {
        val optional = Optional.of("test")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(optional).isEmpty { "Custom: should be empty" }
            }
        assertTrue(error.message!!.contains("Custom: should be empty"))
    }

    // hasValue tests
    @Test
    fun hasValue_succeeds_when_value_matches() {
        val optional = Optional.of("test")
        assertThat(optional).hasValue("test")
    }

    @Test
    fun hasValue_fails_when_value_does_not_match() {
        data class OptionalContainer(
            val optional: Optional<String>,
        )
        val container = OptionalContainer(Optional.of("test"))

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.optional).hasValue("different")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(container.optional).hasValue("different")
            |          |         |
            |          |         Optional[test]
            |          OptionalContainer(optional=Optional[test])
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasValue_fails_when_optional_is_empty() {
        data class OptionalContainer(
            val optional: Optional<String>,
        )
        val container = OptionalContainer(Optional.empty())

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.optional).hasValue("test")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(container.optional).hasValue("test")
            |          |         |
            |          |         Optional.empty
            |          OptionalContainer(optional=Optional.empty)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasValue_supports_custom_message() {
        val optional = Optional.of("test")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(optional).hasValue("different") { "Custom: wrong value" }
            }
        assertTrue(error.message!!.contains("Custom: wrong value"))
    }

    @Test
    fun hasValue_works_with_complex_objects() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val person = Person("Alice", 30)
        val optional = Optional.of(person)
        assertThat(optional).hasValue(person)
    }
}

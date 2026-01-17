package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class StringTest {
    // isEqualTo tests
    @Test
    fun isEqualTo_succeeds_when_strings_equal() {
        assertThat("hello").isEqualTo("hello")
    }

    @Test
    fun isEqualTo_succeeds_with_ignoreCase() {
        assertThat("Hello").isEqualTo("HELLO", ignoreCase = true)
    }

    @Test
    fun isEqualTo_fails_when_strings_differ() {
        val actual = "hello"
        val expected = "world"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEqualTo(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isEqualTo(expected)
            |          |                 |
            |          |                 world
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isEqualTo_fails_when_case_differs_without_ignoreCase() {
        val actual = "Hello"
        val expected = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEqualTo(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isEqualTo(expected)
            |          |                 |
            |          |                 hello
            |          Hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotEqualTo tests
    @Test
    fun isNotEqualTo_succeeds_when_strings_differ() {
        assertThat("hello").isNotEqualTo("world")
    }

    @Test
    fun isNotEqualTo_succeeds_when_case_differs_without_ignoreCase() {
        assertThat("Hello").isNotEqualTo("hello")
    }

    @Test
    fun isNotEqualTo_fails_when_strings_equal() {
        val actual = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotEqualTo("hello")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotEqualTo("hello")
            |          |
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotEqualTo_fails_with_ignoreCase() {
        val actual = "Hello"
        val expected = "HELLO"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotEqualTo(expected, ignoreCase = true)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotEqualTo(expected, ignoreCase = true)
            |          |                    |
            |          |                    HELLO
            |          Hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // Nullable tests
    @Test
    fun isEqualTo_succeeds_when_both_null() {
        val str: String? = null
        assertThat(str).isEqualTo(null)
    }

    @Test
    fun isEqualTo_fails_when_one_is_null() {
        val str: String? = null
        val expected = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(str).isEqualTo(expected)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(str).isEqualTo(expected)
            |          |              |
            |          |              hello
            |          null
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotEqualTo_succeeds_when_one_is_null() {
        val str: String? = null
        assertThat(str).isNotEqualTo("hello")
    }

    // Custom message support test
    @Test
    fun isEqualTo_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("hello").isEqualTo("world") { "Custom: strings should match" }
            }
        assertTrue(error.message!!.contains("Custom: strings should match"))
    }

    // Power Assert diagram verification tests
    @Test
    fun isEqualTo_shows_power_assert_with_property_chain() {
        data class User(
            val name: String,
        )
        val user = User("Alice")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(user.name).isEqualTo("Bob")
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(user.name).isEqualTo("Bob")
            |          |    |
            |          |    Alice
            |          User(name=Alice)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isEqualTo_shows_power_assert_with_variable() {
        val expected = "world"
        val actual = "hello"
        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEqualTo(expected)
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(actual).isEqualTo(expected)
            |          |                 |
            |          |                 world
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotEqualTo_shows_power_assert_with_expression() {
        data class Config(
            val value: String,
        )
        val config = Config("test")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(config.value).isNotEqualTo("test")
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(config.value).isNotEqualTo("test")
            |          |      |
            |          |      test
            |          Config(value=test)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }
}

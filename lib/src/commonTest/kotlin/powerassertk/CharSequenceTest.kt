package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CharSequenceTest {
    // length tests
    @Test
    fun length_returns_assert_on_length() {
        assertThat("hello").length().isEqualTo(5)
    }

    // isEmpty tests
    @Test
    fun isEmpty_succeeds_when_empty() {
        assertThat("").isEmpty()
    }

    @Test
    fun isEmpty_fails_when_not_empty() {
        val actual = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isEmpty()
            |          |
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotEmpty tests
    @Test
    fun isNotEmpty_succeeds_when_not_empty() {
        assertThat("hello").isNotEmpty()
    }

    @Test
    fun isNotEmpty_fails_when_empty() {
        val actual = ""

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotEmpty()
            |          |
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNullOrEmpty tests
    @Test
    fun isNullOrEmpty_succeeds_when_null() {
        val str: String? = null
        assertThat(str).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_succeeds_when_empty() {
        val str: String? = ""
        assertThat(str).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_fails_when_not_empty() {
        val str: String? = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(str).isNullOrEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(str).isNullOrEmpty()
            |          |
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasLength tests
    @Test
    fun hasLength_succeeds_when_length_matches() {
        assertThat("hello").hasLength(5)
    }

    @Test
    fun hasLength_fails_when_length_differs() {
        val actual = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasLength(3)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).hasLength(3)
            |          |
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasSameLengthAs tests
    @Test
    fun hasSameLengthAs_succeeds_when_lengths_match() {
        assertThat("hello").hasSameLengthAs("world")
    }

    @Test
    fun hasSameLengthAs_fails_when_lengths_differ() {
        val actual = "hello"
        val other = "hi"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasSameLengthAs(other)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).hasSameLengthAs(other)
            |          |                       |
            |          |                       hi
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasLineCount tests
    @Test
    fun hasLineCount_succeeds_for_single_line() {
        assertThat("hello").hasLineCount(1)
    }

    @Test
    fun hasLineCount_succeeds_for_multiple_lines() {
        assertThat("line1\nline2\nline3").hasLineCount(3)
    }

    @Test
    fun hasLineCount_fails_when_count_differs() {
        val actual = "line1\nline2"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasLineCount(3)
            }
        val message = error.message!!

        assertTrue(
            message.contains("assertThat(actual).hasLineCount(3)"),
            "Should show assertion expression",
        )
        assertTrue(
            message.contains("line1"),
            "Should show actual value",
        )
    }

    // contains single tests
    @Test
    fun contains_succeeds_when_substring_exists() {
        assertThat("hello world").contains("world")
    }

    @Test
    fun contains_succeeds_with_ignoreCase() {
        assertThat("Hello World").contains("WORLD", ignoreCase = true)
    }

    @Test
    fun contains_fails_when_substring_not_found() {
        val actual = "hello"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).contains("xyz")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).contains("xyz")
            |          |
            |          hello
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // contains vararg tests
    @Test
    fun contains_vararg_succeeds_when_all_found() {
        assertThat("hello world").contains("hello", "world")
    }

    @Test
    fun contains_vararg_fails_when_any_missing() {
        val actual = "hello world"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).contains("hello", "xyz")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).contains("hello", "xyz")
            |          |
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // contains Iterable tests
    @Test
    fun contains_iterable_succeeds_when_all_found() {
        assertThat("hello world").contains(listOf("hello", "world"))
    }

    @Test
    fun contains_iterable_fails_when_any_missing() {
        val actual = "hello world"
        val values = listOf("hello", "xyz")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).contains(values)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).contains(values)
            |          |                |
            |          |                [hello, xyz]
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // doesNotContain single tests
    @Test
    fun doesNotContain_succeeds_when_substring_not_found() {
        assertThat("hello").doesNotContain("xyz")
    }

    @Test
    fun doesNotContain_fails_when_substring_found() {
        val actual = "hello world"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).doesNotContain("world")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).doesNotContain("world")
            |          |
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // doesNotContain vararg tests
    @Test
    fun doesNotContain_vararg_succeeds_when_none_found() {
        assertThat("hello").doesNotContain("xyz", "abc")
    }

    @Test
    fun doesNotContain_vararg_fails_when_any_found() {
        val actual = "hello world"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).doesNotContain("xyz", "world")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).doesNotContain("xyz", "world")
            |          |
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // doesNotContain Iterable tests
    @Test
    fun doesNotContain_iterable_succeeds_when_none_found() {
        assertThat("hello").doesNotContain(listOf("xyz", "abc"))
    }

    @Test
    fun doesNotContain_iterable_fails_when_any_found() {
        val actual = "hello world"
        val values = listOf("xyz", "world")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).doesNotContain(values)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).doesNotContain(values)
            |          |                      |
            |          |                      [xyz, world]
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // startsWith tests
    @Test
    fun startsWith_succeeds_when_prefix_matches() {
        assertThat("hello world").startsWith("hello")
    }

    @Test
    fun startsWith_succeeds_with_ignoreCase() {
        assertThat("Hello World").startsWith("HELLO", ignoreCase = true)
    }

    @Test
    fun startsWith_fails_when_prefix_not_match() {
        val actual = "hello world"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).startsWith("world")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).startsWith("world")
            |          |
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // endsWith tests
    @Test
    fun endsWith_succeeds_when_suffix_matches() {
        assertThat("hello world").endsWith("world")
    }

    @Test
    fun endsWith_succeeds_with_ignoreCase() {
        assertThat("Hello World").endsWith("WORLD", ignoreCase = true)
    }

    @Test
    fun endsWith_fails_when_suffix_not_match() {
        val actual = "hello world"

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).endsWith("hello")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).endsWith("hello")
            |          |
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // matches tests
    @Test
    fun matches_succeeds_when_regex_matches() {
        assertThat("hello123").matches(Regex("\\w+\\d+"))
    }

    @Test
    fun matches_fails_when_regex_not_match() {
        val actual = "hello"
        val pattern = Regex("\\d+")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).matches(pattern)
            }
        val message = error.message!!

        assertTrue(
            message.contains("assertThat(actual).matches(pattern)"),
            "Should show assertion expression",
        )
        assertTrue(
            message.contains("hello"),
            "Should show actual value",
        )
    }

    // containsMatch tests
    @Test
    fun containsMatch_succeeds_when_regex_found() {
        assertThat("hello123world").containsMatch(Regex("\\d+"))
    }

    @Test
    fun containsMatch_fails_when_regex_not_found() {
        val actual = "hello"
        val pattern = Regex("\\d+")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsMatch(pattern)
            }
        val message = error.message!!

        assertTrue(
            message.contains("assertThat(actual).containsMatch(pattern)"),
            "Should show assertion expression",
        )
        assertTrue(
            message.contains("hello"),
            "Should show actual value",
        )
    }

    // Custom message support test
    @Test
    fun isEmpty_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("hello").isEmpty { "Custom: should be empty" }
            }
        assertTrue(error.message!!.contains("Custom: should be empty"))
    }

    // Power Assert diagram verification tests
    @Test
    fun contains_shows_power_assert_with_property_chain() {
        data class Message(
            val text: String,
        )
        val msg = Message("hello world")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(msg.text).contains("xyz")
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(msg.text).contains("xyz")
            |          |   |
            |          |   hello world
            |          Message(text=hello world)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun startsWith_shows_power_assert_with_expression() {
        val prefix = "world"
        val text = "hello world"
        val error =
            assertFailsWith<AssertionError> {
                assertThat(text).startsWith(prefix)
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(text).startsWith(prefix)
            |          |                |
            |          |                world
            |          hello world
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasLength_shows_power_assert_with_chained_call() {
        data class User(
            val name: String,
        )
        val user = User("Alice")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(user.name).hasLength(10)
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(user.name).hasLength(10)
            |          |    |
            |          |    Alice
            |          User(name=Alice)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }
}

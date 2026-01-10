package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

data class Person(val name: String, val age: Int)

class AssertTest {

    @Test
    fun isEqualTo_succeeds_when_values_are_equal() {
        assertThat(42).isEqualTo(42)
        assertThat("hello").isEqualTo("hello")
        assertThat(null).isEqualTo(null)
    }

    @Test
    fun isEqualTo_fails_with_correct_error_message() {
        val error = assertFailsWith<AssertionError> {
            assertThat(42).isEqualTo(100)
        }
        val message = error.message!!

        // Power Assert のフォーマット全体を確認
        val expectedFormat = """
            assertThat(42).isEqualTo(100)
            |
        """.trimIndent()

        assertTrue(message.contains(expectedFormat), "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message")
    }

    @Test
    fun isEqualTo_shows_power_assert_intermediate_values() {
        val person = Person("Alice", 30)
        val error = assertFailsWith<AssertionError> {
            assertThat(person.name).isEqualTo("Bob")
        }
        val message = error.message!!

        // Power Assert の複数行フォーマット全体を確認
        val expectedFormat = """
            assertThat(person.name).isEqualTo("Bob")
            |          |      |
            |          |      Alice
            |          Person(name=Alice, age=30)
        """.trimIndent()

        assertTrue(message.contains(expectedFormat), "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message")
    }

    @Test
    fun isEqualTo_fails_with_custom_message() {
        val error = assertFailsWith<AssertionError> {
            assertThat(5).isEqualTo(10) { "Custom error: values don't match" }
        }
        val message = error.message!!

        // カスタムメッセージのフォーマット全体を確認
        val expectedFormat = """
            Custom error: values don't match
            assertThat(5).isEqualTo(10) { "Custom error: values don't match" }
            |
        """.trimIndent()

        assertTrue(message.contains(expectedFormat), "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message")
    }
}

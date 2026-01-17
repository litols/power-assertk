package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BooleanTest {
    @Test
    fun isTrue_succeeds_when_value_is_true() {
        assertThat(true).isTrue()
    }

    @Test
    fun isTrue_fails_with_power_assert_format() {
        val actual = false

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isTrue()
            }
        val message = error.message!!

        // Power Assert形式で変数展開を確認
        val expectedFormat =
            """
            assertThat(actual).isTrue()
            |          |
            |          false
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isTrue_shows_power_assert_with_expression() {
        data class State(
            val isActive: Boolean,
        )
        val state = State(false)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(state.isActive).isTrue()
            }
        val message = error.message!!

        // Power Assert形式の中間値確認
        val expectedFormat =
            """
            assertThat(state.isActive).isTrue()
            |          |     |
            |          |     false
            |          State(isActive=false)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show intermediate values in Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isTrue_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(false).isTrue { "Custom: expected true" }
            }
        val message = error.message!!

        // カスタムメッセージとPower Assert形式の両方を確認
        val expectedFormat =
            """
            Custom: expected true
            assertThat(false).isTrue { "Custom: expected true" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show custom message with Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isFalse_succeeds_when_value_is_false() {
        assertThat(false).isFalse()
    }

    @Test
    fun isFalse_fails_with_power_assert_format() {
        val actual = true

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isFalse()
            }
        val message = error.message!!

        // Power Assert形式で変数展開を確認
        val expectedFormat =
            """
            assertThat(actual).isFalse()
            |          |
            |          true
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isFalse_shows_power_assert_with_expression() {
        data class State(
            val isActive: Boolean,
        )
        val state = State(true)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(state.isActive).isFalse()
            }
        val message = error.message!!

        // Power Assert形式の中間値確認
        val expectedFormat =
            """
            assertThat(state.isActive).isFalse()
            |          |     |
            |          |     true
            |          State(isActive=true)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show intermediate values in Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isFalse_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(true).isFalse { "Custom: expected false" }
            }
        val message = error.message!!

        // カスタムメッセージとPower Assert形式の両方を確認
        val expectedFormat =
            """
            Custom: expected false
            assertThat(true).isFalse { "Custom: expected false" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show custom message with Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }
}

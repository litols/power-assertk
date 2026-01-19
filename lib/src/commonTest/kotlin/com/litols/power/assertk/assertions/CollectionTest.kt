package com.litols.power.assertk.assertions

import com.litols.power.assertk.assertThat
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CollectionTest {
    // size tests
    @Test
    fun size_returns_assert_on_size() {
        assertThat(listOf(1, 2, 3)).size().isEqualTo(3)
    }

    // isEmpty tests
    @Test
    fun isEmpty_succeeds_when_empty() {
        assertThat(emptyList<Int>()).isEmpty()
    }

    @Test
    fun isEmpty_fails_when_not_empty() {
        val actual = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isEmpty()
            |          |
            |          [1, 2, 3]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotEmpty tests
    @Test
    fun isNotEmpty_succeeds_when_not_empty() {
        assertThat(listOf(1, 2, 3)).isNotEmpty()
    }

    @Test
    fun isNotEmpty_fails_when_empty() {
        val actual = emptyList<Int>()

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotEmpty()
            |          |
            |          []
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNullOrEmpty tests
    @Test
    fun isNullOrEmpty_succeeds_when_null() {
        val list: List<Int>? = null
        assertThat(list).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_succeeds_when_empty() {
        val list: List<Int>? = emptyList()
        assertThat(list).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_fails_when_not_empty() {
        val list: List<Int>? = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(list).isNullOrEmpty()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(list).isNullOrEmpty()
            |          |
            |          [1, 2, 3]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasSize tests
    @Test
    fun hasSize_succeeds_when_size_matches() {
        assertThat(listOf(1, 2, 3)).hasSize(3)
    }

    @Test
    fun hasSize_fails_when_size_differs() {
        val actual = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasSize(5)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).hasSize(5)
            |          |
            |          [1, 2, 3]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasSameSizeAs tests
    @Test
    fun hasSameSizeAs_succeeds_when_sizes_match() {
        assertThat(listOf(1, 2, 3)).hasSameSizeAs(listOf("a", "b", "c"))
    }

    @Test
    fun hasSameSizeAs_fails_when_sizes_differ() {
        val actual = listOf(1, 2, 3)
        val other = listOf("a", "b")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasSameSizeAs(other)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).hasSameSizeAs(other)
            |          |                     |
            |          |                     [a, b]
            |          [1, 2, 3]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // Custom message support test
    @Test
    fun isEmpty_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(listOf(1, 2, 3)).isEmpty { "Custom: should be empty" }
            }
        assertTrue(error.message!!.contains("Custom: should be empty"))
    }

    // Power Assert diagram verification tests
    @Test
    fun isEmpty_shows_power_assert_with_property_chain() {
        data class Container(
            val items: List<Int>,
        )

        val container = Container(listOf(1, 2, 3))
        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.items).isEmpty()
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(container.items).isEmpty()
            |          |         |
            |          |         [1, 2, 3]
            |          Container(items=[1, 2, 3])
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasSize_shows_power_assert_with_variable() {
        val numbers = listOf(1, 2, 3, 4, 5)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(numbers).hasSize(3)
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(numbers).hasSize(3)
            |          |
            |          [1, 2, 3, 4, 5]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasSameSizeAs_shows_power_assert_with_chained_call() {
        data class Data(
            val values: List<String>,
        )

        val data = Data(listOf("a", "b", "c"))
        val error =
            assertFailsWith<AssertionError> {
                assertThat(data.values).hasSameSizeAs(listOf(1, 2))
            }
        val message = error.message!!

        // Power Assertの完全なダイアグラムフォーマットを確認
        val expectedFormat =
            """
            assertThat(data.values).hasSameSizeAs(listOf(1, 2))
            |          |    |                     |
            |          |    |                     [1, 2]
            |          |    [a, b, c]
            |          Data(values=[a, b, c])
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // Different collection types
    @Test
    fun hasSize_works_with_set() {
        assertThat(setOf(1, 2, 3)).hasSize(3)
    }

    @Test
    fun isEmpty_works_with_mutableList() {
        assertThat(mutableListOf<Int>()).isEmpty()
    }

    @Test
    fun hasSameSizeAs_works_with_different_types() {
        assertThat(setOf(1, 2, 3)).hasSameSizeAs(listOf("a", "b", "c"))
    }
}

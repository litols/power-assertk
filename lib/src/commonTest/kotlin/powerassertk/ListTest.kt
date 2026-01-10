package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ListTest {
    // index tests
    @Test
    fun index_returns_element_at_index() {
        val list = listOf(1, 2, 3)
        assertThat(list).index(1).isEqualTo(2)
    }

    @Test
    fun index_fails_when_index_out_of_bounds() {
        val actual = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).index(5)
            }

        // Note: index() is a transformation method without a message parameter,
        // so it doesn't support Power Assert diagram transformation
        assertTrue(error.message!!.contains("expected to have element at index"))
    }

    @Test
    fun index_allows_chaining() {
        data class Person(val name: String)
        val people = listOf(Person("Alice"), Person("Bob"))
        assertThat(people).index(0).isEqualTo(Person("Alice"))
    }

    // containsExactly tests
    @Test
    fun containsExactly_succeeds_when_exact_match() {
        val list = listOf(1, 2, 3)
        assertThat(list).containsExactly(1, 2, 3)
    }

    @Test
    fun containsExactly_fails_when_order_differs() {
        val actual = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsExactly(1, 3, 2)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).containsExactly(1, 3, 2)
            |          |
            |          [1, 2, 3]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun containsExactly_fails_when_size_differs() {
        val actual = listOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsExactly(1, 2)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).containsExactly(1, 2)
            |          |
            |          [1, 2, 3]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun containsExactly_supports_custom_message() {
        val list = listOf(1, 2, 3)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(list).containsExactly(1, 2, 4) { "Custom error" }
            }
        assertTrue(error.message!!.contains("Custom error"))
    }

    // containsSubList tests
    @Test
    fun containsSubList_succeeds_when_sublist_found() {
        val list = listOf(1, 2, 3, 4, 5)
        assertThat(list).containsSubList(listOf(2, 3, 4))
    }

    @Test
    fun containsSubList_succeeds_when_sublist_at_start() {
        val list = listOf(1, 2, 3, 4, 5)
        assertThat(list).containsSubList(listOf(1, 2))
    }

    @Test
    fun containsSubList_succeeds_when_sublist_at_end() {
        val list = listOf(1, 2, 3, 4, 5)
        assertThat(list).containsSubList(listOf(4, 5))
    }

    @Test
    fun containsSubList_succeeds_when_sublist_is_empty() {
        val list = listOf(1, 2, 3)
        assertThat(list).containsSubList(emptyList())
    }

    @Test
    fun containsSubList_fails_when_sublist_not_found() {
        val actual = listOf(1, 2, 3, 4, 5)
        val sublist = listOf(2, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsSubList(sublist)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).containsSubList(sublist)
            |          |                       |
            |          |                       [2, 4]
            |          [1, 2, 3, 4, 5]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // startsWith tests
    @Test
    fun startsWith_succeeds_when_starts_with_elements() {
        val list = listOf(1, 2, 3, 4)
        assertThat(list).startsWith(1, 2)
    }

    @Test
    fun startsWith_succeeds_when_exact_match() {
        val list = listOf(1, 2, 3)
        assertThat(list).startsWith(1, 2, 3)
    }

    @Test
    fun startsWith_fails_when_not_starting_with_elements() {
        val actual = listOf(1, 2, 3, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).startsWith(2, 3)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).startsWith(2, 3)
            |          |
            |          [1, 2, 3, 4]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun startsWith_fails_when_too_short() {
        val actual = listOf(1, 2)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).startsWith(1, 2, 3)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).startsWith(1, 2, 3)
            |          |
            |          [1, 2]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // endsWith tests
    @Test
    fun endsWith_succeeds_when_ends_with_elements() {
        val list = listOf(1, 2, 3, 4)
        assertThat(list).endsWith(3, 4)
    }

    @Test
    fun endsWith_succeeds_when_exact_match() {
        val list = listOf(1, 2, 3)
        assertThat(list).endsWith(1, 2, 3)
    }

    @Test
    fun endsWith_fails_when_not_ending_with_elements() {
        val actual = listOf(1, 2, 3, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).endsWith(2, 3)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).endsWith(2, 3)
            |          |
            |          [1, 2, 3, 4]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun endsWith_fails_when_too_short() {
        val actual = listOf(3, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).endsWith(1, 2, 3, 4)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).endsWith(1, 2, 3, 4)
            |          |
            |          [3, 4]
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }
}

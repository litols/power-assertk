package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ArrayTest {
    // size tests
    @Test
    fun size_returns_assert_on_size() {
        assertThat(arrayOf(1, 2, 3)).size().isEqualTo(3)
    }

    // isEmpty tests
    @Test
    fun isEmpty_succeeds_when_empty() {
        assertThat(emptyArray<Int>()).isEmpty()
    }

    @Test
    fun isEmpty_fails_when_not_empty() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(arrayOf(1, 2, 3)).isEmpty()
            }
        assertTrue(error.message!!.contains("assertThat(arrayOf(1, 2, 3)).isEmpty()"))
    }

    // isNotEmpty tests
    @Test
    fun isNotEmpty_succeeds_when_not_empty() {
        assertThat(arrayOf(1, 2, 3)).isNotEmpty()
    }

    @Test
    fun isNotEmpty_fails_when_empty() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(emptyArray<Int>()).isNotEmpty()
            }
        assertTrue(error.message!!.contains("assertThat(emptyArray<Int>()).isNotEmpty()"))
    }

    // isNullOrEmpty tests
    @Test
    fun isNullOrEmpty_succeeds_when_null() {
        val array: Array<Int>? = null
        assertThat(array).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_succeeds_when_empty() {
        val array: Array<Int>? = emptyArray()
        assertThat(array).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_fails_when_not_empty() {
        val array: Array<Int>? = arrayOf(1, 2, 3)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(array).isNullOrEmpty()
            }
        assertTrue(error.message!!.contains("assertThat(array).isNullOrEmpty()"))
    }

    // hasSize tests
    @Test
    fun hasSize_succeeds_when_size_matches() {
        assertThat(arrayOf(1, 2, 3)).hasSize(3)
    }

    @Test
    fun hasSize_fails_when_size_differs() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(arrayOf(1, 2, 3)).hasSize(5)
            }
        assertTrue(error.message!!.contains("assertThat(arrayOf(1, 2, 3)).hasSize(5)"))
    }

    // hasSameSizeAs tests
    @Test
    fun hasSameSizeAs_succeeds_when_sizes_match() {
        assertThat(arrayOf(1, 2, 3)).hasSameSizeAs(arrayOf("a", "b", "c"))
    }

    @Test
    fun hasSameSizeAs_fails_when_sizes_differ() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(arrayOf(1, 2, 3)).hasSameSizeAs(arrayOf("a", "b"))
            }
        assertTrue(error.message!!.contains("hasSameSizeAs"))
    }

    // Custom message support test
    @Test
    fun isEmpty_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(arrayOf(1, 2, 3)).isEmpty { "Custom: should be empty" }
            }
        assertTrue(error.message!!.contains("Custom: should be empty"))
    }

    // Power Assert diagram verification tests
    @Test
    fun isEmpty_shows_power_assert_with_property_chain() {
        data class Container(val items: Array<Int>) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Container) return false
                return items.contentEquals(other.items)
            }

            override fun hashCode(): Int = items.contentHashCode()
        }
        val container = Container(arrayOf(1, 2, 3))
        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.items).isEmpty()
            }
        val message = error.message!!

        // Power Assert diagram shows array memory address, so just verify the structure
        assertTrue(
            message.contains("assertThat(container.items).isEmpty()"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
        assertTrue(
            message.contains("Container(items=[1, 2, 3])"),
            "Should show container with array contents in toString:\nActual:\n$message",
        )
    }

    @Test
    fun hasSize_shows_power_assert_with_variable() {
        val numbers = arrayOf(1, 2, 3, 4, 5)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(numbers).hasSize(3)
            }
        val message = error.message!!

        // Power Assert diagram shows array memory address, so just verify the structure
        assertTrue(
            message.contains("assertThat(numbers).hasSize(3)"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    @Test
    fun hasSameSizeAs_shows_power_assert_with_chained_call() {
        data class Data(val values: Array<String>) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Data) return false
                return values.contentEquals(other.values)
            }

            override fun hashCode(): Int = values.contentHashCode()
        }
        val data = Data(arrayOf("a", "b", "c"))
        val error =
            assertFailsWith<AssertionError> {
                assertThat(data.values).hasSameSizeAs(arrayOf(1, 2))
            }
        val message = error.message!!

        // Power Assert diagram shows array memory address, so just verify the structure
        assertTrue(
            message.contains("assertThat(data.values).hasSameSizeAs(arrayOf(1, 2))"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
        assertTrue(
            message.contains("Data(values=[a, b, c])"),
            "Should show data with array contents in toString:\nActual:\n$message",
        )
    }
}

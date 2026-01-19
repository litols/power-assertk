package com.litols.power.assertk.assertions

import com.litols.power.assertk.arrayContentsString
import com.litols.power.assertk.assertThat
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
        val actual = arrayOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEmpty()
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).isEmpty()"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    // isNotEmpty tests
    @Test
    fun isNotEmpty_succeeds_when_not_empty() {
        assertThat(arrayOf(1, 2, 3)).isNotEmpty()
    }

    @Test
    fun isNotEmpty_fails_when_empty() {
        val actual = emptyArray<Int>()

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotEmpty()
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).isNotEmpty()"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
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
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(array).isNullOrEmpty()"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    // hasSize tests
    @Test
    fun hasSize_succeeds_when_size_matches() {
        assertThat(arrayOf(1, 2, 3)).hasSize(3)
    }

    @Test
    fun hasSize_fails_when_size_differs() {
        val actual = arrayOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasSize(5)
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).hasSize(5)"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    // hasSameSizeAs tests
    @Test
    fun hasSameSizeAs_succeeds_when_sizes_match() {
        assertThat(arrayOf(1, 2, 3)).hasSameSizeAs(arrayOf("a", "b", "c"))
    }

    @Test
    fun hasSameSizeAs_fails_when_sizes_differ() {
        val actual = arrayOf(1, 2, 3)
        val other = arrayOf("a", "b")

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).hasSameSizeAs(other)
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).hasSameSizeAs(other)"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
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
        data class Container(
            val items: Array<Int>,
        ) {
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
            message.contains("Container(items=${arrayContentsString("[1, 2, 3]")})"),
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
        data class Data(
            val values: Array<String>,
        ) {
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
            message.contains("Data(values=${arrayContentsString("[a, b, c]")})"),
            "Should show data with array contents in toString:\nActual:\n$message",
        )
    }

    // Wave 3.4: Additional Array methods

    // contains/doesNotContain tests
    @Test
    fun contains_succeeds_when_element_found() {
        assertThat(arrayOf(1, 2, 3)).contains(2)
    }

    @Test
    fun contains_fails_when_element_not_found() {
        val actual = arrayOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).contains(4)
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).contains(4)"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    @Test
    fun doesNotContain_succeeds_when_element_not_found() {
        assertThat(arrayOf(1, 2, 3)).doesNotContain(4)
    }

    // containsAll tests
    @Test
    fun containsAll_succeeds_when_all_found() {
        assertThat(arrayOf(1, 2, 3, 4)).containsAll(1, 3)
    }

    @Test
    fun containsAll_fails_when_any_missing() {
        val actual = arrayOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsAll(1, 4)
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).containsAll(1, 4)"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    // containsExactly tests
    @Test
    fun containsExactly_succeeds_when_exact_match() {
        assertThat(arrayOf(1, 2, 3)).containsExactly(1, 2, 3)
    }

    @Test
    fun containsExactly_fails_when_order_differs() {
        val actual = arrayOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsExactly(1, 3, 2)
            }
        val message = error.message!!

        // Arrays show memory address, so verify structure
        assertTrue(
            message.contains("assertThat(actual).containsExactly(1, 3, 2)"),
            "Should show assertion expression in Power Assert diagram:\nActual:\n$message",
        )
    }

    // containsExactlyInAnyOrder tests
    @Test
    fun containsExactlyInAnyOrder_succeeds() {
        assertThat(arrayOf(3, 1, 2)).containsExactlyInAnyOrder(1, 2, 3)
    }

    // containsNone tests
    @Test
    fun containsNone_succeeds_when_none_found() {
        assertThat(arrayOf(1, 2, 3)).containsNone(4, 5)
    }

    // each tests
    @Test
    fun each_succeeds_when_all_satisfy() {
        assertThat(arrayOf(2, 4, 6)).each {
            if (it.actual % 2 != 0) throw AssertionError("not even")
        }
    }

    // any tests
    @Test
    fun any_succeeds_when_at_least_one_satisfies() {
        assertThat(arrayOf(1, 2, 3)).any { it.isEqualTo(2) }
    }

    // none tests
    @Test
    fun none_succeeds_when_none_satisfy() {
        assertThat(arrayOf(1, 2, 3)).none { it.isEqualTo(4) }
    }

    // atLeast tests
    @Test
    fun atLeast_succeeds() {
        assertThat(arrayOf(2, 3, 4, 5)).atLeast(2) { it.isGreaterThan(3) }
    }

    // atMost tests
    @Test
    fun atMost_succeeds() {
        assertThat(arrayOf(2, 3, 4, 5)).atMost(2) { it.isGreaterThan(3) }
    }

    // exactly tests
    @Test
    fun exactly_succeeds() {
        assertThat(arrayOf(2, 3, 4, 5)).exactly(2) { it.isGreaterThan(3) }
    }

    // first tests
    @Test
    fun first_returns_first_element() {
        assertThat(arrayOf(1, 2, 3)).first().isEqualTo(1)
    }

    // single tests
    @Test
    fun single_returns_single_element() {
        assertThat(arrayOf(42)).single().isEqualTo(42)
    }

    // index tests
    @Test
    fun index_returns_element_at_index() {
        assertThat(arrayOf(1, 2, 3)).index(1).isEqualTo(2)
    }

    // extracting tests
    @Test
    fun extracting_single_property() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val people = arrayOf(Person("Alice", 30), Person("Bob", 25))
        val names: Iterable<String> = assertThat(people).extracting { p -> p.name }.actual
        assertThat(names).containsExactlyInAnyOrder("Alice", "Bob")
    }

    @Test
    fun extracting_two_properties() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val people = arrayOf(Person("Alice", 30))
        val result = assertThat(people).extracting({ p -> p.name }, { p -> p.age })
        val list = result.actual
        assertTrue(list.size == 1)
        assertTrue(list[0] == ("Alice" to 30))
    }

    @Test
    fun extracting_three_properties() {
        data class Person(
            val name: String,
            val age: Int,
            val city: String,
        )

        val people = arrayOf(Person("Alice", 30, "NYC"))
        val result = assertThat(people).extracting({ p -> p.name }, { p -> p.age }, { p -> p.city })
        val list = result.actual
        assertTrue(list.size == 1)
        assertTrue(list[0] == Triple("Alice", 30, "NYC"))
    }
}

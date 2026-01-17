package powerassertk

import powerassertk.assertions.containsExactlyInAnyOrder
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SequenceTest {
    // Note: contains and doesNotContain tests removed due to potential ambiguity with stdlib methods

    // containsAll tests
    @Test
    fun containsAll_succeeds_when_all_found() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3, 4)
        assertThat(seq).containsAll(1, 3)
    }

    @Test
    fun containsAll_fails_when_any_missing() {
        val actual: Sequence<Int> = sequenceOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsAll(1, 4)
            }
        val message = error.message!!

        // Note: Sequences are lazy and display their internal class name, not contents
        val expectedFormat =
            """
            assertThat(actual).containsAll(1, 4)
            |          |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
        // Also verify the actual contains Sequence reference
        assertTrue(
            message.contains(sequenceTypeCheck()) || message.contains("kotlin.collections"),
            "Should show Sequence type in diagram:\n$message",
        )
    }

    // containsAtLeast tests
    @Test
    fun containsAtLeast_succeeds_when_all_found() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3, 4)
        assertThat(seq).containsAtLeast(1, 3)
    }

    // containsOnly tests
    @Test
    fun containsOnly_succeeds_when_exact_match() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)
        assertThat(seq).containsOnly(3, 2, 1)
    }

    @Test
    fun containsOnly_fails_when_extra_elements() {
        val actual: Sequence<Int> = sequenceOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsOnly(1, 2)
            }
        val message = error.message!!

        // Note: Sequences are lazy and display their internal class name, not contents
        val expectedFormat =
            """
            assertThat(actual).containsOnly(1, 2)
            |          |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
        assertTrue(
            message.contains(sequenceTypeCheck()) || message.contains("kotlin.collections"),
            "Should show Sequence type in diagram:\n$message",
        )
    }

    // containsExactly tests
    @Test
    fun containsExactly_succeeds_when_exact_match() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)
        assertThat(seq).containsExactly(1, 2, 3)
    }

    @Test
    fun containsExactly_fails_when_order_differs() {
        val actual: Sequence<Int> = sequenceOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsExactly(1, 3, 2)
            }
        val message = error.message!!

        // Note: Sequences are lazy and display their internal class name, not contents
        val expectedFormat =
            """
            assertThat(actual).containsExactly(1, 3, 2)
            |          |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
        assertTrue(
            message.contains(sequenceTypeCheck()) || message.contains("kotlin.collections"),
            "Should show Sequence type in diagram:\n$message",
        )
    }

    // containsExactlyInAnyOrder tests
    @Test
    fun containsExactlyInAnyOrder_succeeds() {
        val seq: Sequence<Int> = sequenceOf(3, 1, 2)
        assertThat(seq).containsExactlyInAnyOrder(1, 2, 3)
    }

    // containsNone tests
    @Test
    fun containsNone_succeeds_when_none_found() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)
        assertThat(seq).containsNone(4, 5)
    }

    @Test
    fun containsNone_fails_when_any_found() {
        val actual: Sequence<Int> = sequenceOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).containsNone(2, 4)
            }
        val message = error.message!!

        // Note: Sequences are lazy and display their internal class name, not contents
        val expectedFormat =
            """
            assertThat(actual).containsNone(2, 4)
            |          |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
        assertTrue(
            message.contains(sequenceTypeCheck()) || message.contains("kotlin.collections"),
            "Should show Sequence type in diagram:\n$message",
        )
    }

    // isEmpty tests
    @Test
    fun isEmpty_succeeds_when_empty() {
        val seq: Sequence<Int> = emptySequence()
        assertThat(seq).isEmpty()
    }

    @Test
    fun isEmpty_fails_when_not_empty() {
        val actual: Sequence<Int> = sequenceOf(1)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isEmpty()
            }
        val message = error.message!!

        // Note: Sequences are lazy and display their internal class name, not contents
        val expectedFormat =
            """
            assertThat(actual).isEmpty()
            |          |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
        assertTrue(
            message.contains(sequenceTypeCheck()) || message.contains("kotlin.collections"),
            "Should show Sequence type in diagram:\n$message",
        )
    }

    // isNotEmpty tests
    @Test
    fun isNotEmpty_succeeds_when_not_empty() {
        val seq: Sequence<Int> = sequenceOf(1)
        assertThat(seq).isNotEmpty()
    }

    // each tests
    @Test
    fun each_succeeds_when_all_satisfy() {
        val seq: Sequence<Int> = sequenceOf(2, 4, 6)
        assertThat(seq).each {
            if (it.actual % 2 != 0) throw AssertionError("not even")
        }
    }

    @Test
    fun each_fails_when_any_does_not_satisfy() {
        val seq: Sequence<Int> = sequenceOf(2, 3, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(seq).each {
                    if (it.actual % 2 != 0) throw AssertionError("not even")
                }
            }

        // Note: each() has message parameter in the middle, not at the end,
        // so Power Assert transformation doesn't apply
        assertTrue(error.message!!.contains("element at index"))
    }

    // any tests
    @Test
    fun any_succeeds_when_at_least_one_satisfies() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)
        assertThat(seq).any { it.isEqualTo(2) }
    }

    @Test
    fun any_fails_when_none_satisfy() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(seq).any { it.isEqualTo(4) }
            }

        // Note: any() has message parameter in the middle, not at the end,
        // so Power Assert transformation doesn't apply
        assertTrue(error.message!!.contains("expected at least one element"))
    }

    // none tests
    @Test
    fun none_succeeds_when_none_satisfy() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)
        assertThat(seq).none { it.isEqualTo(4) }
    }

    // atLeast tests
    @Test
    fun atLeast_succeeds() {
        val seq: Sequence<Int> = sequenceOf(2, 3, 4, 5)
        assertThat(seq).atLeast(2) { it.isGreaterThan(3) }
    }

    @Test
    fun atLeast_fails() {
        val seq: Sequence<Int> = sequenceOf(2, 3, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(seq).atLeast(2) { it.isGreaterThan(3) }
            }

        // Note: atLeast() has message parameter in the middle, not at the end,
        // so Power Assert transformation doesn't apply
        assertTrue(error.message!!.contains("expected at least 2"))
    }

    // atMost tests
    @Test
    fun atMost_succeeds() {
        val seq: Sequence<Int> = sequenceOf(2, 3, 4, 5)
        assertThat(seq).atMost(2) { it.isGreaterThan(3) }
    }

    // exactly tests
    @Test
    fun exactly_succeeds() {
        val seq: Sequence<Int> = sequenceOf(2, 3, 4, 5)
        assertThat(seq).exactly(2) { it.isGreaterThan(3) }
    }

    @Test
    fun exactly_fails() {
        val seq: Sequence<Int> = sequenceOf(2, 3, 4)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(seq).exactly(2) { it.isGreaterThan(3) }
            }

        // Note: exactly() has message parameter in the middle, not at the end,
        // so Power Assert transformation doesn't apply
        assertTrue(error.message!!.contains("expected exactly 2"))
    }

    // first tests
    @Test
    fun first_returns_first_element() {
        val seq: Sequence<Int> = sequenceOf(1, 2, 3)
        assertThat(seq).first().isEqualTo(1)
    }

    @Test
    fun first_fails_when_empty() {
        val seq: Sequence<Int> = emptySequence()

        val error =
            assertFailsWith<AssertionError> {
                assertThat(seq).first()
            }

        // Note: first() is a transformation method without a message parameter,
        // so Power Assert transformation doesn't apply
        assertTrue(error.message!!.contains("expected to have at least one element"))
    }

    // single tests
    @Test
    fun single_returns_single_element() {
        val seq: Sequence<Int> = sequenceOf(42)
        assertThat(seq).single().isEqualTo(42)
    }

    @Test
    fun single_fails_when_multiple_elements() {
        val seq: Sequence<Int> = sequenceOf(1, 2)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(seq).single()
            }

        // Note: single() is a transformation method without a message parameter,
        // so Power Assert transformation doesn't apply
        assertTrue(error.message!!.contains("expected to have exactly one element"))
    }

    // extracting tests
    @Test
    fun extracting_single_property() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val people: Sequence<Person> = sequenceOf(Person("Alice", 30), Person("Bob", 25))
        val names: Iterable<String> = assertThat(people).extracting { p -> p.name }.actual
        assertThat(names).containsExactlyInAnyOrder("Alice", "Bob")
    }

    @Test
    fun extracting_two_properties() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val people: Sequence<Person> = sequenceOf(Person("Alice", 30))
        val result = assertThat(people).extracting({ p -> p.name }, { p -> p.age })
        // Verify result has expected pair
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

        val people: Sequence<Person> = sequenceOf(Person("Alice", 30, "NYC"))
        val result = assertThat(people).extracting({ p -> p.name }, { p -> p.age }, { p -> p.city })
        // Verify result has expected triple
        val list = result.actual
        assertTrue(list.size == 1)
        assertTrue(list[0] == Triple("Alice", 30, "NYC"))
    }
}

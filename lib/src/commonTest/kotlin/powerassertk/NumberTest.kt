package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NumberTest {
    // isZero tests
    @Test
    fun isZero_succeeds_for_zero_values() {
        assertThat(0).isZero()
        assertThat(0.0).isZero()
        assertThat(0L).isZero()
        assertThat(0.0f).isZero()
    }

    @Test
    fun isZero_fails_for_positive_values() {
        val actual = 5

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isZero()
            |          |
            |          5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isZero_fails_for_negative_values() {
        val actual = -5

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isZero()
            |          |
            |          -5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isZero_fails_for_double_non_zero() {
        val actual = 1.5

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isZero()
            |          |
            |          1.5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotZero tests
    @Test
    fun isNotZero_succeeds_for_positive_values() {
        assertThat(5).isNotZero()
        assertThat(1.5).isNotZero()
        assertThat(100L).isNotZero()
        assertThat(0.1f).isNotZero()
    }

    @Test
    fun isNotZero_succeeds_for_negative_values() {
        assertThat(-5).isNotZero()
        assertThat(-1.5).isNotZero()
        assertThat(-100L).isNotZero()
        assertThat(-0.1f).isNotZero()
    }

    @Test
    fun isNotZero_fails_for_zero_int() {
        val actual = 0

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotZero()
            |          |
            |          0
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotZero_fails_for_zero_double() {
        val actual = 0.0

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotZero()
            |          |
            |          ${numberString(actual)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotZero_fails_for_zero_long() {
        val actual = 0L

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotZero()
            |          |
            |          0
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotZero_fails_for_zero_float() {
        val actual = 0.0f

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNotZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNotZero()
            |          |
            |          ${numberString(actual)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isPositive tests
    @Test
    fun isPositive_succeeds_for_positive_int() {
        assertThat(5).isPositive()
        assertThat(1).isPositive()
        assertThat(100).isPositive()
    }

    @Test
    fun isPositive_succeeds_for_positive_double() {
        assertThat(1.5).isPositive()
        assertThat(0.1).isPositive()
    }

    @Test
    fun isPositive_succeeds_for_positive_long() {
        assertThat(100L).isPositive()
        assertThat(1L).isPositive()
    }

    @Test
    fun isPositive_succeeds_for_positive_float() {
        assertThat(1.5f).isPositive()
        assertThat(0.1f).isPositive()
    }

    @Test
    fun isPositive_fails_for_negative_values() {
        val actual = -5

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isPositive()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isPositive()
            |          |
            |          -5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isPositive_fails_for_zero() {
        val actual = 0

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isPositive()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isPositive()
            |          |
            |          0
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isPositive_fails_for_zero_double() {
        val actual = 0.0

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isPositive()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isPositive()
            |          |
            |          ${numberString(actual)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNegative tests
    @Test
    fun isNegative_succeeds_for_negative_int() {
        assertThat(-5).isNegative()
        assertThat(-1).isNegative()
        assertThat(-100).isNegative()
    }

    @Test
    fun isNegative_succeeds_for_negative_double() {
        assertThat(-1.5).isNegative()
        assertThat(-0.1).isNegative()
    }

    @Test
    fun isNegative_succeeds_for_negative_long() {
        assertThat(-100L).isNegative()
        assertThat(-1L).isNegative()
    }

    @Test
    fun isNegative_succeeds_for_negative_float() {
        assertThat(-1.5f).isNegative()
        assertThat(-0.1f).isNegative()
    }

    @Test
    fun isNegative_fails_for_positive_values() {
        val actual = 5

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNegative()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNegative()
            |          |
            |          5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNegative_fails_for_zero() {
        val actual = 0

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNegative()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNegative()
            |          |
            |          0
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNegative_fails_for_zero_double() {
        val actual = 0.0

        val error =
            assertFailsWith<AssertionError> {
                assertThat(actual).isNegative()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(actual).isNegative()
            |          |
            |          ${numberString(actual)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // Power Assert diagram verification tests with property chains
    @Test
    fun isZero_shows_power_assert_with_property_chain() {
        data class Counter(val value: Int)
        val counter = Counter(5)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(counter.value).isZero()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(counter.value).isZero()
            |          |       |
            |          |       5
            |          Counter(value=5)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isPositive_shows_power_assert_with_property_chain() {
        data class Temperature(val celsius: Double)
        val temp = Temperature(-10.0)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(temp.celsius).isPositive()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(temp.celsius).isPositive()
            |          |    |
            |          |    ${numberString(temp.celsius)}
            |          Temperature(celsius=${numberString(temp.celsius)})
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNegative_shows_power_assert_with_property_chain() {
        data class Score(val points: Int)
        val score = Score(100)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(score.points).isNegative()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(score.points).isNegative()
            |          |     |
            |          |     100
            |          Score(points=100)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }
}

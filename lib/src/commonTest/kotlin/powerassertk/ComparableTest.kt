package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ComparableTest {
    // isGreaterThan tests
    @Test
    fun isGreaterThan_succeeds_when_value_is_greater() {
        assertThat(10).isGreaterThan(5)
        assertThat("b").isGreaterThan("a")
    }

    @Test
    fun isGreaterThan_fails_with_power_assert_format() {
        val actual = 5
        val expected = 10

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isGreaterThan(expected)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isGreaterThan(expected)
            |          |                     |
            |          |                     10
            |          5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isLessThan tests
    @Test
    fun isLessThan_succeeds_when_value_is_less() {
        assertThat(5).isLessThan(10)
        assertThat("a").isLessThan("b")
    }

    @Test
    fun isLessThan_fails_with_power_assert_format() {
        val actual = 10
        val expected = 5

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isLessThan(expected)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isLessThan(expected)
            |          |                  |
            |          |                  5
            |          10
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isGreaterThanOrEqualTo tests
    @Test
    fun isGreaterThanOrEqualTo_succeeds_when_value_is_greater_or_equal() {
        assertThat(10).isGreaterThanOrEqualTo(10)
        assertThat(10).isGreaterThanOrEqualTo(5)
    }

    @Test
    fun isGreaterThanOrEqualTo_fails_with_power_assert_format() {
        val actual = 5
        val expected = 10

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isGreaterThanOrEqualTo(expected)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isGreaterThanOrEqualTo(expected)
            |          |                              |
            |          |                              10
            |          5
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isLessThanOrEqualTo tests
    @Test
    fun isLessThanOrEqualTo_succeeds_when_value_is_less_or_equal() {
        assertThat(5).isLessThanOrEqualTo(5)
        assertThat(5).isLessThanOrEqualTo(10)
    }

    @Test
    fun isLessThanOrEqualTo_fails_with_power_assert_format() {
        val actual = 10
        val expected = 5

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isLessThanOrEqualTo(expected)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isLessThanOrEqualTo(expected)
            |          |                           |
            |          |                           5
            |          10
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isBetween tests
    @Test
    fun isBetween_succeeds_when_value_is_between_inclusive() {
        assertThat(5).isBetween(1, 10)
        assertThat(1).isBetween(1, 10) // inclusive start
        assertThat(10).isBetween(1, 10) // inclusive end
    }

    @Test
    fun isBetween_fails_with_power_assert_format() {
        val actual = 15
        val start = 1
        val end = 10

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isBetween(start, end)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isBetween(start, end)
            |          |                 |      |
            |          |                 |      10
            |          |                 1
            |          15
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isStrictlyBetween tests
    @Test
    fun isStrictlyBetween_succeeds_when_value_is_strictly_between() {
        assertThat(5).isStrictlyBetween(1, 10)
    }

    @Test
    fun isStrictlyBetween_fails_when_equal_to_boundaries() {
        assertFailsWith<AssertionError> {
            assertThat(1).isStrictlyBetween(1, 10)
        }
        assertFailsWith<AssertionError> {
            assertThat(10).isStrictlyBetween(1, 10)
        }
    }

    @Test
    fun isStrictlyBetween_fails_with_power_assert_format() {
        val actual = 15
        val start = 1
        val end = 10

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isStrictlyBetween(start, end)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isStrictlyBetween(start, end)
            |          |                         |      |
            |          |                         |      10
            |          |                         1
            |          15
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isCloseTo Double tests
    @Test
    fun isCloseTo_double_succeeds_when_within_delta() {
        assertThat(10.0).isCloseTo(10.5, 0.6)
        assertThat(10.5).isCloseTo(10.0, 0.5)
    }

    @Test
    fun isCloseTo_double_fails_with_power_assert_format() {
        val actual = 10.0
        val expected = 15.0
        val delta = 1.0

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isCloseTo(expected, delta)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isCloseTo(expected, delta)
            |          |                 |         |
            |          |                 |         1.0
            |          |                 15.0
            |          10.0
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isCloseTo Float tests
    @Test
    fun isCloseTo_float_succeeds_when_within_delta() {
        assertThat(10.0f).isCloseTo(10.5f, 0.6f)
        assertThat(10.5f).isCloseTo(10.0f, 0.5f)
    }

    @Test
    fun isCloseTo_float_fails_with_power_assert_format() {
        val actual = 10.0f
        val expected = 15.0f
        val delta = 1.0f

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isCloseTo(expected, delta)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isCloseTo(expected, delta)
            |          |                 |         |
            |          |                 |         1.0
            |          |                 15.0
            |          10.0
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    // isEqualByComparingTo tests
    @Test
    fun isEqualByComparingTo_succeeds_when_equal_by_comparison() {
        assertThat(10).isEqualByComparingTo(10)
        assertThat("hello").isEqualByComparingTo("hello")
    }

    @Test
    fun isEqualByComparingTo_fails_with_power_assert_format() {
        val actual = 10
        val expected = 20

        val error = assertFailsWith<AssertionError> {
            assertThat(actual).isEqualByComparingTo(expected)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(actual).isEqualByComparingTo(expected)
            |          |                            |
            |          |                            20
            |          10
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    @Test
    fun isEqualByComparingTo_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).isEqualByComparingTo(20) { "Custom: values differ" }
            }
        val message = error.message!!
        assertTrue(message.contains("Custom: values differ"))
    }

    // Property chain tests
    @Test
    fun isGreaterThan_shows_power_assert_with_property_chain() {
        data class Score(val points: Int)
        val score = Score(5)
        val threshold = 10

        val error = assertFailsWith<AssertionError> {
            assertThat(score.points).isGreaterThan(threshold)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(score.points).isGreaterThan(threshold)
            |          |     |                     |
            |          |     |                     10
            |          |     5
            |          Score(points=5)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    @Test
    fun isLessThan_shows_power_assert_with_property_chain() {
        data class Version(val major: Int)
        val version = Version(10)
        val minVersion = 5

        val error = assertFailsWith<AssertionError> {
            assertThat(version.major).isLessThan(minVersion)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(version.major).isLessThan(minVersion)
            |          |       |                 |
            |          |       |                 5
            |          |       10
            |          Version(major=10)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }

    @Test
    fun isBetween_shows_power_assert_with_property_chain() {
        data class Priority(val level: Int)
        val priority = Priority(15)
        val min = 1
        val max = 10

        val error = assertFailsWith<AssertionError> {
            assertThat(priority.level).isBetween(min, max)
        }
        val message = error.message!!

        val expectedFormat = """
            assertThat(priority.level).isBetween(min, max)
            |          |        |                |    |
            |          |        |                |    10
            |          |        |                1
            |          |        15
            |          Priority(level=15)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
        )
    }
}

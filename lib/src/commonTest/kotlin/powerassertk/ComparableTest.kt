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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(5).isGreaterThan(10)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(5).isGreaterThan(10)"))
    }

    // isLessThan tests
    @Test
    fun isLessThan_succeeds_when_value_is_less() {
        assertThat(5).isLessThan(10)
        assertThat("a").isLessThan("b")
    }

    @Test
    fun isLessThan_fails_with_power_assert_format() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).isLessThan(5)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(10).isLessThan(5)"))
    }

    // isGreaterThanOrEqualTo tests
    @Test
    fun isGreaterThanOrEqualTo_succeeds_when_value_is_greater_or_equal() {
        assertThat(10).isGreaterThanOrEqualTo(10)
        assertThat(10).isGreaterThanOrEqualTo(5)
    }

    @Test
    fun isGreaterThanOrEqualTo_fails_with_power_assert_format() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(5).isGreaterThanOrEqualTo(10)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(5).isGreaterThanOrEqualTo(10)"))
    }

    // isLessThanOrEqualTo tests
    @Test
    fun isLessThanOrEqualTo_succeeds_when_value_is_less_or_equal() {
        assertThat(5).isLessThanOrEqualTo(5)
        assertThat(5).isLessThanOrEqualTo(10)
    }

    @Test
    fun isLessThanOrEqualTo_fails_with_power_assert_format() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).isLessThanOrEqualTo(5)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(10).isLessThanOrEqualTo(5)"))
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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(15).isBetween(1, 10)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(15).isBetween(1, 10)"))
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
        val error =
            assertFailsWith<AssertionError> {
                assertThat(15).isStrictlyBetween(1, 10)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(15).isStrictlyBetween(1, 10)"))
    }

    // isCloseTo Double tests
    @Test
    fun isCloseTo_double_succeeds_when_within_delta() {
        assertThat(10.0).isCloseTo(10.5, 0.6)
        assertThat(10.5).isCloseTo(10.0, 0.5)
    }

    @Test
    fun isCloseTo_double_fails_with_power_assert_format() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10.0).isCloseTo(15.0, 1.0)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(10.0).isCloseTo(15.0, 1.0)"))
    }

    // isCloseTo Float tests
    @Test
    fun isCloseTo_float_succeeds_when_within_delta() {
        assertThat(10.0f).isCloseTo(10.5f, 0.6f)
        assertThat(10.5f).isCloseTo(10.0f, 0.5f)
    }

    @Test
    fun isCloseTo_float_fails_with_power_assert_format() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10.0f).isCloseTo(15.0f, 1.0f)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(10.0f).isCloseTo(15.0f, 1.0f)"))
    }

    // isEqualByComparingTo tests
    @Test
    fun isEqualByComparingTo_succeeds_when_equal_by_comparison() {
        assertThat(10).isEqualByComparingTo(10)
        assertThat("hello").isEqualByComparingTo("hello")
    }

    @Test
    fun isEqualByComparingTo_fails_with_power_assert_format() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).isEqualByComparingTo(20)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(10).isEqualByComparingTo(20)"))
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
}

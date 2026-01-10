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
        val error = assertFailsWith<AssertionError> {
            assertThat(5).isZero()
        }
        assertTrue(error.message!!.contains("assertThat(5).isZero()"))
    }

    @Test
    fun isZero_fails_for_negative_values() {
        val error = assertFailsWith<AssertionError> {
            assertThat(-5).isZero()
        }
        assertTrue(error.message!!.contains("assertThat(-5).isZero()"))
    }

    @Test
    fun isZero_fails_for_double_non_zero() {
        val error = assertFailsWith<AssertionError> {
            assertThat(1.5).isZero()
        }
        assertTrue(error.message!!.contains("assertThat(1.5).isZero()"))
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
        val error = assertFailsWith<AssertionError> {
            assertThat(0).isNotZero()
        }
        assertTrue(error.message!!.contains("assertThat(0).isNotZero()"))
    }

    @Test
    fun isNotZero_fails_for_zero_double() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0.0).isNotZero()
        }
        assertTrue(error.message!!.contains("assertThat(0.0).isNotZero()"))
    }

    @Test
    fun isNotZero_fails_for_zero_long() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0L).isNotZero()
        }
        assertTrue(error.message!!.contains("assertThat(0L).isNotZero()"))
    }

    @Test
    fun isNotZero_fails_for_zero_float() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0.0f).isNotZero()
        }
        assertTrue(error.message!!.contains("assertThat(0.0f).isNotZero()"))
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
        val error = assertFailsWith<AssertionError> {
            assertThat(-5).isPositive()
        }
        assertTrue(error.message!!.contains("assertThat(-5).isPositive()"))
    }

    @Test
    fun isPositive_fails_for_zero() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0).isPositive()
        }
        assertTrue(error.message!!.contains("assertThat(0).isPositive()"))
    }

    @Test
    fun isPositive_fails_for_zero_double() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0.0).isPositive()
        }
        assertTrue(error.message!!.contains("assertThat(0.0).isPositive()"))
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
        val error = assertFailsWith<AssertionError> {
            assertThat(5).isNegative()
        }
        assertTrue(error.message!!.contains("assertThat(5).isNegative()"))
    }

    @Test
    fun isNegative_fails_for_zero() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0).isNegative()
        }
        assertTrue(error.message!!.contains("assertThat(0).isNegative()"))
    }

    @Test
    fun isNegative_fails_for_zero_double() {
        val error = assertFailsWith<AssertionError> {
            assertThat(0.0).isNegative()
        }
        assertTrue(error.message!!.contains("assertThat(0.0).isNegative()"))
    }
}

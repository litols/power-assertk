package powerassertk.assertions

import powerassertk.Assert
import kotlin.math.abs

/**
 * Asserts the value is greater than the other value.
 */
fun <T : Comparable<T>> Assert<T>.isGreaterThan(
    other: T,
    message: (() -> String)? = null,
) {
    if (actual <= other) {
        throw AssertionError(
            message?.invoke() ?: "expected to be greater than:<$other> but was:<$actual>",
        )
    }
}

/**
 * Asserts the value is less than the other value.
 */
fun <T : Comparable<T>> Assert<T>.isLessThan(
    other: T,
    message: (() -> String)? = null,
) {
    if (actual >= other) {
        throw AssertionError(
            message?.invoke() ?: "expected to be less than:<$other> but was:<$actual>",
        )
    }
}

/**
 * Asserts the value is greater than or equal to the other value.
 */
fun <T : Comparable<T>> Assert<T>.isGreaterThanOrEqualTo(
    other: T,
    message: (() -> String)? = null,
) {
    if (actual < other) {
        throw AssertionError(
            message?.invoke() ?: "expected to be greater than or equal to:<$other> but was:<$actual>",
        )
    }
}

/**
 * Asserts the value is less than or equal to the other value.
 */
fun <T : Comparable<T>> Assert<T>.isLessThanOrEqualTo(
    other: T,
    message: (() -> String)? = null,
) {
    if (actual > other) {
        throw AssertionError(
            message?.invoke() ?: "expected to be less than or equal to:<$other> but was:<$actual>",
        )
    }
}

/**
 * Asserts the value is between start and end (inclusive).
 */
fun <T : Comparable<T>> Assert<T>.isBetween(
    start: T,
    end: T,
    message: (() -> String)? = null,
) {
    if (actual < start || actual > end) {
        throw AssertionError(
            message?.invoke() ?: "expected to be between:<$start> and:<$end> (inclusive) but was:<$actual>",
        )
    }
}

/**
 * Asserts the value is strictly between start and end (exclusive).
 */
fun <T : Comparable<T>> Assert<T>.isStrictlyBetween(
    start: T,
    end: T,
    message: (() -> String)? = null,
) {
    if (actual <= start || actual >= end) {
        throw AssertionError(
            message?.invoke() ?: "expected to be strictly between:<$start> and:<$end> (exclusive) but was:<$actual>",
        )
    }
}

/**
 * Asserts the Double value is close to the expected value within the given delta.
 */
fun Assert<Double>.isCloseTo(
    expected: Double,
    delta: Double,
    message: (() -> String)? = null,
) {
    if (abs(actual - expected) > delta) {
        throw AssertionError(
            message?.invoke() ?: "expected to be close to:<$expected> (delta:<$delta>) but was:<$actual>",
        )
    }
}

/**
 * Asserts the Float value is close to the expected value within the given delta.
 */
fun Assert<Float>.isCloseTo(
    expected: Float,
    delta: Float,
    message: (() -> String)? = null,
) {
    if (abs(actual - expected) > delta) {
        throw AssertionError(
            message?.invoke() ?: "expected to be close to:<$expected> (delta:<$delta>) but was:<$actual>",
        )
    }
}

/**
 * Asserts the value equals the expected value using compareTo.
 */
fun <T : Comparable<T>> Assert<T>.isEqualByComparingTo(
    expected: T,
    message: (() -> String)? = null,
) {
    if (actual.compareTo(expected) != 0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be equal by comparison to:<$expected> but was:<$actual>",
        )
    }
}

package powerassertk

import kotlin.jvm.JvmName

// Int extensions

/**
 * Asserts the number is 0.
 */
@JvmName("intIsZero")
fun Assert<Int>.isZero(message: (() -> String)? = null) {
    if (actual != 0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be zero but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is not 0.
 */
@JvmName("intIsNotZero")
fun Assert<Int>.isNotZero(message: (() -> String)? = null) {
    if (actual == 0) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be zero",
        )
    }
}

/**
 * Asserts the number is greater than 0.
 */
@JvmName("intIsPositive")
fun Assert<Int>.isPositive(message: (() -> String)? = null) {
    if (actual <= 0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be positive but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is less than 0.
 */
@JvmName("intIsNegative")
fun Assert<Int>.isNegative(message: (() -> String)? = null) {
    if (actual >= 0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be negative but was:<$actual>",
        )
    }
}

// Long extensions

/**
 * Asserts the number is 0.
 */
@JvmName("longIsZero")
fun Assert<Long>.isZero(message: (() -> String)? = null) {
    if (actual != 0L) {
        throw AssertionError(
            message?.invoke() ?: "expected to be zero but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is not 0.
 */
@JvmName("longIsNotZero")
fun Assert<Long>.isNotZero(message: (() -> String)? = null) {
    if (actual == 0L) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be zero",
        )
    }
}

/**
 * Asserts the number is greater than 0.
 */
@JvmName("longIsPositive")
fun Assert<Long>.isPositive(message: (() -> String)? = null) {
    if (actual <= 0L) {
        throw AssertionError(
            message?.invoke() ?: "expected to be positive but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is less than 0.
 */
@JvmName("longIsNegative")
fun Assert<Long>.isNegative(message: (() -> String)? = null) {
    if (actual >= 0L) {
        throw AssertionError(
            message?.invoke() ?: "expected to be negative but was:<$actual>",
        )
    }
}

// Double extensions

/**
 * Asserts the number is 0.
 */
@JvmName("doubleIsZero")
fun Assert<Double>.isZero(message: (() -> String)? = null) {
    if (actual != 0.0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be zero but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is not 0.
 */
@JvmName("doubleIsNotZero")
fun Assert<Double>.isNotZero(message: (() -> String)? = null) {
    if (actual == 0.0) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be zero",
        )
    }
}

/**
 * Asserts the number is greater than 0.
 */
@JvmName("doubleIsPositive")
fun Assert<Double>.isPositive(message: (() -> String)? = null) {
    if (actual <= 0.0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be positive but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is less than 0.
 */
@JvmName("doubleIsNegative")
fun Assert<Double>.isNegative(message: (() -> String)? = null) {
    if (actual >= 0.0) {
        throw AssertionError(
            message?.invoke() ?: "expected to be negative but was:<$actual>",
        )
    }
}

// Float extensions

/**
 * Asserts the number is 0.
 */
@JvmName("floatIsZero")
fun Assert<Float>.isZero(message: (() -> String)? = null) {
    if (actual != 0.0f) {
        throw AssertionError(
            message?.invoke() ?: "expected to be zero but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is not 0.
 */
@JvmName("floatIsNotZero")
fun Assert<Float>.isNotZero(message: (() -> String)? = null) {
    if (actual == 0.0f) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be zero",
        )
    }
}

/**
 * Asserts the number is greater than 0.
 */
@JvmName("floatIsPositive")
fun Assert<Float>.isPositive(message: (() -> String)? = null) {
    if (actual <= 0.0f) {
        throw AssertionError(
            message?.invoke() ?: "expected to be positive but was:<$actual>",
        )
    }
}

/**
 * Asserts the number is less than 0.
 */
@JvmName("floatIsNegative")
fun Assert<Float>.isNegative(message: (() -> String)? = null) {
    if (actual >= 0.0f) {
        throw AssertionError(
            message?.invoke() ?: "expected to be negative but was:<$actual>",
        )
    }
}

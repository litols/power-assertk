package powerassertk.assertions

import powerassertk.Assert

/**
 * Returns an Assert on the length for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <T : CharSequence> Assert<T>.length(): Assert<Int> = Assert(actual.length)

/**
 * Asserts the CharSequence is empty.
 */
fun <T : CharSequence> Assert<T>.isEmpty(message: (() -> String)? = null) {
    if (actual.isNotEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be empty but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence is not empty.
 */
fun <T : CharSequence> Assert<T>.isNotEmpty(message: (() -> String)? = null) {
    if (actual.isEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be empty",
        )
    }
}

/**
 * Asserts the CharSequence? is null or empty.
 */
fun <T : CharSequence?> Assert<T>.isNullOrEmpty(message: (() -> String)? = null) {
    if (actual != null && actual.isNotEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be null or empty but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence has the expected length.
 */
fun <T : CharSequence> Assert<T>.hasLength(
    length: Int,
    message: (() -> String)? = null,
) {
    if (actual.length != length) {
        throw AssertionError(
            message?.invoke() ?: "expected to have length:<$length> but was:<${actual.length}>",
        )
    }
}

/**
 * Asserts the CharSequence has the same length as the other CharSequence.
 */
fun <T : CharSequence> Assert<T>.hasSameLengthAs(
    other: CharSequence,
    message: (() -> String)? = null,
) {
    if (actual.length != other.length) {
        throw AssertionError(
            message?.invoke()
                ?: "expected to have same length as:<\"$other\"> (${other.length}) but was:<${actual.length}>",
        )
    }
}

/**
 * Asserts the CharSequence has the expected number of lines.
 */
fun <T : CharSequence> Assert<T>.hasLineCount(
    lineCount: Int,
    message: (() -> String)? = null,
) {
    val actualLineCount = actual.lines().size
    if (actualLineCount != lineCount) {
        throw AssertionError(
            message?.invoke() ?: "expected to have line count:<$lineCount> but was:<$actualLineCount>",
        )
    }
}

/**
 * Asserts the CharSequence contains the expected CharSequence.
 */
fun <T : CharSequence> Assert<T>.contains(
    expected: CharSequence,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (!actual.contains(expected, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected to contain:<\"$expected\"> but was:<\"$actual\">",
        )
    }
}

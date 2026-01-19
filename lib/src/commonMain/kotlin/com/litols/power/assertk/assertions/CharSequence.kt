@file:Suppress("TooManyFunctions")

package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert

/**
 * Asserts the CharSequence contains all expected CharSequences.
 */
fun <T : CharSequence> Assert<T>.contains(
    vararg expected: CharSequence,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    val notFound = expected.filter { !actual.contains(it, ignoreCase) }
    if (notFound.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected to contain all:<${notFound.joinToString { "\"$it\"" }}> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence contains all expected CharSequences.
 */
fun <T : CharSequence> Assert<T>.contains(
    expected: Iterable<CharSequence>,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    val notFound = expected.filter { !actual.contains(it, ignoreCase) }
    if (notFound.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected to contain all:<${notFound.joinToString { "\"$it\"" }}> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence does not contain the expected CharSequence.
 */
fun <T : CharSequence> Assert<T>.doesNotContain(
    expected: CharSequence,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (actual.contains(expected, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected not to contain:<\"$expected\"> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence does not contain any of the expected CharSequences.
 */
fun <T : CharSequence> Assert<T>.doesNotContain(
    vararg expected: CharSequence,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    val found = expected.filter { actual.contains(it, ignoreCase) }
    if (found.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected not to contain any of:<${found.joinToString { "\"$it\"" }}> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence does not contain any of the expected CharSequences.
 */
fun <T : CharSequence> Assert<T>.doesNotContain(
    expected: Iterable<CharSequence>,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    val found = expected.filter { actual.contains(it, ignoreCase) }
    if (found.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected not to contain any of:<${found.joinToString { "\"$it\"" }}> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence starts with the expected CharSequence.
 */
fun <T : CharSequence> Assert<T>.startsWith(
    other: CharSequence,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (!actual.startsWith(other, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected to start with:<\"$other\"> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence ends with the expected CharSequence.
 */
fun <T : CharSequence> Assert<T>.endsWith(
    other: CharSequence,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (!actual.endsWith(other, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected to end with:<\"$other\"> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence matches the expected regex.
 */
fun <T : CharSequence> Assert<T>.matches(
    regex: Regex,
    message: (() -> String)? = null,
) {
    if (!regex.matches(actual)) {
        throw AssertionError(
            message?.invoke() ?: "expected to match:<$regex> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the CharSequence contains a match of the expected regex.
 */
fun <T : CharSequence> Assert<T>.containsMatch(
    regex: Regex,
    message: (() -> String)? = null,
) {
    if (!regex.containsMatchIn(actual)) {
        throw AssertionError(
            message?.invoke() ?: "expected to contain match:<$regex> but was:<\"$actual\">",
        )
    }
}

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

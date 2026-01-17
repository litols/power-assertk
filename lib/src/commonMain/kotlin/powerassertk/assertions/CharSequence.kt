package powerassertk.assertions

import powerassertk.Assert

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

package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert

/**
 * Asserts the value matches the given predicate.
 */
fun <T> Assert<T>.matchesPredicate(
    message: (() -> String)? = null,
    f: (T) -> Boolean,
) {
    if (!f(actual)) {
        throw AssertionError(
            message?.invoke() ?: "expected to match predicate but was:<$actual>",
        )
    }
}

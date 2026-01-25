package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.notifyFailure

/**
 * Asserts the value matches the given predicate.
 */
fun <T> Assert<T>.matchesPredicate(
    message: (() -> String)? = null,
    f: (T) -> Boolean,
) {
    if (!f(actual)) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to match predicate but was:<$actual>",
            ),
        )
    }
}

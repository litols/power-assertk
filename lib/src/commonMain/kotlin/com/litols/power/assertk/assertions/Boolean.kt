package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert

/**
 * Asserts the value is true.
 */
fun Assert<Boolean>.isTrue(message: (() -> String)? = null) {
    if (!actual) {
        throw AssertionError(
            message?.invoke() ?: "expected to be true but was false",
        )
    }
}

/**
 * Asserts the value is false.
 */
fun Assert<Boolean>.isFalse(message: (() -> String)? = null) {
    if (actual) {
        throw AssertionError(
            message?.invoke() ?: "expected to be false but was true",
        )
    }
}

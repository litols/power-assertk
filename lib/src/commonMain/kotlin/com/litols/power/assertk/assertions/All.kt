package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.Failure

/**
 * Executes all assertions in the lambda and collects all failures.
 * All assertions will run even if earlier ones fail.
 *
 * Example:
 * ```
 * assertThat("Test").all {
 *     startsWith("L")
 *     hasLength(3)
 * }
 * // Output: The following assertions failed (2 failures):
 * // - expected to start with:<"L"> but was:<"Test">
 * // - expected to have length:<3> but was:<4>
 * ```
 */
fun <T> Assert<T>.all(body: Assert<T>.() -> Unit) {
    Failure.soft().invoke {
        body()
    }
}

/**
 * Executes all assertions in the lambda and collects all failures with a custom message prefix.
 */
fun <T> Assert<T>.all(
    message: String,
    body: Assert<T>.() -> Unit,
) {
    Failure.soft(message).invoke {
        body()
    }
}

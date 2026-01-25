package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.notifyFailure
import kotlin.jvm.JvmName

/**
 * Returns an Assert on the size for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <T : Collection<*>> Assert<T>.size(): Assert<Int> = Assert(actual.size)

/**
 * Asserts the Collection is empty.
 */
fun <T : Collection<*>> Assert<T>.isEmpty(message: (() -> String)? = null) {
    if (actual.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be empty but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the Collection is not empty.
 */
fun <T : Collection<*>> Assert<T>.isNotEmpty(message: (() -> String)? = null) {
    if (actual.isEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected not to be empty",
            ),
        )
    }
}

/**
 * Asserts the Collection? is null or empty.
 */
@JvmName("isNullOrEmptyNullable")
fun <T : Collection<*>?> Assert<T>.isNullOrEmpty(message: (() -> String)? = null) {
    if (actual != null && actual.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be null or empty but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the Collection has the expected size.
 */
fun <T : Collection<*>> Assert<T>.hasSize(
    size: Int,
    message: (() -> String)? = null,
) {
    if (actual.size != size) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to have size:<$size> but was:<${actual.size}>",
            ),
        )
    }
}

/**
 * Asserts the Collection has the same size as the other Collection.
 */
fun <T : Collection<*>> Assert<T>.hasSameSizeAs(
    other: Collection<*>,
    message: (() -> String)? = null,
) {
    if (actual.size != other.size) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to have same size as:<$other> (${other.size}) but was:<${actual.size}>",
            ),
        )
    }
}

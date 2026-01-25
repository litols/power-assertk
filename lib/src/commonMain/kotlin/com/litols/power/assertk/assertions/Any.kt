@file:Suppress("TooManyFunctions")

package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.notifyFailure

/**
 * Asserts the value is equal to expected using `==`.
 */
fun <T> Assert<T>.isEqualTo(
    expected: T,
    message: (() -> String)? = null,
) {
    if (actual != expected) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected:<$expected> but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the value is not equal to expected using `!=`.
 */
fun <T> Assert<T>.isNotEqualTo(
    expected: Any?,
    message: (() -> String)? = null,
) {
    if (actual == expected) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to not be equal to:<$expected>",
            ),
        )
    }
}

/**
 * Asserts the value is null.
 */
fun <T> Assert<T>.isNull(message: (() -> String)? = null) {
    if (actual != null) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be null but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the value is not null and returns an Assert<T> for chaining.
 */
fun <T : Any> Assert<T?>.isNotNull(message: (() -> String)? = null): Assert<T> {
    if (actual == null) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be not null",
            ),
        )
        // In soft failure mode, we still need to return something
        // Use a dummy non-null value (this will never be reached in normal mode)
        @Suppress("UNCHECKED_CAST")
        return Assert(null as T)
    }
    return Assert(actual)
}

/**
 * Asserts the value is the same instance as expected using `===`.
 */
fun <T> Assert<T>.isSameInstanceAs(
    expected: T,
    message: (() -> String)? = null,
) {
    if (actual !== expected) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected same instance:<$expected> but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the value is not the same instance as expected using `!==`.
 */
fun <T> Assert<T>.isNotSameInstanceAs(
    expected: Any?,
    message: (() -> String)? = null,
) {
    if (actual === expected) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to not be same instance:<$expected>",
            ),
        )
    }
}

/**
 * Asserts the value is the same instance as expected using `===`.
 * Alias for [com.litols.power.assertk.assertions.isSameInstanceAs].
 */
fun <T> Assert<T>.isSameAs(
    expected: T,
    message: (() -> String)? = null,
) {
    isSameInstanceAs(expected, message)
}

/**
 * Asserts the value is not the same instance as expected using `!==`.
 * Alias for [com.litols.power.assertk.assertions.isNotSameInstanceAs].
 */
fun <T> Assert<T>.isNotSameAs(
    expected: Any?,
    message: (() -> String)? = null,
) {
    isNotSameInstanceAs(expected, message)
}

/**
 * Asserts the value is in the provided values.
 */
fun <T> Assert<T>.isIn(
    vararg values: T,
    message: (() -> String)? = null,
) {
    if (actual !in values) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be in:<${values.contentToString()}> but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the value is not in the provided values.
 */
fun <T> Assert<T>.isNotIn(
    vararg values: T,
    message: (() -> String)? = null,
) {
    if (actual in values) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to not be in:<${values.contentToString()}> but was:<$actual>",
            ),
        )
    }
}

/**
 * Asserts the value's toString() equals the expected string.
 */
fun <T> Assert<T>.hasToString(
    string: String,
    message: (() -> String)? = null,
) {
    val actualString = actual.toString()
    if (actualString != string) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected toString:<\"$string\"> but was:<\"$actualString\">",
            ),
        )
    }
}

/**
 * Returns an Assert on the toString() result for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <T> Assert<T>.toStringFun(): Assert<String> = Assert(actual.toString())

/**
 * Asserts the value's hashCode() equals the expected hash code.
 */
fun <T : Any> Assert<T>.hasHashCode(
    hashCode: Int,
    message: (() -> String)? = null,
) {
    val actualHashCode = actual.hashCode()
    if (actualHashCode != hashCode) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected hashCode:<$hashCode> but was:<$actualHashCode>",
            ),
        )
    }
}

/**
 * Returns an Assert on the hashCode() result for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <T : Any> Assert<T>.hashCodeFun(): Assert<Int> = Assert(actual.hashCode())

/**
 * Asserts the value is an instance of the expected type and returns an Assert<T> for chaining.
 */
inline fun <reified T : Any> Assert<*>.isInstanceOf(noinline message: (() -> String)? = null): Assert<T> {
    if (actual !is T) {
        notifyFailure(
            AssertionError(
                message?.invoke()
                    ?: "expected to be instance of:<${T::class}> but was instance of:<${actual!!::class}>",
            ),
        )
        // In soft failure mode, return a dummy value
        @Suppress("UNCHECKED_CAST")
        return Assert(null as T)
    }
    return Assert(actual)
}

/**
 * Asserts the value is not an instance of the expected type.
 */
inline fun <reified T : Any> Assert<*>.isNotInstanceOf(noinline message: (() -> String)? = null) {
    if (actual is T) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to not be instance of:<${T::class}> but was:<$actual>",
            ),
        )
    }
}

// TODO: hasClass and doesNotHaveClass temporarily disabled due to Power Assert plugin compatibility issues
// /**
//  * Asserts the value has the expected kotlin class.
//  * This is an exact match, so assertThat("test").hasClass(String::class) is successful
//  * but assertThat("test").hasClass(Any::class) fails.
//  */
// fun <T : Any> Assert<T>.hasClass(
//     kclass: kotlin.reflect.KClass<*>,
//     message: (() -> String)? = null,
// ) {
//     if (actual::class != kclass) {
//         throw AssertionError(
//             message?.invoke() ?: "expected to have class:<$kclass> but was:<${actual::class}>",
//         )
//     }
// }
//
// /**
//  * Asserts the value does not have the expected kotlin class.
//  * This is an exact match, so assertThat("test").doesNotHaveClass(String::class) fails
//  * but assertThat("test").doesNotHaveClass(Any::class) is successful.
//  */
// fun <T : Any> Assert<T>.doesNotHaveClass(
//     kclass: kotlin.reflect.KClass<*>,
//     message: (() -> String)? = null,
// ) {
//     if (actual::class == kclass) {
//         throw AssertionError(
//             message?.invoke() ?: "expected to not have class:<$kclass> but was:<${actual::class}>",
//         )
//     }
// }

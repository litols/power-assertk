package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert

/**
 * Provides access to the actual value for building custom assertions.
 *
 * Example:
 * ```
 * fun Assert<Person>.hasAge(expected: Int) = given { actual ->
 *     if (actual.age == expected) return@given
 *     expected("age:${show(expected)} but was age:${show(actual.age)}")
 * }
 * ```
 */
inline fun <T> Assert<T>.given(assertion: (T) -> Unit) {
    assertion(actual)
}

/**
 * Formats a value for display in error messages.
 */
fun show(value: Any?): String =
    when (value) {
        null -> "null"
        is String -> "\"$value\""
        is Char -> "'$value'"
        is Long -> "${value}L"
        is Float -> "${value}f"
        is Double -> "${value}d"
        else -> value.toString()
    }

/**
 * Throws an AssertionError with the formatted message.
 * Use this function within `given` blocks to fail assertions.
 */
fun expected(message: String): Nothing = throw AssertionError(message)
